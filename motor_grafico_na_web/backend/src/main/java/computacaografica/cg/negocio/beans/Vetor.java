package computacaografica.cg.negocio.beans;

public class Vetor {
	private double x,y,z;
	
	public Vetor(double x,double y,double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/*
	 * Retorna o produto escalar entre dois vetores 3D
	 */
	public double produtoEscalar3D(Vetor vetor2) {
		if(vetor2 == null) 
			throw new RuntimeException("vetor é nulo");
		return this.x*vetor2.x + this.y*vetor2.y + this.z*vetor2.z;
	}
	
	/*
	 * Retorna o produto escalar entre dois vetores 2D
	 */
	public double produtoEscalar2D(Vetor vetor2) {
		if(vetor2 == null) 
			throw new RuntimeException("vetor é nulo");
		return this.x*vetor2.x + this.y*vetor2.y;
	}
	
	
	/*
	 * Retorna o produto vetorial entre this.vetor e vetor2
	 */
	public Vetor produtoVetorial(Vetor v2) {
		if(v2 == null) 
			throw new RuntimeException("vetor é nulo");
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
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}
	
	public Vetor subtrair(Vetor v) {
		if(v == null)
			throw new RuntimeException("Vetor nulo");
		return new Vetor(this.x - v.x,
						 this.y - v.y,
						 this.z - v.z);
	}
	
	public Vetor somar(Vetor v) {
		if(v == null)
			throw new RuntimeException("Vetor nulo");
		return new Vetor(this.x + v.x,
						 this.y + v.y,
						 this.z + v.z);
	}
	
	public Vetor multplicarEscalar(double k) {
		return new Vetor(k*this.x, 
					 	 k*this.y, 
						 k*this.z);
	}
	
	public boolean equals(Vetor v) {
		if(v == null) 
			throw new RuntimeException("Ponto nulo");
		double epsilon = 0.00000000001;
		if(Math.abs(this.x - v.x) < epsilon &&
		   Math.abs(this.y - v.y) < epsilon &&
		   Math.abs(this.z - v.z) < epsilon )
			return true;
		return false;
	}
	
	@Override
	public String toString() {
		return "["+x+"]["+y+"]["+z+"]";
	}
}
