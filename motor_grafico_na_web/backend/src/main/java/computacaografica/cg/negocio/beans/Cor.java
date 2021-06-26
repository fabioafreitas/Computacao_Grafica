package computacaografica.cg.negocio.beans;

public class Cor {
	public double red;
	public double green;
	public double blue;
	
	public Cor(double red, double green, double blue) {
		super();
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	public Cor somar(Cor cor) {
		return new Cor( this.red+cor.red,
						this.green+cor.green,
						this.blue+cor.blue);
	}
	
	public void verificarRGB() {
		this.red = this.red > 255 ? 255 : this.red;
    	this.green = this.green > 255 ? 255 : this.green;
    	this.blue = this.blue > 255 ? 255 : this.blue;
    	this.red = this.red < 0 ? 0 : this.red;
    	this.green = this.green < 0 ? 0 : this.green;
    	this.blue = this.blue < 0 ? 0 : this.blue;
	}

	@Override
	public String toString() {
		return "Cor [red=" + red + ", green=" + green + ", blue=" + blue + "]";
	}
	
	
}
