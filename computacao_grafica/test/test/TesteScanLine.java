package test;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import negocio.Drawner;
import negocio.beans.Ponto;
import negocio.beans.Triangulo;
import negocio.exception.NegocioException;
import negocio.exception.RasterizacaoException;

public class TesteScanLine extends Application{
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
    
	@Override
	public void start(Stage primaryStage) throws Exception {
		int width = 600; 
		int height = 600;
		Canvas canvas = new Canvas(width, height);
		GraphicsContext graphic = canvas.getGraphicsContext2D();
		graphic = canvas.getGraphicsContext2D();
		graphic.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		graphic.setFill(Color.WHITE);

		Ponto p1, p2, p3;		
		p1 = new Ponto(34, 201, 1);
		p2 = new Ponto(234, 588, 1);
		p3 = new Ponto(500, 123, 1);
		
//		p1 = new Ponto(400, 100, 1);
//		p2 = new Ponto(200, 300, 1);
//		p3 = new Ponto(300, 450, 1);
//		
//		p1 = new Ponto(50, 50, 1);
//		p2 = new Ponto(50, 100, 1);
//		p3 = new Ponto(50, 150, 1);
//
//		p1 = new Ponto(100, 50, 1);
//		p2 = new Ponto(50, 200, 1);
//		p3 = new Ponto(150, 200, 1);
//		
//		p1 = new Ponto(50, 50, 1);
//		p2 = new Ponto(100, 50, 1);
//		p3 = new Ponto(300, 400, 1);
//		
//		p1 = new Ponto(10, 10, 1);
//		p2 = new Ponto(10, 10, 1);
//		p3 = new Ponto(10, 10, 1);
//		
//		p1 = new Ponto(10, 10, 1);
//		p2 = new Ponto(50, 10, 1);
//		p3 = new Ponto(100, 10, 1);
//		
//		p1 = new Ponto(10, 10, 1);
//		p2 = new Ponto(50, 50, 1);
//		p3 = new Ponto(100, 100, 1);
//		
//		p1 = new Ponto(100, 0, 1);
//		p2 = new Ponto(50, 50, 1);
//		p3 = new Ponto(0, 100, 1);
//		
//		p1 = new Ponto(100, 100, 1);
//		p2 = new Ponto(105, 200, 1);
//		p3 = new Ponto(110, 300, 1);
//		
//		p1 = new Ponto(110, 100, 1);
//		p2 = new Ponto(105, 200, 1);
//		p3 = new Ponto(100, 300, 1);
//		
//		p1 = new Ponto(10, 300, 1);
//		p2 = new Ponto(20, 300, 1);
//		p3 = new Ponto(590, 300, 1);
		
		scanLine(p1, p2, p3, graphic);
		scanLine(p1, p3, p2, graphic);
		scanLine(p2, p3, p1, graphic);
		scanLine(p2, p1, p3, graphic);
		scanLine(p3, p1, p2, graphic);
		scanLine(p3, p2, p1, graphic);

		Group group = new Group(canvas);
		Scene scene = new Scene(group);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
