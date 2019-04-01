package beans;

public class Vetor {
	public double x,y,z;
	
	/*
	 * Construtor para Vetor 3D
	 */
	public Vetor(double x,double y,double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/*
	 * Construtor para Vetor 2D
	 */
	public Vetor(double x,double y) {
		this.x = x;
		this.y = y;
	}
	
	/*
	 * Retorna o produto escalar entre dois vetores 3D
	 */
	public double produtoEscalar3D(Vetor vetor2) {
		if(vetor2 == null) 
			throw new RuntimeException("vetor2 é nulo");
		return this.x*vetor2.x + this.y*vetor2.y + this.z*vetor2.z;
	}
	
	/*
	 * Retorna o produto escalar entre dois vetores 2D
	 */
	public double produtoEscalar2D(Vetor vetor2) {
		if(vetor2 == null) 
			throw new RuntimeException("vetor2 é nulo");
		return this.x*vetor2.x + this.y*vetor2.y;
	}
	
	
	/*
	 * Retorna o produto vetorial entre this.vetor e vetor2
	 */
	public Vetor produtoVetorial3D(Vetor v2) {
		if(v2 == null) 
			throw new RuntimeException("vetor2 é nulo");
		double a,b,c,d,e,f;
		a = this.x;
		b = this.y;
		c = this.z;
		d = v2.x;
		e = v2.y;
		f = v2.z;
		return new Vetor(b*f-c*e, 	/* X */
						 c*d-a*f,	/* Y */
						 a*e-b*d);	/* Z */
	}
	
	/*
	 * Retorna a norma (Comprimento) do vetor
	 */
	public double norma() {
		return Math.sqrt(Math.pow(this.x, 2) +
						 Math.pow(this.y, 2) +
						 Math.pow(this.z, 2));
	}
	
	/*
	 * Retorna o vetor normalizado, com norma entre 0 e 1
	 */
	public Vetor normalizar() {
		double norma = norma();
		return new Vetor(this.x/norma,
						 this.y/norma, 
						 this.z/norma);
	}
	
	public void print() {
		System.out.print("["+x+"]["+y+"]["+z+"]");
	}
}