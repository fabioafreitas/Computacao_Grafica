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
import negocio.Drawner;
import negocio.beans.Forma;
import negocio.beans.Ponto;
import negocio.beans.Triangulo;

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
    
    public static void rasterizarTrianguloCima(Triangulo t, GraphicsContext graphic) throws InterruptedException {
    	Ponto[] list = t.getPontos();
    	for (int i = 0; i < list.length; i++) {
			for (int j = i+1; j < list.length; j++) {
				if(list[j].getY() < list[i].getY()) {
					Ponto aux = list[j];
					list[j] = list[i];
					list[i] = aux;
				}
			}
		}
    	
    	Ponto top = list[0];
    	Ponto dir = list[1];
    	Ponto esq = list[2];
    	
//    	top.setX( (int) top.getX() );
//    	top.setY( (int) top.getY() );
//    	dir.setX( (int) dir.getX() );
//    	dir.setY( (int) dir.getY() );
//    	esq.setX( (int) esq.getX() );
//    	esq.setY( (int) esq.getY() );
    	 
    	int topx = (int) top.getX();
    	int topy = (int) top.getY();
    	
    	int dirx = (int) dir.getX();
    	int diry = (int) dir.getY();
    	
    	int esqx = (int) esq.getX();
    	int esqy = (int) esq.getY();
    	
    	
    	int minx = topx;
    	int miny = topy;
    	
    	int maxx = topx;
    	int maxy = topy;
    	while((minx != dirx) && (miny != diry)) {
    		Ponto min = new Ponto(minx, miny, 0);
    		Ponto max = new Ponto(maxx, maxy, 0);
    		rasterizarLinha(min, max, graphic);
    		miny++;
    		maxy++;
    		minx = equacaoReta(miny, top, esq);
    		maxx = equacaoReta(maxy, top, dir);
    	}

    }
    
    public static int equacaoReta(int y, Ponto pa, Ponto pb) {
    	double a = pb.getX() - pa.getX();
    	double b = pb.getY() - pa.getY();
    	double c = y - pa.getY();
    	return (int) ((a/b)*c + pa.getX());
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

		Ponto ptop = new Ponto(300, 100, 1);
		Ponto pdir = new Ponto(250, 150, 1);
		Ponto pesq = new Ponto(350, 150, 1);
		rasterizarTrianguloCima(new Triangulo(ptop, pdir, pesq), graphic);
		
		Group group = new Group(canvas);
		Scene scene = new Scene(group);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
