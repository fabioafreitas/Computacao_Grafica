package negocio;

import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import negocio.beans.CameraVirtual;
import negocio.beans.Forma;
import negocio.beans.Ponto;
import negocio.beans.Triangulo;
import negocio.exception.NegocioException;

//TODO corridir a classe drawner, devido a modificação da classe Forma
public class Drawner { 
	private static GraphicsContext graphic;
	
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
		Forma form = new Forma(fileName);
		form.normalizarProjecaoOrtogonal(width, height);
		
		Canvas canvas = new Canvas(width, height);
		canvas.setTranslateX(margin/2);
		canvas.setTranslateY(margin/2);
		
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
	public static void projecaoPerspectiva(Canvas canvas, CameraVirtual camera, Forma objeto) throws NegocioException {
		graphic = canvas.getGraphicsContext2D();
		graphic.setFill(Color.WHITE);
		int width  = (int) canvas.getWidth(); 
		int height = (int) canvas.getHeight();
		//graphic.fillRect(ponto.getX(), ponto.getY(), 1, 1);
		
		ArrayList<Ponto> vertices = objeto.getVertices();
		ArrayList<int[]> indexTriangulos = objeto.getIndiceTriangulos();
		
		// Transformações dos pontos do objeto para coordenadas de tela
		for (Ponto ponto : vertices) {
			ponto = Algebra.getCoordenadasVista(ponto, camera);
			ponto = Algebra.getProjecaoPerspectiva(ponto, camera);
			ponto = Algebra.getPerspectivaNormalizada(ponto, camera);
			ponto = Algebra.getCoordenadaTela(ponto, width, height);
		}
		
		for (int[] index : indexTriangulos) {
			rasterizarLinha(vertices.get(index[0]), vertices.get(index[1]));
			rasterizarLinha(vertices.get(index[1]), vertices.get(index[2]));
			rasterizarLinha(vertices.get(index[0]), vertices.get(index[2]));
		}
		
		// Rasterizando os triangulos do objeto
//		for (int[] index : indexTriangulos) {
//			Triangulo triangulo = new Triangulo(vertices.get(index[0]), 
//												vertices.get(index[1]), 
//												vertices.get(index[2]));
//			rasterizarTriangulo(triangulo);
//		}
	}
	
	/**
	 * Pinta os pixels de dentro de um triangulo
	 * se dois pontos do triangulo sao colineares (caso base), entao:
	 * 	- triangulo eh pintado
	 * senao
	 *  - triangulo eh dividido, de forma que os dois triangulos possuam dois pontos colineares
	 *  - a funcao eh chamada recursivamente e entra no caso base
	 * @param triangulo
	 * @param graphic
	 * @throws NegocioException
	 */
	public static void rasterizarTriangulo(Triangulo triangulo) throws NegocioException {
		int y1 = (int) triangulo.getP1().getY();
		int y2 = (int) triangulo.getP2().getY();
		int y3 = (int) triangulo.getP3().getY();
		
		if(y1 != y2 && y2 != y3 && y1 != y3) {
			Triangulo[] listTriangulo = dividirTriangulo(triangulo);
			rasterizarTriangulo(listTriangulo[0]);
			rasterizarTriangulo(listTriangulo[1]);
		}
		else {
			
		}
	}
	
	private static Triangulo[] dividirTriangulo(Triangulo triangulo) throws NegocioException {
		Ponto p1, p2, p3;
		Ponto[] listPonto = triangulo.getPontos();
		for (int i = 0; i < listPonto.length; i++) {
			for (int j = i+1; j < listPonto.length; j++) {
				if(listPonto[j].getY() < listPonto[i].getY()) {
					Ponto aux = listPonto[j];
					listPonto[j] = listPonto[i];
					listPonto[i] = aux;
				}
			}
		}
		p1 = listPonto[0]; // ponto alto
		p2 = listPonto[1]; // ponto medio
		p3 = listPonto[2]; // ponto baixo
		
		// Calculando ponto (ponto de corte) que divide o triangulo 
		double declive = ((p3.getX() - p1.getX()))/((p3.getY() - p1.getY()));
		double y = p2.getY();
		double x = declive*(y - p1.getY()) + p1.getX();
		Ponto pontoCorte = new Ponto(x,y,0);
		
		return new Triangulo[] {new Triangulo(p1, p2, pontoCorte),
								new Triangulo(p2, p3, pontoCorte)};
	}
	
	private static void rasterizarLinha(Ponto p0, Ponto p1) {
        if(p0.equals(p1)) {
        	graphic.fillRect( p0.getX(), p0.getY(), 1, 1);
        }
        else {
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
    }
	
	
	public static void main(String[] args) throws NegocioException {
		Triangulo t = new Triangulo(new Ponto(0, 3, 0),
									new Ponto(2, 2, 0),
									new Ponto(1, 1, 0));
		rasterizarTriangulo(t);
	} 
}
