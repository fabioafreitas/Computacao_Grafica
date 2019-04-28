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
import negocio.beans.Forma;
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
		Forma form = new Forma(fileName);
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
	public static void projecaoPerspectiva(Canvas canvas, CameraVirtual camera, Forma objeto) throws NegocioException {
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
			
			/*
			 * Apos as operacoes algebricas, os pontos mudam de posicao.
			 * Caso essas conversoes gerem tres pontos colinear, entao
			 * nao tera como rasterizado
			 * Este if testa esta situacao
			 */
			if(!Algebra.verificarColinearidade(p1, p2, p3)) {
				Triangulo triangulo = new Triangulo(p1, p2, p3);
				rasterizarTriangulo(triangulo, graphic); 
			}
		}
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
	public static void rasterizarTriangulo(Triangulo triangulo, GraphicsContext graphic) throws NegocioException {
		int y1 = (int) triangulo.getP1().getY();
		int y2 = (int) triangulo.getP2().getY();
		int y3 = (int) triangulo.getP3().getY();
		
		if(y1 != y2 && y2 != y3 && y1 != y3) {
			Triangulo[] listTriangulo = dividirTriangulo(triangulo);
			rasterizarTriangulo(listTriangulo[0], graphic);
			rasterizarTriangulo(listTriangulo[1], graphic);
		}
		else {
			Ponto[] list = ordenaPontos(triangulo.getPontos());
			if((int) list[1].getY() == (int) list[2].getY())
				scanLineTrianguloCima(triangulo, graphic);
			else
				scanLineTrianguloBaixo(triangulo, graphic);
				
		}
	}
	
	/**
	 * Separa um triangulo, cujas coordenadas Y sao todas diferentes
	 * Retorna o triangulo de cime e o de baixo, respectivamente
	 * @param triangulo
	 * @return
	 * @throws NegocioException
	 */
	private static Triangulo[] dividirTriangulo(Triangulo triangulo) throws NegocioException {
		Ponto p1, p2, p3;
		Ponto[] listPonto = ordenaPontos(triangulo.getPontos());
		p1 = listPonto[0]; // ponto alto
		p2 = listPonto[1]; // ponto medio
		p3 = listPonto[2]; // ponto baixo
		
		// Calculando ponto (ponto de corte) que divide o triangulo 
		double y = p2.getY();
		double x = equacaoReta(y, p1, p3);
		Ponto pontoCorte = new Ponto(x,y,0);
		
		return new Triangulo[] {new Triangulo(p1, p2, pontoCorte),
								new Triangulo(p2, p3, pontoCorte)};
	}
	
	/**
	 * Desenha uma linha entre dois pontos de entrada
	 * independe da posicao dos pontos
	 * @param p0
	 * @param p1
	 */
	private static void rasterizarLinha(Ponto p0, Ponto p1, GraphicsContext graphic) {
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
	
	/**
	 * calcula o scanLine de um triangulo do formato abaixo
	 * para ser rasterizado
	 *  /\
	 * /__\
	 * @param triangulo
	 * @param graphic
	 * @throws NegocioException
	 */
    private static void scanLineTrianguloCima(Triangulo t, GraphicsContext graphic) throws NegocioException {
    	Ponto[] list = ordenaPontos(t.getPontos());
    	
    	//triangulo com ponta para baixo
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
	
    /**
	 * calcula o scanLine de um triangulo do formato abaixo
	 * para ser rasterizado
	 * ____
	 * \  /
	 *  \/
	 * @param triangulo
	 * @param graphic
	 * @throws NegocioException
	 */
	private static void scanLineTrianguloBaixo(Triangulo t, GraphicsContext graphic) throws NegocioException {
    	Ponto[] list = ordenaPontos(t.getPontos());
    	
    	//triangulo com ponta para cima
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
	
	/**
	 * Calcula o valor correspondente do eixo X
	 * referente a seu valor de Y
	 * f(y)=x
	 * @param y
	 * @param pa
	 * @param pb
	 * @return
	 */
	private static double equacaoReta(double y, Ponto pa, Ponto pb) {
    	double a = pb.getX() - pa.getX();
    	double b = pb.getY() - pa.getY();
    	double c = y - pa.getY();
    	return (a/b)*c + pa.getX();
    }
	
	/**
	 * Ordena pontos com relação a seu eixo Y
	 * utilizada em pontos de triangulos
	 * @param list
	 * @return
	 */
	private static Ponto[] ordenaPontos(Ponto[] list) {
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
