package beans;

import java.util.ArrayList;

import negocio.exception.EntradaInvalidaException;
import negocio.exception.FormatoInvalidoException;
import negocio.exception.NegocioException;

public class Forma {
	private ArrayList<Ponto> vertices;
	private ArrayList<int[]> indiceTriangulos;
	
	//TODO corrigir a entrada de indices invalidos por parte do arraylist
	public Forma(ArrayList<Ponto> vertices, ArrayList<int[]> indiceTriangulos) throws NegocioException{
		if(vertices == null || indiceTriangulos == null) 
			throw new RuntimeException("ArrayLists nulos");
		if(!indiceTriangulos.isEmpty() && indiceTriangulos.get(0).length != 3)
			throw new FormatoInvalidoException("ArrayList com indices de triangulos invalido");
		for (int i = 0; i < indiceTriangulos.size(); i++) {
			for (int j = 0; j < 3; j++) {
				if(indiceTriangulos.get(i)[j] < 0 || indiceTriangulos.get(i)[j] >= vertices.size())
					throw new EntradaInvalidaException("Indices de triangulos invalidos");
			}
		}
		this.vertices = vertices;
		this.indiceTriangulos = indiceTriangulos;
	}

	public ArrayList<Ponto> getVertices() {
		return vertices;
	}

	public ArrayList<int[]> getIndiceTriangulos() {
		return this.indiceTriangulos;
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
}
