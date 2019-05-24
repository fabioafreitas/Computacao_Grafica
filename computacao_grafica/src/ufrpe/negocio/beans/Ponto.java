package ufrpe.negocio.beans;

public class Ponto {
	private double x,y,z;
	
	public Ponto(double x,double y,double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Ponto menos Ponto dá Vetor
	 * @param ponto
	 * @return
	 */
	public Vetor subtrair(Ponto ponto) {
		if(ponto == null)
			throw new RuntimeException("ponto é nulo");
		return new Vetor(this.x - ponto.x,
						 this.y - ponto.y,
						 this.z - ponto.z);
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

	public boolean equals(Ponto p) {
		if(p == null) 
			throw new RuntimeException("Ponto nulo");
		double epsilon = 0.00000000001;
		if(Math.abs(this.x - p.x) < epsilon &&
		   Math.abs(this.y - p.y) < epsilon &&
		   Math.abs(this.z - p.z) < epsilon )
			return true;
		return false;
	}
	
	@Override
	public String toString() {
		return "["+x+"]["+y+"]["+z+"]";
	}
	
}
