package ufrpe.negocio.beans;

import ufrpe.negocio.exception.NegocioException;

public class ZBuffer {
	public Matriz tela;
	
	public ZBuffer(int width, int height) throws NegocioException {
		tela = new Matriz(width, height);
	}
	
	
}
