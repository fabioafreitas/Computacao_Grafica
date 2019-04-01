package negocio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import beans.Forma;
import beans.Ponto;
import beans.Triangulo;

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
	public Forma projecao(Forma forma) {
		ArrayList<Ponto> projecao = new ArrayList<Ponto>();
		ArrayList<Ponto> vertices = forma.getVertices();
		for (int i = 0; i < vertices.size() ; i++) {
			projecao.add(new Ponto(vertices.get(i).x, vertices.get(i).y));
		}
		return new Forma(projecao, forma.getTriangulos());
	}
	
	public Forma normalizar() {
		//TODO 
		return null;
	}
}
