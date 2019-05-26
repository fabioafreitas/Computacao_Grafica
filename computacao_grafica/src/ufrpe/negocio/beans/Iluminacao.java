package ufrpe.negocio.beans;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Iluminacao {
	public Cor luzAmb, luzL;
	public double ka, ks, eta;
	public Vetor kd, od;
	public Ponto pl;
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
	}
}

