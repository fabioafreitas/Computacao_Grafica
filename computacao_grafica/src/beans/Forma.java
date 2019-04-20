package beans;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import negocio.exception.EntradaInvalidaException;
import negocio.exception.FormatoInvalidoException;
import negocio.exception.NegocioException;

public class Forma {
	private ArrayList<Ponto> vertices;
	private ArrayList<int[]> indicesTriangulos;
	
	public Forma(String fileName) throws IOException, NegocioException {
		String separator = System.getProperty("file.separator");
		String path = "Formas"+separator+fileName+".byu";
		BufferedReader reader = new BufferedReader(new FileReader(path));
		
		// Lógica da extração dos dados
		String line = reader.readLine();
		if(line.length() == 0)
			throw new FormatoInvalidoException("Arquivo .byu no formato invalido");
		String split[] = line.split(" ");
		this.vertices = new ArrayList<>();
		this.indicesTriangulos = new ArrayList<int[]>();
		int numeroVertices = Integer.parseInt(split[0]);
		int numeroTriangulos = Integer.parseInt(split[1]);
		
		for (int i = 0; i < numeroVertices; i++) {
			line = reader.readLine();
			if(line.length() == 0)
				throw new FormatoInvalidoException("Arquivo .byu no formato invalido");
			split = line.split(" ");
			vertices.add(new Ponto( Double.parseDouble(split[0]),
									Double.parseDouble(split[1]),
									Double.parseDouble(split[2])));
		}
 
		for (int i = 0; i < numeroTriangulos; i++) {
			line = reader.readLine();
			if(line.length() == 0)
				throw new FormatoInvalidoException("Arquivo .byu no formato invalido");
			split = line.split(" ");
			if(split.length != 3)
				throw new FormatoInvalidoException("Indices de triangulos no formato invalido");
			int index[] = new int[] {Integer.parseInt(split[0]) - 1,
									 Integer.parseInt(split[1]) - 1,
									 Integer.parseInt(split[2]) - 1};
			for (int j = 0; j < index.length; j++) {
				if(index[j] < 0 || index[j] >= vertices.size())
					throw new EntradaInvalidaException("Indices de triangulos invalidos");
			}
			indicesTriangulos.add(index);
		}
		reader.close();
	}

	public ArrayList<Ponto> getVertices() {
		return vertices;
	}

	public ArrayList<int[]> getIndiceTriangulos() {
		return this.indicesTriangulos;
	} 
	
	public void setVertices(int indice, Ponto p) throws NegocioException {
		if(p == null)
			throw new RuntimeException("Ponto nulo");
		if(indice < 0)
			throw new EntradaInvalidaException("Indice invalido");
		if(indice >= vertices.size())
			throw new EntradaInvalidaException("Indice maior do que a quantidade de vertices");
		vertices.set(indice, p);
	}
	
	/**
	 * @param Largura da tela
	 * @param Altura da tela
	 * @throws NegocioException 
	 */
	public void normalizarProjecaoOrtogonal(int width, int height) throws NegocioException {		
		if(width <= 0 || height <= 0)
			throw new EntradaInvalidaException("Dimensoes de tela invalidas");
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
	}
	
	public void projecaoPerspectiva() {
		
	}
}
