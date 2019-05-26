package ufrpe.negocio.beans;

import javafx.scene.paint.Color;

public class ZBuffer {
	private Color color;
	private double depth;
	
	public ZBuffer(Color color, double depth) {
		super();
		this.color = color;
		this.depth = depth;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public double getDepth() {
		return depth;
	}
	
	public void setDepth(double depth) {
		this.depth = depth;
	}

	@Override
	public String toString() {
		return "ZBuffer [color=" + color + ", depth=" + depth + "]";
	}
	
	
}
