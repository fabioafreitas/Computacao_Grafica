package negocio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import beans.Forma;
import beans.Ponto;
import beans.Triangulo;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Drawner {
	/**
	 * Lê um arquivo .byu, separa seus vétices, triangulos e os coloca num objeto Forma
	 * @param Nome do arquivo .byu presente no folder Formas/
	 * @return Objeto Forma referente a este arquivo
	 */
	public static Forma lerArquivo(String fileName) throws IOException {
		// Leitor do arquivo
		String path = "Formas/"+fileName+".byu";
		BufferedReader reader = new BufferedReader(new FileReader(path));
		
		// Lógica da extração dos dados
		String line = reader.readLine();
		String split[] = line.split(" ");
		ArrayList<Ponto> vertices = new ArrayList<>();
		ArrayList<Triangulo> triangulos = new ArrayList<>();
		int numeroVertices = Integer.parseInt(split[0]);
		int numeroTriangulos = Integer.parseInt(split[1]);
		
		for (int i = 0; i < numeroVertices; i++) {
			line = reader.readLine();
			split = line.split(" ");
			vertices.add(new Ponto( Double.parseDouble(split[0]),
									Double.parseDouble(split[1]),
									Double.parseDouble(split[2])));
		}

		for (int i = 0; i < numeroTriangulos; i++) {
			line = reader.readLine();
			split = line.split(" ");
			triangulos.add(new Triangulo(vertices.get(Integer.parseInt(split[0]) - 1),
										 vertices.get(Integer.parseInt(split[1]) - 1), 
										 vertices.get(Integer.parseInt(split[2]) - 1)));
		}
		reader.close();
		return new Forma(vertices, triangulos);
	}
	
	/**
	 * Desconsidera o eixo Z dos vértices de uma Forma
	 * @param Instância de Forma
	 * @return Projecao dos vértices desta Forma
	 */
	public static Forma projecao(Forma forma) {
		ArrayList<Ponto> projecao = new ArrayList<Ponto>();
		ArrayList<Ponto> vertices = forma.getVertices();
		for (int i = 0; i < vertices.size() ; i++) {
			projecao.add(new Ponto(vertices.get(i).x, vertices.get(i).y));
		}
		return new Forma(projecao, forma.getTriangulos());
	}
	
	/**
	 * 
	 * @param Forma projetada
	 * @param Largura da tela
	 * @param Altura da tela
	 * @return Retorna uma forma dentro dos padrões dessa tela
	 */
	public static Forma normalizar(Forma projecao, int width, int height) {
		ArrayList<Ponto> vertices = projecao.getVertices();
		
		//Busca mínimo e máximo para X e Y
		double minX = vertices.get(0).x;
		double maxX = vertices.get(0).x;
		double minY = vertices.get(0).y;
		double maxY = vertices.get(0).y;
		for (int i = 1; i < vertices.size(); i++) {
			double currentX = vertices.get(i).x;
			double currentY = vertices.get(i).y;
			if(currentX > maxX) 
				maxX = currentX;
			if(currentX < minX) 
				minX = currentX;
			if(currentY > maxY) 
				maxY = currentY;
			if(currentY < minY) 
				minY = currentY;			
		}
		
		//Observe que dará erro, caso o maxX igual a minX
		for (int i = 0; i < vertices.size(); i++) {
			vertices.get(i).x = ((vertices.get(i).x - minX)/(maxX - minX))*(width-1);
			vertices.get(i).y = ((vertices.get(i).y - minY)/(maxY - minY))*(height-1);
		}
		
		return new Forma(vertices, projecao.getTriangulos());
	}
	
	/**
	 * 
	 * @param objeto canvas que receberá a pintura
	 * @param nome do arquivo a ser pintado no canvas
	 * @throws IOException, caso o arquivo .byu não exista
	 */
	public static void desenhar(Canvas canvas, String fileName) throws IOException {
		Forma form = projecao(lerArquivo(fileName));
		form = normalizar(form, (int) canvas.getWidth(), (int) canvas.getHeight());
		GraphicsContext graphic = canvas.getGraphicsContext2D();
		ArrayList<Ponto> pontos = form.getVertices();
		
		//Pintando todos os pixels do canvas de preto
		graphic.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		// Pintando a forma no canvas
		for (int i = 0; i < pontos.size() ; i++) {
			graphic.setFill(Color.WHITE);
			graphic.fillRect(pontos.get(i).x - 1 ,
							 pontos.get(i).y - 1 , 1, 1);
		}
	}

}
