package entrega2;

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
    
    // só funciona se o ponto comum estiver acima dos outros dois
    public static void rasterizarTrianguloCima(Triangulo t, GraphicsContext graphic) throws NegocioException {
    	Ponto[] list = ordenaPontos(t.getPontos());
    	
    	//triangulo de ponta cabeça
    	if((int) list[0].getY() == (int) list[1].getY() )
    		throw new RasterizacaoException("ponto comum abaixo dos demais");
    		
    	Ponto ptop = list[0];
    	Ponto pdir = list[1];
    	Ponto pesq = list[2];
    	
    	int xMin = (int) ptop.getX();
    	int yMin = (int) ptop.getY();
    	int xMax = (int) ptop.getX();
    	int yMax = (int) ptop.getY();
    	
    	Ponto pmin = new Ponto(xMin, yMin, 0);
		Ponto pmax = new Ponto(xMax, yMax, 0);
    	while((int) pmin.getY() <= (int) pesq.getY()) {
    		rasterizarLinha(pmin, pmax, graphic);
    		yMin++;
    		yMax++;
    		xMin = (int) equacaoReta(yMin, ptop, pesq);
    		xMax = (int) equacaoReta(yMax, ptop, pdir);
    		pmin = new Ponto(xMin, yMin, 0);
    		pmax = new Ponto(xMax, yMax, 0);
    	}

    }
    
    public static double equacaoReta(double y, Ponto pa, Ponto pb) {
    	double a = pb.getX() - pa.getX();
    	double b = pb.getY() - pa.getY();
    	double c = y - pa.getY();
    	return (a/b)*c + pa.getX();
    }
    
    public static void rasterizarTrianguloBaixo(Triangulo t, GraphicsContext graphic) throws NegocioException {
    	Ponto[] list = ordenaPontos(t.getPontos());
    	
    	//triangulo de ponta cabeça
    	if((int) list[1].getY() == (int) list[2].getY() )
    		throw new RasterizacaoException("ponto comum a cima dos demais");
    		
    	Ponto pdir = list[0];
    	Ponto pesq = list[1];
    	Ponto plow = list[2];
    	
    	int xMin = (int) plow.getX();
    	int yMin = (int) plow.getY();
    	int xMax = (int) plow.getX();
    	int yMax = (int) plow.getY();
    	
    	Ponto pmin = new Ponto(xMin, yMin, 0);
		Ponto pmax = new Ponto(xMax, yMax, 0);
    	while((int) pmin.getY() >= (int) pesq.getY()) {
    		rasterizarLinha(pmin, pmax, graphic);
    		yMin--;
    		yMax--;
    		xMin = (int) equacaoReta(yMin, plow, pesq);
    		xMax = (int) equacaoReta(yMax, plow, pdir);
    		pmin = new Ponto(xMin, yMin, 0);
    		pmax = new Ponto(xMax, yMax, 0);
    	}

    }
    
    public static Ponto[] ordenaPontos(Ponto[] list) {
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

		Ponto ptop, plow, pdir, pesq;
		
//		ptop = new Ponto(300, 100, 1);
//		pdir = new Ponto(250, 150, 1);
//		pesq = new Ponto(350, 150, 1);
//		rasterizarTrianguloCima(new Triangulo(ptop, pdir, pesq), graphic);
//		
//		plow = new Ponto(300, 200, 1);
//		pdir = new Ponto(250, 150, 1);
//		pesq = new Ponto(350, 150, 1);
//		rasterizarTrianguloBaixo(new Triangulo(plow, pdir, pesq), graphic);
		
		Ponto p1, p2, p3;
//		p1 = new Ponto(0, 0, 0);
//		p2 = new Ponto(200, 200, 0);
//		p3 = new Ponto(100, 300, 0);
//		Drawner.rasterizarTriangulo(new Triangulo(p1, p2, p3), graphic);
//		
//		p1 = new Ponto(300, 100, 1);
//		p2 = new Ponto(250, 150, 1);
//		p3 = new Ponto(350, 150, 1);
//		Drawner.rasterizarTriangulo(new Triangulo(p1, p2, p3), graphic);
//		
//		p1 = new Ponto(300, 200, 1);
//		p2 = new Ponto(250, 150, 1);
//		p3 = new Ponto(350, 150, 1);
//		Drawner.rasterizarTriangulo(new Triangulo(p1, p2, p3), graphic);
		
		p1 = new Ponto(34.66, 201.4, 1);
		p2 = new Ponto(234.121212, 588.1, 1);
		p3 = new Ponto(500.2, 123.123, 1);
		Drawner.rasterizarTriangulo(new Triangulo(p1, p2, p3), graphic);
		
		
		
		Group group = new Group(canvas);
		Scene scene = new Scene(group);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
