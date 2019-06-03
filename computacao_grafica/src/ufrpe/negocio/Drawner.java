package ufrpe.negocio;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ufrpe.negocio.beans.CameraVirtual;
import ufrpe.negocio.beans.Cor;
import ufrpe.negocio.beans.Iluminacao;
import ufrpe.negocio.beans.Objeto;
import ufrpe.negocio.beans.Ponto;
import ufrpe.negocio.beans.Triangulo;
import ufrpe.negocio.beans.Vetor;
import ufrpe.negocio.beans.ZBuffer;
import ufrpe.negocio.exception.NegocioException;

public class Drawner{
	private static GraphicsContext pincel;
	private static List<Ponto> verticesMundiais;
	private static List<Ponto> verticesVista;
	private static List<Ponto> verticesTela;
	private static List<int[]> indexTriangulos;
	private static List<Triangulo> triangulos;
	private static ZBuffer depthBuffer[][];
	private static Iluminacao iluminacao;
	private static int width; 
	private static int height;
	/**
	 * Função principal, que fará todo o algoritmo de renderização
	 * @param canvas
	 * @param camera
	 * @param objeto
	 * @param light
	 * @throws NegocioException
	 */
	public static void renderizar(Canvas canvas, CameraVirtual camera, Objeto objeto, Iluminacao ilumination) throws NegocioException {
		iluminacao = ilumination;
		pincel = canvas.getGraphicsContext2D();
		width  = (int) canvas.getWidth(); 
		height = (int) canvas.getHeight();
		triangulos = new ArrayList<>();
		verticesMundiais = objeto.getVertices();
		verticesVista = new ArrayList<>();
		verticesTela = new ArrayList<>();
		indexTriangulos = objeto.getIndiceTriangulos();
		
		// Tranformando pontos em coordenadas de tela
		for (Ponto pontoMundial : verticesMundiais) {
			Ponto pontoVista = Biblioteca.getCoordenadasVista(pontoMundial, camera);
			verticesVista.add(pontoVista);
			Ponto pontoPerspectiva = Biblioteca.getProjecaoPerspectiva(pontoVista, camera);
			Ponto pontoNormalizado = Biblioteca.getPerspectivaNormalizada(pontoPerspectiva, camera);
			Ponto pontoTela = Biblioteca.getCoordenadaTela(pontoNormalizado, width, height);
			verticesTela.add(pontoTela);
		}
		
		// atribuindo vertices mundiais, vista e de tela aos triangulos
		// calculando a normal dos triangulos
		for (int i = 0; i < indexTriangulos.size(); i++) {
			triangulos.add(new Triangulo());
			int[] index = indexTriangulos.get(i);
			triangulos.get(i).pVista1 = verticesVista.get(index[0]);
			triangulos.get(i).pVista2 = verticesVista.get(index[1]);
			triangulos.get(i).pVista3 = verticesVista.get(index[2]);
			triangulos.get(i).pTela1 = verticesTela.get(index[0]);
			triangulos.get(i).pTela2 = verticesTela.get(index[1]);
			triangulos.get(i).pTela3 = verticesTela.get(index[2]);
			Ponto pVista1 = verticesVista.get(index[0]);
			Ponto pVista2 = verticesVista.get(index[1]);
			Ponto pVista3 = verticesVista.get(index[2]);
			triangulos.get(i).normal = Biblioteca.getNormalDoTriangulo(pVista1, pVista2, pVista3);
		}
		
		// calculando vetores normais de cada vertice
		for (int i = 0; i < verticesVista.size(); i++) {
			verticesVista.get(i).setNormal(Biblioteca.getNormalDoVertice(verticesVista.get(i), triangulos));
		}
		
		// Reatribuindo vertices de vista aos triangulos
		// agora os vertices de vista já estão com suas normais atribuidas
		for (int i = 0; i < triangulos.size(); i++) {
			int[] index = indexTriangulos.get(i);
			triangulos.get(i).pVista1 = verticesVista.get(index[0]);
			triangulos.get(i).pVista2 = verticesVista.get(index[1]);
			triangulos.get(i).pVista3 = verticesVista.get(index[2]);
		}
		
		inicializarDepthBuffer();
		Biblioteca.mergesort(0, triangulos.size(), triangulos);
		
		for (Triangulo triangulo : triangulos) {
			scanLine(triangulo);
		}
		
		pintarCanvas();
	}
	
	private static void scanLine(Triangulo triangulo) throws NegocioException {
		Ponto p1 = triangulo.pTela1;
		Ponto p2 = triangulo.pTela2;
		Ponto p3 = triangulo.pTela3;
		
		if( (p1.getY() == p2.getY()) && (p2.getY() == p3.getY())) {
			Ponto[] list = ordenarPontosAbscissa(new Ponto[] {p1, p2, p3});
			int xMinTela = (int) Math.floor(list[0].getX()+0.5);
			int xMaxTela = (int) Math.floor(list[2].getX()+0.5);
			int yTela = (int) list[1].getY();
			while(xMinTela <= xMaxTela) {
				Ponto ponto = new Ponto(xMinTela, yTela, 0);
				zBufferAlgoritmo(ponto, triangulo);
				xMinTela++;
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
				Ponto ponto = new Ponto(xMinTela, yTela, 0);
				zBufferAlgoritmo(ponto, triangulo);
				xMinTela++;
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
				Ponto ponto = new Ponto(xMinTela, yTela, 0);
				zBufferAlgoritmo(ponto, triangulo);
				xMinTela++;
			}
			yTela--;
			xMin = abscissaDaReta(yTela, baixo, meio);
			xMax = abscissaDaReta(yTela, baixo, divisa);
		}
	}
	
	/**
	 * Utilizado apenas no ScanLine
	 * @param y
	 * @param pa
	 * @param pb
	 * @return
	 */
	private static double abscissaDaReta(double y, Ponto pa, Ponto pb) {
    	double a = pb.getX() - pa.getX();
    	double b = pb.getY() - pa.getY();
    	double c = y - pa.getY();
    	return (a/b)*c + pa.getX();
    }
    
	/**
	 * Utilizado apenas no ScanLine
	 * @param list
	 * @return
	 */
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
	
    /**
     * Utilizado apenas no ScanLine
     * @param list
     * @return
     */
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
     * Calcula a cor de um ponto com a iluminação de phong
     * case o ponto esteja fora dos limites da tela (width, height), ele não será calculado
     * É nesta etapa que preenchemos os pixels a serem pintados no ZBuffer
     * @param ponto
     * @param triangulo
     * @param width
     * @param height
     * @throws NegocioException 
     */
    private static void zBufferAlgoritmo(Ponto ponto, Triangulo triangulo) throws NegocioException {
    	int x = (int) ponto.getX();
    	int y = (int) ponto.getY();

    	if(x >= 0 && x < (width-1) && y >= 0 && y < (height-1)) {
    		Ponto p1 = triangulo.pVista1;
    		Ponto p2 = triangulo.pVista2;
    		Ponto p3 = triangulo.pVista3;
    		Ponto pt1 = triangulo.pTela1;
    		Ponto pt2 = triangulo.pTela2;
    		Ponto pt3 = triangulo.pTela3;
    		
    	
    		Ponto baricentrica = Biblioteca.coordenadaBaricentrica(ponto, pt1, pt2, pt3);
    		Ponto pontoEstimado = Biblioteca.getCartesianoDaBaricentrica(baricentrica, p1, p2, p3);
    		if(pontoEstimado.getZ() < depthBuffer[x][y].getDepth()) {
    			Vetor n = calcularVetorN(baricentrica, triangulo);
    			Vetor l = calcularVetorL(ponto);
    			Vetor v = calcularVetorV(ponto);
    			Vetor r = calcularVetorR(n, l);
    			Cor cor = iluminacaoPhong(n, l, v, r);
    			
    			depthBuffer[x][y].setColor(Color.rgb((int) cor.red,
							    					 (int) cor.green,
							    					 (int) cor.blue));
    			depthBuffer[x][y].setDepth(pontoEstimado.getZ());
    		}
    	}
    }
    
    /**
     * Inicializa o ZBuffer com profundidade no infinito e preto como cor do pixel
     */
    private static void inicializarDepthBuffer() {
    	depthBuffer = new ZBuffer[width][height];
    	for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				depthBuffer[i][j] = new ZBuffer(Color.BLACK, Double.POSITIVE_INFINITY);
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
    
    private static Cor iluminacaoPhong(Vetor n, Vetor l, Vetor v, Vetor r) {
    	Cor cor = new Cor(0, 0, 0);
    	Cor ambiente = iluminacaoAmbiental();
    	Cor difusa = null;
    	Cor espectral = null;
    	boolean casoEspecial = false;
    	
    	// caso especial difuso
    	if(n.produtoEscalar3D(l) < 0) {
			if(v.produtoEscalar3D(n) < 0) {
				n.setX( -n.getX() );
				n.setY( -n.getY() );
				n.setZ( -n.getZ() );
			} else {
				difusa = new Cor(0,0,0);
				espectral = new Cor(0,0,0);
				casoEspecial = true;
			}
		}
    	difusa = iluminacaoDifusa(n, l, v);
    	
    	// caso especial espectral
    	if(!casoEspecial) {
    		if(v.produtoEscalar3D(r) < 0) {
        		espectral = new Cor(0,0,0);
        	} else {
        		espectral = iluminacaoEspectral(v, r);
        	}
    	}
    	
    	cor = cor.somar(ambiente);
    	cor = cor.somar(difusa);
    	cor = cor.somar(espectral);
    	cor.verificarRGB();
    	return cor;
    }
    
	private static Cor iluminacaoAmbiental() {
		double ka = iluminacao.getKa();
		Cor iamb = iluminacao.getLuzAmb();
	    return new Cor(ka*(iamb.red), ka*(iamb.green), ka*(iamb.blue));
    }

	private static Cor iluminacaoDifusa(Vetor n, Vetor l, Vetor v) {
		Vetor kd = iluminacao.getKd();
		Vetor od = iluminacao.getOd();
		Cor il = iluminacao.getLuzL();
		
		double escalar = n.produtoEscalar3D(l);
		Vetor vetor = kd.multplicarEscalar(escalar);
		
		return new Cor(vetor.getX() * od.getX() * il.red,
					   vetor.getY() * od.getY() * il.green,
					   vetor.getZ() * od.getZ() * il.blue);
	}
	
	private static Cor iluminacaoEspectral(Vetor v, Vetor r) {
		double eta = iluminacao.getEta();
		double ks = iluminacao.getKs();
		Cor il = iluminacao.getLuzL();
		
		double escalar = v.produtoEscalar3D(r);
		escalar = Math.pow(escalar, eta)*ks;
		
		return new Cor(il.red * escalar,
					   il.green * escalar,
					   il.blue * escalar);
	}
	
	private static Vetor calcularVetorN(Ponto baricentrica, Triangulo triangulo) {
		double alfa = baricentrica.getX();
		double beta = baricentrica.getY();
		double gama = baricentrica.getZ();
		
		Vetor v1 = triangulo.pVista1.getNormal();
		Vetor v2 = triangulo.pVista2.getNormal();
		Vetor v3 = triangulo.pVista3.getNormal();
		
		v1 = v1.multplicarEscalar(alfa);
		v2 = v2.multplicarEscalar(beta);
		v3 = v3.multplicarEscalar(gama);
		
		Vetor n = (v1.somar(v2)).somar(v3);
		return n.normalizar();
	}

	private static Vetor calcularVetorL(Ponto ponto) {
		Vetor l = (iluminacao.getPl()).subtrair(ponto);
		return l.normalizar();
	}
	
	private static Vetor calcularVetorV(Ponto ponto) {
		Ponto origem = new Ponto(0, 0, 0);
		Vetor v = origem.subtrair(ponto);
		return v.normalizar();
	}
	
	private static Vetor calcularVetorR(Vetor n, Vetor l) {
		double escalar = 2*(n.produtoEscalar3D(l));
		Vetor vetor = n.multplicarEscalar(escalar);
		Vetor r = vetor.subtrair(l);
		return r.normalizar();
	}

}
