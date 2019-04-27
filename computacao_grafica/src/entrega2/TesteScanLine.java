package entrega2;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import negocio.beans.Forma;
import negocio.beans.Ponto;

public class TesteScanLine extends Application{
    public static void rasterizarLinha(Ponto p0, Ponto p1, GraphicsContext graphic) {                    
        int x0 = (int) p0.getX();
        int y0 = (int) p0.getY();
        int x1 = (int) p1.getX();
        int y1 = (int) p1.getY();
        
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1; 
        int sy = y0 < y1 ? 1 : -1; 
        int err = dx-dy;
        int e2;
        while (!(x0 == x1 && y0 == y1))  {
        	graphic.fillRect(x0, y0, 1, 1);
            e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x0 = x0 + sx;
            }
            if (e2 < dx)  {
                err += dx;
                y0 = y0 + sy;
            }
        }                                
    }
	
    // por que este código não funciona e
//	public static void rasterizarLinha(Ponto p1, Ponto p0, GraphicsContext graphic) {
//		graphic.setFill(Color.WHITE);
//		if(p0.equals(p1))
//			graphic.fillRect(p0.getX(), p0.getY(), 1, 1);
//
//		double deltax =  p1.getX() - p0.getX();			
//		double deltay =  p1.getY() - p0.getY();
//		double erro = 0;
//		double declive = Math.abs(deltay/deltax);
//		int y = (int) p0.getY();
//		for(int x = (int) p0.getX(); x < p1.getX() ; x++) {
//			graphic.fillRect(x, y, 1, 1);
//			erro += declive;
//			if(erro >= 0.5) {
//				y += 1;
//				erro -= 1;
//			}
//		}
//	}
//	
	@Override
	public void start(Stage primaryStage) throws Exception {
		//ADICIONE O NOME DO ARQUIVO
		String fileName = "calice2"; 
		int width = 600; 
		int height = 600;
		Forma objeto = new Forma(fileName);
		objeto.normalizarProjecaoOrtogonal(width, height);
		Canvas canvas = new Canvas(width, height);
		GraphicsContext graphic = canvas.getGraphicsContext2D();
		graphic = canvas.getGraphicsContext2D();
		graphic.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		graphic.setFill(Color.WHITE);
//		for (Ponto ponto : objeto.getVertices()) {
//			graphic.fillRect(ponto.getX(),ponto.getY(), 1, 1);
//		}
		Ponto p0 = new Ponto(100, 100, 1);
		Ponto p1 = new Ponto(200, 250, 1);
		rasterizarLinha(p1, p0, graphic);
		p0 = new Ponto(200, 250, 1);
		p1 = new Ponto(400, 400, 1);
		rasterizarLinha(p1, p0, graphic);
		p0 = new Ponto(400, 400, 1);
		p1 = new Ponto(100, 100, 1);
		rasterizarLinha(p1, p0, graphic);
		p0 = new Ponto(250, 300, 1);
		p1 = new Ponto(250, 500, 1);
		rasterizarLinha(p1, p0, graphic);
		Group group = new Group(canvas);
		Scene scene = new Scene(group);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
