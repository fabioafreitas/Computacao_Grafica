package entrega1;

import negocio.beans.Matriz;
import negocio.beans.Ponto;
import negocio.beans.Triangulo;
import negocio.beans.Vetor;

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
		System.out.println(matriz3);
	}

	public static void b() {
		Ponto p1 = new Ponto(3.5 ,1.5 ,2);
		Ponto p2 = new Ponto(1 ,2 ,1.5);
		Vetor v = p1.subtrair(p2);
		System.out.println(v);
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
			v3 = v1.produtoVetorial(v2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(v3);
	}
	
	public static void e() {
		Vetor v1 = new Vetor(3.5 ,1.5 ,2);
		double norma = v1.norma();
		System.out.println(norma);
	}
	
	public static void f() {
		Vetor v1 = new Vetor(3.5 ,1.5 ,2);
		Vetor normalizado = v1.normalizar();
		System.out.println(normalizado);
	}
	
	public static void g() {
		Ponto baricentrica = new Ponto(-0.25, 0.75, 0);
		Ponto a = new Ponto(-1,1, 0);
		Ponto b = new Ponto(0,-1, 0);
		Ponto c = new Ponto(1,1, 0);
		Triangulo triangulo = null;
		Ponto coordenada = null;
		try {
			triangulo = new Triangulo(a, b, c);
			coordenada = triangulo.coordenadaBaricentrica(baricentrica);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(coordenada);
	}
	
	public static void h() {
		Ponto coordenada = new Ponto(0.5, 0.25, 0.25);
		Ponto a = new Ponto(-1,1, 0);
		Ponto b = new Ponto(0,-1, 0);
		Ponto c = new Ponto(1,1, 0);
		Triangulo triangulo = null;
		Ponto baricentrica = null;
		try {
			triangulo = new Triangulo(a, b, c);
			baricentrica = triangulo.pontoCartesianoBaricentrico(coordenada);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(baricentrica);
	}
	
	
	public static void main(String[] args) {
//		a();
//		System.out.println("\n");
//		b();
//		System.out.println("\n");
//		c();
//		System.out.println("\n");
//		d();
//		System.out.println("\n");
//		e();
//		System.out.println("\n");
//		f();
//		System.out.println("\n");
//		g();
//		System.out.println("\n");
//		h();
		
		Vetor u = new Vetor(Math.sqrt(2)/2, 0, Math.sqrt(2)/2);
		Vetor v = new Vetor(Math.sqrt(2)/2, 0, -Math.sqrt(2)/2);
		Vetor n = new Vetor(0, -1, 0);
		
		System.out.println("u = "+u);
		System.out.println("v = "+v);
		System.out.println("n = "+n);
		System.out.println();
		System.out.println("u = "+n.produtoVetorial(v));
		System.out.println("n = "+v.produtoVetorial(u));
		System.out.println("v = "+u.produtoVetorial(n));
		
	}
}
