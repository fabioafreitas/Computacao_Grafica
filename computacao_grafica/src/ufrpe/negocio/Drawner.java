package ufrpe.negocio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ufrpe.negocio.beans.CameraVirtual;
import ufrpe.negocio.beans.Objeto;
import ufrpe.negocio.beans.Ponto;
import ufrpe.negocio.beans.Triangulo;
import ufrpe.negocio.beans.Vetor;
import ufrpe.negocio.beans.ZBuffer;
import ufrpe.negocio.exception.NegocioException;

public class Drawner{
	private static GraphicsContext pincel;
	private static List<Ponto> vertices;
	private static List<Ponto> verticesCoordVista;
	private static List<int[]> indexTriangulos;
	private static List<Triangulo> triangulos;
	private static List<Vetor> normaisVertices;
	private static ZBuffer depthBuffer[][];
	private static int width; 
	private static int height;
	
	public static void projecaoPerspectiva(Canvas canvas, CameraVirtual camera, Objeto objeto) throws NegocioException {
		width  = (int) canvas.getWidth(); 
		height = (int) canvas.getHeight();
		pincel = canvas.getGraphicsContext2D();
		vertices = objeto.getVertices();
		indexTriangulos = objeto.getIndiceTriangulos();
		verticesCoordVista = new ArrayList<>();
		
		// Transformando pontos para coordenadas de tela
		Ponto ponto;
		for (int i = 0; i < vertices.size(); i++) {
			ponto = Algebra.getCoordenadasVista(vertices.get(i), camera);
			verticesCoordVista.add(ponto);
			ponto = Algebra.getProjecaoPerspectiva(ponto, camera);
			ponto = Algebra.getPerspectivaNormalizada(ponto, camera);
			ponto = Algebra.getCoordenadaTela(ponto, width, height);
			vertices.get(i).setX(ponto.getX());
			vertices.get(i).setY(ponto.getY());
			vertices.get(i).setZ(ponto.getZ());
		}
		
		// Calculando triangulos
		triangulos = new ArrayList<>();
		for (int[] index : indexTriangulos) {
			triangulos.add(new Triangulo(vertices.get(index[0]), 
										 vertices.get(index[1]), 
										 vertices.get(index[2]),
										 verticesCoordVista.get(index[0]),
										 verticesCoordVista.get(index[1]),
										 verticesCoordVista.get(index[2])));
		}
		
		// Armazenando as normais de cada vertices
		normaisVertices = new ArrayList<>();
		for (int i = 0; i < vertices.size(); i++) {
			normaisVertices.add(getNormalDeVertice(i));
		}
		
		// TODO ordenar baricentros dos triangulos ??????????????
		
		/*
		// * após encontrar a normal de cada vértice, 
		 * 
		 * preciso determinar quais pixels serão pintados
		 * 
		 * Faço isto, avaliando todos os pixels do objeto, dentro do scanline, 
		 * 
		 * 	e calculando sua cor(iluminação) e produndidade
		 * 
		 * e armazenando esta informaçõe sna matriz do zbuffer. 
		 * 
		 * após fazer todas as avaliações pinto todos os pixels armazenados no ZBuffer.
		 */
		for (Triangulo triangulo : triangulos) {
			scanLine(triangulo);
		}
		//pintarCanvas();
	}
	
	private static void scanLine(Triangulo triangulo) throws NegocioException {
		pincel.setFill(Color.WHITE);
		Ponto p1 = triangulo.getP1();
		Ponto p2 = triangulo.getP2();
		Ponto p3 = triangulo.getP3();
		
		if( (p1.getY() == p2.getY()) && (p2.getY() == p3.getY())) {
			Ponto[] list = ordenarPontosAbscissa(new Ponto[] {p1, p2, p3});
			int xMinTela = (int) Math.floor(list[0].getX()+0.5);
			int xMaxTela = (int) Math.floor(list[2].getX()+0.5);
			int yTela = (int) list[1].getY();
			while(xMinTela <= xMaxTela) {
//				Ponto ponto = new Ponto(xMaxTela, yTela, 0);
//				calcularCor(ponto, triangulo);
//				xMinTela++;
				pincel.fillRect(xMinTela++, yTela, 1, 1);
			}
			return;
		}
		
		Ponto[] list = ordenarPontosOrdenada(new Ponto[] {p1, p2, p3});
		Ponto topo = list[0];
		Ponto meio = list[1];
		Ponto baixo = list[2];
		Ponto divisa = new Ponto(abscissaDaReta(meio.getY(), topo, baixo), meio.getY(), 0);
		
		if(meio.getX() > divisa.getX()) {
			Ponto aux = meio;
			meio = divisa;
			divisa = aux;
		}
		
		double xMin = topo.getX();
		double xMax = topo.getX();
		int yTela = (int) topo.getY();
		
		while(yTela <= meio.getY()) {
			int xMinTela = (int) Math.floor(xMin+0.5);
			int xMaxTela = (int) Math.floor(xMax+0.5);
			while(xMinTela <= xMaxTela) {
				pincel.fillRect(xMinTela++, yTela, 1, 1);
			}
			yTela++;
			xMin = abscissaDaReta(yTela, topo, meio);
			xMax = abscissaDaReta(yTela, topo, divisa);
		}
	
		xMin = baixo.getX(); 
		xMax = baixo.getX();
		yTela = (int) baixo.getY();
		
		while(yTela >= meio.getY()) {
			int xMinTela = (int) Math.floor(xMin+0.5);
			int xMaxTela = (int) Math.floor(xMax+0.5);
			while(xMinTela <= xMaxTela) {
				pincel.fillRect(xMinTela++, yTela, 1, 1);
			}
			yTela--;
			xMin = abscissaDaReta(yTela, baixo, meio);
			xMax = abscissaDaReta(yTela, baixo, divisa);
		}
	}
	
	private static double abscissaDaReta(double y, Ponto pa, Ponto pb) {
    	double a = pb.getX() - pa.getX();
    	double b = pb.getY() - pa.getY();
    	double c = y - pa.getY();
    	return (a/b)*c + pa.getX();
    }
    
    private static Ponto[] ordenarPontosOrdenada(Ponto[] list) {
    	for (int i = 0; i < list.length; i++) {
			for (int j = i+1; j < list.length; j++) {
				if(list[j].getY() < list[i].getY()) {
					Ponto aux = list[j];
					list[j] = list[i];
					list[i] = aux;
				}
			} 
		}
    	return list;
    }
	
    private static Ponto[] ordenarPontosAbscissa(Ponto[] list) {
    	for (int i = 0; i < list.length; i++) {
			for (int j = i+1; j < list.length; j++) {
				if(list[j].getY() < list[i].getY()) {
					Ponto aux = list[j];
					list[j] = list[i];
					list[i] = aux;
				}
			} 
		}
    	return list;
    }
    
    
    /**
     * Retorna o vetor normal a algum vertice
     * recebe o index deste vertice
     */
    private static Vetor getNormalDeVertice(int indexVertice) {
    	Vetor normalVertice = new Vetor(0, 0, 0);
    	Vetor normalTriangulo;
    	for (int i = 0; i < triangulos.size(); i++) {
    		int[] index = indexTriangulos.get(i);
			if(index[0] == indexVertice || index[1] == indexVertice || index[2] == indexVertice) {
				normalTriangulo = (triangulos.get(i)).getVetorNormal();
				normalVertice = normalVertice.somar(normalTriangulo);
			}
		}
    	return normalVertice.normalizar();
    }
    
    /**
     * Calcula a cor de um ponto com a iluminação de phong
     * case o ponto esteja fora dos limites da tela (width, height), ele não será calculado
     * É nesta etapa que preenchemos os pixels a serem pintados no ZBuffer
     * @param ponto
     * @param triangulo
     * @param width
     * @param height
     * @throws NegocioException 
     */
    private static void calcularCor(Ponto ponto, Triangulo triangulo) throws NegocioException {
    	int x = (int) ponto.getX();
    	int y = (int) ponto.getY();
    	if(x >= 0 || x < width || y >= 0 || y < height) {
    		Ponto coordBaricentrica = triangulo.coordenadaBaricentrica(ponto);
    		Ponto pontoEstimado = triangulo.getCartesianoDaBaricentrica(coordBaricentrica);
    		System.out.println();
    	}
    	
    }
    
    private static void inicializarDepthBuffer(int width, int height) {
    	depthBuffer = new ZBuffer[width][height];
    	for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				depthBuffer[i][j].setColor(Color.BLACK);
				depthBuffer[i][j].setDepth(Double.POSITIVE_INFINITY);
			}
		}
    }
    
    /**
     * Baseado nos pixels armazenados no ZBuffer, é realizada
     * a pintura dos pixels no vanvas
     */
    private static void pintarCanvas() {
    	for (int i = 0; i < depthBuffer.length; i++) {
			for (int j = 0; j < depthBuffer[0].length; j++) {
				pincel.setFill(depthBuffer[i][j].getColor());
				pincel.fillRect(i, j, 1, 1);
			}
		}
    }
}
