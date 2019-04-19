package beans;

public class Ponto {
	private double x,y,z;
	
	public Ponto(double x,double y,double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/*
	 * Subtrai o this.ponto de um ponto de entrada
	 */
	public Ponto subtrair(Ponto ponto2) {
		if(ponto2 == null)
			throw new RuntimeException("ponto2 é nulo");
		Ponto ponto3 = new Ponto(0,0,0);
		ponto3.x = this.x - ponto2.x;
		ponto3.y = this.y - ponto2.y;
		ponto3.z = this.z - ponto2.z;
		return ponto3;
	}
	
	public void print() {
		System.out.print("["+x+"]["+y+"]["+z+"]");
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
		if(this.x == p.x && this.y == p.y && this.z == p.z)
			return true;
		return false;
	}
	
}
