package bib;

import java.lang.Math;

public class Biblioteca {
	/*
	 * Recebe duas matrizes, as multiplica e retorna a matriz resultante
	 */
	public static double[][] multiplicarMatriz(double m1[][], double m2[][]) {
		int coluna1 = m1[0].length;
		int linha2 = m2.length; 
		if(coluna1 == linha2) {
			int linha1 = m1.length;
			int coluna2 = m2[0].length;
			double m3[][] = new double[linha1][coluna2];
			for (int i = 0; i < linha1; i++) {
				for (int j = 0; j < coluna2; j++) {
					m3[i][j] = 0;
					for (int k = 0; k < linha2; k++) { // Somatório de cada entrada da matriz
						m3[i][j] += m1[i][k]*m2[k][j];
					}
				}
			}
			return m3;
		}
		return null;
	}
	
	/*
	 * Recebe dois pontos e subtrai o primeiro do segundo
	 * LEMBRANDO QUE A SUBTRACAO ENTRE PONTOS GERA UM VETOR
	 */
	public static double[] subtrairPontos3D(double[] p1, double[] p2) {
		double[] p3 = new double[3];
		for (int i = 0; i < p3.length; i++) {
			p3[i] = p1[i] - p2[i];
		}
		return p3;
	}
	
	/*
	 * Recebe dois vetores e retorna o produto escalar entre eles
	 * O retorno eh um numero REAL
	 */
	public static double produtoEscalar3D(double[] v1, double[] v2) {
		double total = 0;
		for (int i = 0; i < v1.length; i++) {
			total += v1[i]*v2[i];
		}
		return total;
	}
	
	/*
	 * Recebe dois vetores e calcula o produto vetorial entre eles
	 * Retorna o vetor resultante
	 */
	public static double[] produtoVetorial3D(double[] v1, double[] v2) {
		double a = v1[0]; double b = v1[1]; double c = v1[2]; 
		double d = v2[0]; double e = v2[1]; double f = v2[2]; 
		double[] v3 = new double[3];
		v3[0] = b*f-c*e;
		v3[1] = c*d-a*f;
		v3[2] = a*e-b*d;
		return v3;
	}
	
	/*
	 * Retorna a norma (Comprimento) do vetor
	 */
	public static double normaVetor3D(double[] v) {
		double total = Math.sqrt(v[0]*v[0] + v[1]*v[1] + v[2]*v[2]);
		return total;
	}
	
	/*
	 * Retorna o vetor normalizado, com norma entre 0 e 1
	 */
	public static double[] normalizarVetor3D(double[] v) {
		double norma = normaVetor3D(v);
		double[] v2 = new double[3];
		v2[0] = v[0]/norma;
		v2[1] = v[1]/norma; 
		v2[2] = v[2]/norma;
		return v2;
	}
	
	/* Recebe um ponto P e tres pontos nao colineares, respectivamente
	 * Retorna a coordenada baricentrica
	 */
	public static double[] coordenadaBaricentrica2D(double[] p0, double[] p1, double[] p2, double[] p3) {
		if(verificarColinearidade(p1, p2, p3) == false) {
			double a = p1[0] - p3[0];
			double b = p2[0] - p3[0];
			double c = p1[1] - p3[1];
			double d = p2[1] - p3[1];
			double det = 1/(a*d-b*c);
			double m1[][] = {{det*d, det*(-b)},{det*(-c), det*a}};
			double m2[][] = {{p0[0]-p3[0]},{p0[1]-p3[1]}};
			m2 = multiplicarMatriz(m1, m2);
			double[] coordenada = {m2[0][0], m2[1][0], 1 - m2[0][0]- m2[1][0]};
			return coordenada;
		}
		return null;
	}
	
	/* Recebe a coordenada baricentrica e tres pontos nao colineares, respectivamente
	 * Retorna o ponto correspondente a combinacao
	 */
	public static double[] pontoCoordenadaBaricentrica(double[] bar, double[] p1, double[] p2, double[] p3) {
		if(verificarColinearidade(p1, p2, p3) == false) {
			double a = bar[0], b = bar[1], c = bar[2];
			double[] ponto = {a*p1[0]+b*p2[0]+c*p3[0],
							  a*p1[1]+b*p2[1]+c*p3[1]};
			return ponto;
		}
		return null;
	}
	
	/* Verifica se os tres pontos 2d sao colineares
	 * Sao colineares, se a determinante da matriz é igual a 0
	 * [x1 y1 1]
	 * [x2 y2 1]
	 * [x3 y3 1]
	 */
	
	public static boolean verificarColinearidade(double[] p1, double[] p2, double[] p3) {
		double result = (p1[0]*p2[1] + p1[1]*p3[0] + p2[0]*p3[1])
						-(p3[0]*p2[1] + p3[1]*p1[0] + p2[0]*p1[1]);
		if(result == 0) return true;
		else return false;
	}
}
