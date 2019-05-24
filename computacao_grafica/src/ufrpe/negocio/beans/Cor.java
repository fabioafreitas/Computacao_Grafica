package ufrpe.negocio.beans;

public class Cor {
	private double red;
	private double green;
	private double blue;
	
	public Cor(double red, double green, double blue) {
		super();
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public double getRed() {
		return red;
	}

	public void setRed(double red) {
		this.red = red;
	}

	public double getGreen() {
		return green;
	}

	public void setGreen(double green) {
		this.green = green;
	}

	public double getBlue() {
		return blue;
	}

	public void setBlue(double blue) {
		this.blue = blue;
	}
}
