package beans;

public class Ponto {
	public double x,y,z;
	
	/*
	 * Construtor para Ponto 3D
	 */
	public Ponto(double x,double y,double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/*
	 * Construtor para Ponto 2D
	 */
	public Ponto(double x,double y) {
		this.x = x;
		this.y = y;
	}
	
	/*
	 * Subtrai o this.ponto de um ponto de entrada
	 */
	public Ponto subtrair3D(Ponto ponto2) {
		if(ponto2 == null)
			throw new RuntimeException("ponto2 é nulo");
		Ponto ponto3 = new Ponto(0,0,0);
		ponto3.x = this.x - ponto2.x;
		ponto3.y = this.y - ponto2.y;
		ponto3.z = this.z - ponto2.z;
		return ponto3;
	}
	
	/*
	 * Subtrai o this.ponto de um ponto de entrada
	 */
	public Ponto subtrair2D(Ponto ponto2) {
		if(ponto2 == null)
			throw new RuntimeException("ponto2 é nulo");
		Ponto ponto3 = new Ponto(0,0,0);
		ponto3.x = this.x - ponto2.x;
		ponto3.y = this.y - ponto2.y;
		return ponto3;
	}
	
	public void print() {
		System.out.print("["+x+"]["+y+"]["+z+"]");
	}
}
