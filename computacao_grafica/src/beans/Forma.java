package beans;

import java.util.ArrayList;

public class Forma {
	private ArrayList<Ponto> vertices;
	private ArrayList<Triangulo> triangulos;
	
	public Forma(ArrayList<Ponto> vertices, ArrayList<Triangulo> triangulos) {
		this.vertices = vertices;
		this.triangulos = triangulos;
	}

	public ArrayList<Ponto> getVertices() {
		return vertices;
	}

	public ArrayList<Triangulo> getTriangulos() {
		return triangulos;
	}
}
