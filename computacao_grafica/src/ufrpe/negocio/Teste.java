package ufrpe.negocio;

import java.util.ArrayList;
import java.util.List;

import ufrpe.negocio.beans.Ponto;
import ufrpe.negocio.beans.Triangulo;

public class Teste {	
	public static void main(String[] args) {
		List<Triangulo> t = new ArrayList<Triangulo>();
		Ponto p1 = new Ponto(1, 1, 1);
		Ponto p2 = new Ponto(2, 2, 5);
		Ponto p3 = new Ponto(3, 4, 7);
		Ponto p4 = new Ponto(4, 4, 0);
		Ponto p5 = new Ponto(5, 1, 2);
		Ponto p6 = new Ponto(6, 6, 3);
		t.add(new Triangulo(null, null, null, p4, p5, p6, null, null, null, null));
		t.add(new Triangulo(null, null, null, p5, p6, p1, null, null, null, null));
		t.add(new Triangulo(null, null, null, p3, p4, p5, null, null, null, null));
		t.add(new Triangulo(null, null, null, p2, p3, p4, null, null, null, null));
		t.add(new Triangulo(null, null, null, p1, p2, p3, null, null, null, null));
		t.add(new Triangulo(null, null, null, p6, p1, p4, null, null, null, null));
		
		for (Triangulo triangulo : t) {
			System.out.println(Biblioteca.getBaricentro(triangulo));
		}
		
		Biblioteca.mergesort(0, t.size(), t);
		System.out.println();
		for (Triangulo triangulo : t) {
			System.out.println(Biblioteca.getBaricentro(triangulo));
		}
	}
}
