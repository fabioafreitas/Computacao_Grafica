package negocio.beans;

import negocio.exception.NegocioException;
import negocio.exception.PontosColinearesException;

public class Triangulo {
	private Ponto p1, p2, p3;
	
	public Triangulo(Ponto p1, Ponto p2, Ponto p3) throws NegocioException {
		if(p1 == null || p2 == null || p3 == null)
			throw new RuntimeException("Ponto Nulo");
		if(verificarColinearidade(p1, p2, p3))
			throw new PontosColinearesException("Triangulo invalido, pontos colineares");
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
	}
	
	/**
	 * Retorna a coordenada baricentrica referente a um ponto p 
	 * @param p
	 * @return
	 * @throws NegocioException
	 */
	public Ponto coordenadaBaricentrica(Ponto p) throws NegocioException{
		if(p == null)
			throw new RuntimeException("Ponto nulo");
		double a = p1.getX() - p3.getX();
		double b = p2.getX() - p3.getX();
		double c = p1.getY() - p3.getY();
		double d = p2.getY() - p3.getY();
		double det = 1/(a*d-b*c);
		double m1[][] = {{det*d, det*(-b)},{det*(-c), det*a}};
		double m2[][] = {{p.getX()-p3.getX()},{p.getY()-p3.getY()}};
		Matriz matriz1 = new  Matriz(m1);
		Matriz matriz2 = matriz1.multiplicar(new Matriz(m2));
		return new Ponto(matriz2.getMatriz()[0][0],
						 matriz2.getMatriz()[1][0], 
						 1 - matriz2.getMatriz()[0][0] - matriz2.getMatriz()[1][0]);
	}
	
	/**
	 * Retorna o ponto cartesiano referente a uma coordenada baricentrica
	 * @param coordBaricentrica
	 * @return
	 * @throws NegocioException
	 */
	public Ponto pontoCartesianoBaricentrico(Ponto coordBaricentrica) throws NegocioException{
		if(coordBaricentrica == null)
			throw new RuntimeException("Ponto nulo");
		double a, b, c;
		a = coordBaricentrica.getX();
		b = coordBaricentrica.getY();
		c = coordBaricentrica.getZ();
		return new Ponto(a*p1.getX()+b*p2.getX()+c*p3.getX(), 
						 a*p1.getY()+b*p2.getY()+c*p3.getY(),
						 0);
	}
	
	public Ponto getP1() {
		return p1;
	}

	public Ponto getP2() {
		return p2;
	}
	
	public Ponto getP3() {
		return p3;
	}
	
	public Ponto[] getPontos() {
		return new Ponto[] {this.p1, this.p2, this.p3};
	}
	
	public void setP1(Ponto p1) throws PontosColinearesException {
		if(p1 == null)
			throw new RuntimeException("Ponto Nulo");
		if(verificarColinearidade(p1, this.p2, this.p3))
			throw new PontosColinearesException("Set Ponto 1 colinear");
		this.p1 = p1;
	}
	
	public void setP2(Ponto p2) throws PontosColinearesException {
		if(p2 == null)
			throw new RuntimeException("Ponto Nulo");
		if(verificarColinearidade(this.p1, p2, this.p3))
			throw new PontosColinearesException("Set Ponto 2 colinear");
		this.p2 = p2;
	}
	
	public void setP3(Ponto p3) throws PontosColinearesException {
		if(p3 == null)
			throw new RuntimeException("Ponto Nulo");
		if(verificarColinearidade(this.p1, this.p2, p3))
			throw new PontosColinearesException("Set Ponto 3 colinear");
		this.p3 = p3;
	}
	
	public boolean equals(Triangulo t) {
		if(t == null)
			throw new RuntimeException("Triangulo nulo");
		if(this.p1.equals(t.p1) && this.p2.equals(t.p2) && this.p3.equals(t.p3))
			return true;
		return false;
	}
	
	private boolean verificarColinearidade(Ponto p1, Ponto p2, Ponto p3) {
		return 0 == ( p1.getX()*p2.getY() + p1.getY()*p3.getX() + p2.getX()*p3.getY())
					-(p3.getX()*p2.getY() + p3.getY()*p1.getX() + p2.getX()*p1.getY());
	}
}
