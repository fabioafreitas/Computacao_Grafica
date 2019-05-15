package negocio;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.rules.Verifier;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import negocio.beans.CameraVirtual;
import negocio.beans.Objeto;
import negocio.beans.Ponto;
import negocio.beans.Triangulo;
import negocio.exception.NegocioException;
import negocio.exception.RasterizacaoException;

public class Drawner{ 	
	/**
	 * Método da questão dois da primeira entrega
	 * @param width
	 * @param height
	 * @param fileName
	 * @return Cena pronta para ser exibida
	 * @throws IOException
	 * @throws NumberFormatException
	 * @throws NegocioException
	 */
	public static Scene desenharProjecaoOrtogonal(int width, int height, String fileName) 
			throws IOException, NumberFormatException, NegocioException {
		int margin = 20;
		Objeto form = new Objeto(fileName);
		form.normalizarProjecaoOrtogonal(width, height);
		
		Canvas canvas = new Canvas(width, height);
		canvas.setTranslateX(margin/2);
		canvas.setTranslateY(margin/2);
		
		GraphicsContext graphic = canvas.getGraphicsContext2D();
		graphic = canvas.getGraphicsContext2D();
		ArrayList<Ponto> pontos = form.getVertices();
		
		//Pintando todo o CANVAS de preto (Cor default do GraphicsContext é Preto)
		graphic.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		// Pintando a forma no canvas
		for (int i = 0; i < pontos.size() ; i++) {
			graphic.setFill(Color.WHITE);
			graphic.fillRect(pontos.get(i).getX() - 1 ,
							 pontos.get(i).getY() - 1 , 1, 1);
		}
		
		Group group = new Group(canvas);
		return new Scene(group, width+margin, height+margin);
	}

	/**
	 * Metodo da segunda entrega
	 * @param canvas
	 * @param camera
	 * @param objeto
	 * @throws NegocioException 
	 */
	public static void projecaoPerspectiva(Canvas canvas, CameraVirtual camera, Objeto objeto) throws NegocioException {
		GraphicsContext graphic = canvas.getGraphicsContext2D();
		graphic = canvas.getGraphicsContext2D();
		graphic.setFill(Color.WHITE);
		int width  = (int) canvas.getWidth(); 
		int height = (int) canvas.getHeight();
		
		ArrayList<Ponto> vertices = objeto.getVertices();
		ArrayList<int[]> indexTriangulos = objeto.getIndiceTriangulos();
		
		// Transformações dos pontos do objeto para coordenadas de tela
		Ponto ponto = null;
		for (int i = 0; i < vertices.size(); i++) {
			ponto = Algebra.getCoordenadasVista(vertices.get(i), camera);
			ponto = Algebra.getProjecaoPerspectiva(ponto, camera);
			ponto = Algebra.getPerspectivaNormalizada(ponto, camera);
			ponto = Algebra.getCoordenadaTela(ponto, width, height);
			vertices.get(i).setX(ponto.getX());
			vertices.get(i).setY(ponto.getY());
			vertices.get(i).setZ(ponto.getZ());
			//graphic.fillRect(ponto.getX(), ponto.getY(), 1, 1);
		}
		
		// Rasterizando os triangulos do objeto
		for (int[] index : indexTriangulos) {
			Ponto p1 = vertices.get(index[0]);
			Ponto p2 = vertices.get(index[1]);
			Ponto p3 = vertices.get(index[2]);
			scanLine(p1, p2, p3, graphic);
		}
	}
	
	public static void scanLine(Ponto p1, Ponto p2, Ponto p3, GraphicsContext pincel) {
		pincel.setFill(Color.WHITE);
		if( (p1.getY() == p2.getY()) && (p2.getY() == p3.getY())) {
			Ponto[] list = ordenarPontosAbscissa(new Ponto[] {p1, p2, p3});
			int xMinTela = (int) Math.floor(list[0].getX()+0.5);
			int xMaxTela = (int) Math.floor(list[2].getX()+0.5);
			int yTela = (int) list[1].getY();
			while(xMinTela <= xMaxTela) {
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
	
    public static double abscissaDaReta(double y, Ponto pa, Ponto pb) {
    	double a = pb.getX() - pa.getX();
    	double b = pb.getY() - pa.getY();
    	double c = y - pa.getY();
    	return (a/b)*c + pa.getX();
    }
    
    public static Ponto[] ordenarPontosOrdenada(Ponto[] list) {
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
	
    public static Ponto[] ordenarPontosAbscissa(Ponto[] list) {
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
}
