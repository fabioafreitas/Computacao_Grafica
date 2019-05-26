package ufrpe.negocio.beans;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Iluminacao {
	private Cor luzAmb, luzL;
	private double ka, ks, eta;
	private Vetor kd, od;
	private Ponto pl;
	private BufferedReader reader;
	 
	public Iluminacao(String fileName) throws IOException {
		String separator = System.getProperty("file.separator");
		String path = "Iluminacao"+separator+fileName+".txt";
		reader = new BufferedReader(new FileReader(path));
		
		String line = reader.readLine();
		String split[] = line.split(" ");
		this.luzAmb = new Cor(Double.parseDouble(split[0]), 
							  Double.parseDouble(split[1]), 
							  Double.parseDouble(split[2]));
		
		line = reader.readLine();
		split = line.split(" ");
		this.luzL = new Cor(Double.parseDouble(split[0]), 
							Double.parseDouble(split[1]), 
							Double.parseDouble(split[2]));
		
		line = reader.readLine();
		this.ka = Double.parseDouble(line);
		
		line = reader.readLine();
		this.ks = Double.parseDouble(line);
		
		line = reader.readLine();
		this.eta = Double.parseDouble(line);
		
		line = reader.readLine();
		split = line.split(" ");
		this.kd = new Vetor(Double.parseDouble(split[0]), 
							Double.parseDouble(split[1]), 
							Double.parseDouble(split[2]));
		
		line = reader.readLine();
		split = line.split(" ");
		this.od = new Vetor(Double.parseDouble(split[0]), 
							Double.parseDouble(split[1]), 
							Double.parseDouble(split[2]));
		
		line = reader.readLine();
		split = line.split(" ");
		this.pl = new Ponto(Double.parseDouble(split[0]), 
							Double.parseDouble(split[1]), 
							Double.parseDouble(split[2]));
		
		//kd = kd.normalizar();
		//od = od.normalizar();
		reader.close();
	}
	
	public Iluminacao(Cor luzAmb, Cor luzL, double ka, double ks, double eta, Vetor kd, Vetor od, Ponto pl) {
		super();
		this.luzAmb = luzAmb;
		this.luzL = luzL;
		this.ka = ka;
		this.ks = ks;
		this.eta = eta;
		this.kd = kd;
		this.od = od;
		this.pl = pl;
	}



	public Cor getLuzAmb() {
		return luzAmb;
	}

	public Cor getLuzL() {
		return luzL;
	}

	public double getKa() {
		return ka;
	}

	public double getKs() {
		return ks;
	}

	public double getEta() {
		return eta;
	}

	public Vetor getKd() {
		return kd;
	}

	public Vetor getOd() {
		return od;
	}

	public Ponto getPl() {
		return pl;
	}

	public BufferedReader getReader() {
		return reader;
	}

	@Override
	public String toString() {
		return "Iluminacao [luzAmb=" + luzAmb + ", luzL=" + luzL + ", ka=" + ka + ", ks=" + ks + ", eta=" + eta
				+ ", kd=" + kd + ", od=" + od + ", pl=" + pl + ", reader=" + reader + "]";
	}
	
	
}

