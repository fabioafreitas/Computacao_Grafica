package beans;

public class CameraVirtual {
	private Ponto pontoC;
	private Vetor vetorN, vetorV;
	private double d, hx, hy;
	
	public CameraVirtual(Ponto pontoC, Vetor vetorN, Vetor vetorV, double d, double hx, double hy) {
		super();
		if(pontoC == null || vetorN == null || vetorV == null)
			throw new RuntimeException("Alguma entrada eh nula");
		this.pontoC = pontoC;
		this.vetorN = vetorN;
		this.vetorV = vetorV;
		this.d = d;
		this.hx = hx;
		this.hy = hy;
	}
	
	

}
