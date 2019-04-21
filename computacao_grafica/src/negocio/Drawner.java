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
	 * TODO Rasterização
	 * @param canvas
	 * @param camera
	 * @param objeto
	 * @throws NegocioException 
	 */
	public static void projecaoPerspectiva(Canvas canvas, CameraVirtual camera, Forma objeto) throws NegocioException {
		graphic = canvas.getGraphicsContext2D();
		Ponto pontoAux;
		int width  = (int) canvas.getWidth(); 
		int height = (int) canvas.getHeight();
		
		graphic.setFill(Color.WHITE);
		for (Ponto ponto : objeto.getVertices()) {
			ponto = Algebra.baseMundialParaVista(ponto, camera);
			ponto = Algebra.converterPontoPerspectivaNormalizado(ponto, camera);
			ponto = Algebra.coverterPontoCoordenadaTela(ponto, width, height);
			
			graphic.fillRect(ponto.getX(), ponto.getY(), 1, 1);
		}
	}
}
