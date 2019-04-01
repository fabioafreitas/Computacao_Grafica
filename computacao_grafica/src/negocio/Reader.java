package negocio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import beans.Forma;
import beans.Ponto;
import beans.Triangulo;


public class Reader{
	/*
	 * Lê um arquivo .byu, separa seus vétices, triangulos e os coloca num objeto Forma
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
}
