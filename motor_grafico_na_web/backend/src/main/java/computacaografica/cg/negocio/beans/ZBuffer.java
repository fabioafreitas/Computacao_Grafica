package computacaografica.cg.negocio.beans;

public class ZBuffer {
	private Cor cor;
	private double depth;
	
	public ZBuffer(Cor cor, double depth) {
		super();
		this.cor = cor;
		this.depth = depth;
	}
	
	public Cor getCor() {
		return cor;
	}
	
	public void setCor(Cor cor) {
		this.cor = cor;
	}
	
	public double getDepth() {
		return depth;
	}
	
	public void setDepth(double depth) {
		this.depth = depth;
	}

	@Override
	public String toString() {
		return "ZBuffer [color=" + cor + ", depth=" + depth + "]";
	}
	
	
}
