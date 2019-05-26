package ufrpe.negocio.beans;

public class Triangulo {
	public Ponto pMundial1, pMundial2, pMundial3;
	public Ponto pVista1, pVista2, pVista3;
	public Ponto pTela1, pTela2, pTela3;
	public Vetor normal;
	
	public Triangulo(Ponto pMundial1, Ponto pMundial2, Ponto pMundial3, 
					 Ponto pVista1, Ponto pVista2, Ponto pVista3,
					 Ponto pTela1, Ponto pTela2, Ponto pTela3,
					 Vetor normal) {
		this.pMundial1 = pMundial1;
		this.pMundial2 = pMundial2;
		this.pMundial3 = pMundial3;
		this.pVista1 = pVista1;
		this.pVista2 = pVista2;
		this.pVista3 = pVista3;
		this.pTela1 = pTela1;
		this.pTela2 = pTela2;
		this.pTela3 = pTela3;
		this.normal = normal;
	}

	public Triangulo() {
		super();
	}
	
	
}
