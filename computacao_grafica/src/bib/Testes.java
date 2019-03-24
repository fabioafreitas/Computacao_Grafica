package bib;

import java.sql.Blob;

import bib.Biblioteca;

public class Testes {
	public static void printMatriz(double m[][]) {
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[0].length; j++) {
				System.out.print("["+m[i][j]+"]");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static void printArray(double[] v) {
		for (int i = 0; i < v.length; i++) {
			System.out.print("["+v[i]+"]");
		}
		System.out.println();
	}
	
	public static void a() {
		double m1[][] = {{1.5, 2.5, 3.5}, 
						 {4.5, 5.5, 6.5}};
		double m2[][] = {{7.5, 8.5},
						 {9.5, 10.5},
						 {11.5, 12.5}};
		double m3[][] = Biblioteca.multiplicarMatriz(m1, m2);
		printMatriz(m3);
	}

	public static void b() {
		double[] p1 = {3.5 ,1.5 ,2};
		double[] p2 = {1 ,2 ,1.5};
		double[] p3 = Biblioteca.subtrairPontos3D(p1, p2);
		printArray(p3);
	}
	
	public static void c() {
		double[] v1 = {3.5 ,1.5 ,2};
		double[] v2 = {1 ,2 ,1.5};
		double result = Biblioteca.produtoEscalar3D(v1, v2);
		System.out.println(result);
	}
	
	public static void d() {
		double[] v1 = {3.5 ,1.5 ,2};
		double[] v2 = {1 ,2 ,1.5};
		double[] v3 = Biblioteca.produtoVetorial3D(v1, v2);
		printArray(v3);
	}
	
	public static void e() {
		double[] v1 = {3.5 ,1.5 ,2};
		double norma = Biblioteca.normaVetor3D(v1);
		printArray(v1);
		System.out.println(norma);
	}
	
	public static void f() {
		double[] v1 = {3.5 ,1.5 ,2};
		double[] normalizado = Biblioteca.normalizarVetor3D(v1);
		printArray(normalizado);
	}
	
	public static void g() {
		double[] p = {-0.25, 0.75};
		double[] a = {-1,1};
		double[] b = {0,-1};
		double[] c = {1,1};
		double[] coord = Biblioteca.coordenadaBaricentrica2D(p, a, b, c);
		printArray(coord);
	}
	
	public static void h() {
		double[] a = {-1,1};
		double[] b = {0,-1};
		double[] c = {1,1};
		double[] coord = {0.5, 0.25, 0.25};
		double[] p = Biblioteca.pontoCoordenadaBaricentrica(coord, a, b, c);
		printArray(p);
	}
	
	
	public static void main(String[] args) {
		// Adicionar a função para testar
	}
}
