package beans;

import negocio.exception.NegocioException;
import negocio.exception.PontosColinearesException;

public class Triangulo {
	public Ponto p1, p2, p3;
	
	public Triangulo(Ponto p1, Ponto p2, Ponto p3) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
	}
	
	public Ponto coordenadaBaricentrica(Ponto p) throws NegocioException{
		if(verificarColinearidade()) {
			throw new PontosColinearesException("Sem coordenada baricentrica, pontos do triangulo sao colineares");
		}
		double a = p1.x - p3.x;
		double b = p2.x - p3.x;
		double c = p1.y - p3.y;
		double d = p2.y - p3.y;
		double det = 1/(a*d-b*c);
		double m1[][] = {{det*d, det*(-b)},{det*(-c), det*a}};
		double m2[][] = {{p.x-p3.x},{p.y-p3.y}};
		Matriz matriz1 = new  Matriz(m1);
		Matriz matriz2 = matriz1.multiplicar(new Matriz(m2));
		return new Ponto(matriz2.getMatriz()[0][0],
						 matriz2.getMatriz()[1][0], 
						 1 - matriz2.getMatriz()[0][0] - matriz2.getMatriz()[1][0]);
	}
	
	public Ponto pontoCoordenadaBaricentrica(Ponto coordBaricentrica) throws NegocioException{
		if(verificarColinearidade()) {
			throw new PontosColinearesException("Sem coordenada baricentrica, pontos do triangulo sao colineares");
		}
		double a, b, c;
		a = coordBaricentrica.x;
		b = coordBaricentrica.y;
		c = coordBaricentrica.z;
		return new Ponto(a*p1.x+b*p2.x+c*p3.x, 
						 a*p1.y+b*p2.y+c*p3.y);
	}
	
	private boolean verificarColinearidade() {
		return 0 == ( p1.x*p2.y + p1.y*p3.x + p2.x*p3.y)
					-(p3.x*p2.y + p3.y*p1.x + p2.x*p1.y);
	}
}
