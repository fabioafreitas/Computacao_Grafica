package negocio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import beans.CameraVirtual;
import beans.Forma;
import beans.Ponto;
import beans.Triangulo;
import beans.Vetor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import negocio.exception.NegocioException;

//TODO corridir a classe drawner, devido a modificação da classe Forma
public class Drawner {
	/** 
	 * Lê um arquivo .byu, separa seus vétices, triangulos e os coloca num objeto Forma
	 * @param Nome do arquivo .byu presente no folder Formas/
	 * @return Objeto Forma referente a este arquivo
	 * @throws NegocioException 
	 * @throws NumberFormatException 
	 */
	public static Forma lerArquivoByu(String fileName) 
			throws IOException, NumberFormatException, NegocioException {
		// Leitor do arquivo
		String separator = System.getProperty("file.separator");
		String path = "Formas"+separator+fileName+".byu";
		BufferedReader reader = new BufferedReader(new FileReader(path));
		
		// Lógica da extração dos dados
		String line = reader.readLine();
		String split[] = line.split(" ");
		ArrayList<Ponto> vertices = new ArrayList<>();
		ArrayList<int[]> indicesTriangulos = new ArrayList<>();
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
			indicesTriangulos.add(new int[] {Integer.parseInt(split[0]) - 1,
											 Integer.parseInt(split[1]) - 1,
											 Integer.parseInt(split[2]) - 1});
		}
		reader.close();
		return new Forma(vertices, indicesTriangulos);
	}
	
	
	public static CameraVirtual lerArquivoCameraVirtual(String fileName) throws IOException {
		String separator = System.getProperty("file.separator");
		String path = "CameraVirtual"+separator+fileName+".txt";
		BufferedReader reader = new BufferedReader(new FileReader(path));
		
		String line = reader.readLine();
		String split[] = line.split(" ");
		Ponto pontoC = new Ponto(Double.parseDouble(split[0]), 
 								 Double.parseDouble(split[1]), 
								 Double.parseDouble(split[2]));

		line = reader.readLine();
		split = line.split(" ");
		Vetor vetorN = new Vetor(Double.parseDouble(split[0]), 
								 Double.parseDouble(split[1]), 
								 Double.parseDouble(split[2]));
		
		line = reader.readLine();
		split = line.split(" ");
		Vetor vetorV = new Vetor(Double.parseDouble(split[0]), 
								 Double.parseDouble(split[1]), 
								 Double.parseDouble(split[2]));
		
		double d = Double.parseDouble(reader.readLine());
		double hx = Double.parseDouble(reader.readLine());
		double hy = Double.parseDouble(reader.readLine());
		
		return new CameraVirtual(pontoC, vetorN, vetorV, d, hx, hy);
	}
	
	
	
	/**
	 * Desconsidera o eixo Z dos vértices de uma Forma
	 * @param Instância de Forma
	 * @return Projecao dos vértices desta Forma
	 * @throws NegocioException 
	 */
	public static Forma projecaoOrtogonal(Forma forma) throws NegocioException {
		ArrayList<Ponto> projecao = new ArrayList<Ponto>();
		ArrayList<Ponto> vertices = forma.getVertices();
		for (int i = 0; i < vertices.size() ; i++) {
			projecao.add(new Ponto(vertices.get(i).getX(), vertices.get(i).getY(), 0));
		}
		return new Forma(projecao, forma.getIndiceTriangulos());
	}
	
	

	/**
	 * 
	 * @param Forma projetada
	 * @param Largura da tela
	 * @param Altura da tela
	 * @return Retorna uma forma dentro dos padrões dessa tela
	 * @throws NegocioException 
	 */
	public static Forma normalizar(Forma projecao, int width, int height) throws NegocioException {
		ArrayList<Ponto> vertices = projecao.getVertices();
		
		//Busca mínimo e máximo para X e Y
		double minX = vertices.get(0).getX();
		double maxX = vertices.get(0).getX();
		double minY = vertices.get(0).getY();
		double maxY = vertices.get(0).getY();
		for (int i = 1; i < vertices.size(); i++) {
			double currentX = vertices.get(i).getX();
			double currentY = vertices.get(i).getY();
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
			vertices.get(i).setX(((vertices.get(i).getX() - minX)/(maxX - minX))*(width-1));
			vertices.get(i).setY(((vertices.get(i).getY() - minY)/(maxY - minY))*(height-1));
		}
		
		return new Forma(vertices, projecao.getIndiceTriangulos());
	}
	
	/**
	 * 
	 * @param objeto canvas que receberá a pintura
	 * @param nome do arquivo a ser pintado no canvas
	 * @throws IOException, caso o arquivo .byu não exista
	 * @throws NegocioException 
	 * @throws NumberFormatException 
	 */
	public static void desenharProjecaoOrtogonal(Canvas canvas, String fileName) 
			throws IOException, NumberFormatException, NegocioException {
		Forma form = projecaoOrtogonal(lerArquivoByu(fileName));
		form = normalizar(form, (int) canvas.getWidth(), (int) canvas.getHeight());
		GraphicsContext graphic = canvas.getGraphicsContext2D();
		ArrayList<Ponto> pontos = form.getVertices();
		
		//Pintando todos os pixels do canvas de preto
		graphic.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		// Pintando a forma no canvas
		for (int i = 0; i < pontos.size() ; i++) {
			graphic.setFill(Color.WHITE);
			graphic.fillRect(pontos.get(i).getX() - 1 ,
							 pontos.get(i).getY() - 1 , 10, 10);
		}
	}

}
