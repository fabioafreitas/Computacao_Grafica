package ufrpe.negocio.beans;

import ufrpe.negocio.exception.NegocioException;
import ufrpe.negocio.exception.PontosColinearesException;

public class Triangulo {
	private Ponto p1, p2, p3;
	private Ponto pVista1, pVista2, pVista3;
	
	
	public Triangulo(Ponto p1, Ponto p2, Ponto p3, Ponto pVista1, Ponto pVista2, Ponto pVista3) {
		if(p1 == null || p2 == null || p3 == null || pVista1 == null || pVista2 == null || pVista3 == null)
			throw new RuntimeException("Ponto Nulo");
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.pVista1 = pVista1;
		this.pVista2 = pVista2;
		this.pVista3 = pVista3;
	}

	public Triangulo(Ponto p1, Ponto p2, Ponto p3) throws NegocioException {
		if(p1 == null || p2 == null || p3 == null)
			throw new RuntimeException("Ponto Nulo");
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
	}
	
	/**
	 * Retorna a coordenada baricentrica referente a um ponto p
	 * 
	 * Na logica deste projeto, o triangulo a ser utilizado
	 * sera o que possui pontos em coordenadas de tela (p1, p2, p3)
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
	 * 
	 * @param p
	 * @return
	 * @throws NegocioException
	 */
	public Ponto getCartesianoDaBaricentrica(Ponto coordBaricentrica) throws NegocioException{
		if(coordBaricentrica == null)
			throw new RuntimeException("Ponto nulo");
		double a, b, c;
		a = coordBaricentrica.getX();
		b = coordBaricentrica.getY();
		c = coordBaricentrica.getZ();
		return new Ponto(a*pVista1.getX()+b*pVista2.getX()+c*pVista3.getX(), 
						 a*pVista1.getY()+b*pVista2.getY()+c*pVista3.getY(),
						 a*pVista1.getZ()+b*pVista2.getZ()+c*pVista3.getZ());
	}
	
	
	
	/**
	 * Retorna o vetor normal ao triangulo
	 * Ja normaliza este vetor
	 * @return
	 */
	public Vetor getVetorNormal() {
		Vetor a = p2.subtrair(p1);
		Vetor b = p3.subtrair(p1);
		Vetor c = a.produtoVetorial(b);
		return c.normalizar();
	}
	
	public Ponto getBaricentro() {
		return new Ponto((p1.getX() + p2.getX() + p3.getX())/3,
						 (p1.getY() + p2.getY() + p3.getY())/3,
						 (p1.getZ() + p2.getZ() + p3.getZ())/3);
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
	
	public void setP1(Ponto p1) {
		this.p1 = p1;
	}

	public void setP2(Ponto p2) {
		this.p2 = p2;
	}

	public void setP3(Ponto p3) {
		this.p3 = p3;
	}
	
	public Ponto getpVista1() {
		return pVista1;
	}

	public void setpVista1(Ponto pVista1) {
		this.pVista1 = pVista1;
	}

	public Ponto getpVista2() {
		return pVista2;
	}

	public void setpVista2(Ponto pVista2) {
		this.pVista2 = pVista2;
	}

	public Ponto getpVista3() {
		return pVista3;
	}

	public void setpVista3(Ponto pVista3) {
		this.pVista3 = pVista3;
	}

	public boolean equals(Triangulo t) {
		if(t == null)
			throw new RuntimeException("Triangulo nulo");
		if(this.p1.equals(t.p1) && this.p2.equals(t.p2) && this.p3.equals(t.p3))
			return true;
		return false;
	}
}
