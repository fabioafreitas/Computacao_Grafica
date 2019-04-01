package view;

import beans.Matriz;
import beans.Ponto;
import beans.Triangulo;
import beans.Vetor;

public class PrimeiraQuestao {
	
	public static void a() {
		Matriz matriz1 = new Matriz(new double[][] {{1.5, 2.5, 3.5}, {4.5, 5.5, 6.5}});
		Matriz matriz2 = new Matriz(new double[][] {{7.5, 8.5},{9.5, 10.5},{11.5, 12.5}});
		Matriz matriz3 = null;
		try {
			matriz3 = matriz1.multiplicar(matriz2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		matriz3.print();
	}

	public static void b() {
		Ponto p1 = new Ponto(3.5 ,1.5 ,2);
		Ponto p2 = new Ponto(1 ,2 ,1.5);
		Ponto p3 = p1.subtrair3D(p2);
		p3.print();
	}
	
	public static void c() {
		Vetor v1 = new Vetor(3.5 ,1.5 ,2);
		Vetor v2 = new Vetor(1 ,2 ,1.5);
		double result = v1.produtoEscalar3D(v2);
		System.out.println(result);
	}
	
	public static void d() {
		Vetor v1 = new Vetor(3.5 ,1.5 ,2);
		Vetor v2 = new Vetor(1 ,2 ,1.5);
		Vetor v3 = null;
		try {
			v3 = v1.produtoVetorial3D(v2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		v3.print();
	}
	
	public static void e() {
		Vetor v1 = new Vetor(3.5 ,1.5 ,2);
		double norma = v1.norma();
		System.out.println(norma);
	}
	
	public static void f() {
		Vetor v1 = new Vetor(3.5 ,1.5 ,2);
		Vetor normalizado = v1.normalizar();
		normalizado.print();
	}
	
	public static void g() {
		Ponto baricentrica = new Ponto(-0.25, 0.75);
		Ponto a = new Ponto(-1,1);
		Ponto b = new Ponto(0,-1);
		Ponto c = new Ponto(1,1);
		Triangulo triangulo = new Triangulo(a, b, c);
		Ponto coordenada = null;
		try {
			coordenada = triangulo.coordenadaBaricentrica(baricentrica);
		} catch (Exception e) {
			e.printStackTrace();
		}
		coordenada.print();
	}
	
	public static void h() {
		Ponto coordenada = new Ponto(0.5, 0.25, 0.25);
		Ponto a = new Ponto(-1,1);
		Ponto b = new Ponto(0,-1);
		Ponto c = new Ponto(1,1);
		Triangulo triangulo = new Triangulo(a, b, c);
		Ponto baricentrica = null;
		try {
			baricentrica = triangulo.pontoCoordenadaBaricentrica(coordenada);
		} catch (Exception e) {
			e.printStackTrace();
		}
		baricentrica.print();
	}
	
	
	public static void main(String[] args) {
		// Adicionar a função para testar
	}
}
