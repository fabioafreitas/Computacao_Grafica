package ufrpe.negocio.beans;

import javafx.scene.paint.Color;

public class ZBuffer {
	private Color c;
	private double depth;
	
	public ZBuffer(Color c, double depth) {
		super();
		this.c = c;
		this.depth = depth;
	}
	
	public Color getColor() {
		return c;
	}
	
	public void setColor(Color c) {
		this.c = c;
	}
	
	public double getDepth() {
		return depth;
	}
	
	public void setDepth(double depth) {
		this.depth = depth;
	}
}
