package ufrpe.negocio;

import java.util.ArrayList;
import java.util.List;

import ufrpe.negocio.beans.CameraVirtual;
import ufrpe.negocio.beans.Matriz;
import ufrpe.negocio.beans.Ponto;
import ufrpe.negocio.beans.Triangulo;
import ufrpe.negocio.beans.Vetor;
import ufrpe.negocio.exception.NegocioException;

public class Biblioteca {
	/**
	 * Realiza a mudanca de base de um ponto em coordenadas mundiais para coordenadas de vista
	 * @param pMundial
	 * @param cam
	 * @return ponto em coordenadas de vista
	 * @throws NegocioException
	 */
	public static Ponto getCoordenadasVista(Ponto pMundial, CameraVirtual cam) throws NegocioException {
		if(pMundial == null)
			throw new RuntimeException("Ponto nulo");
		if(cam == null)
			throw new RuntimeException("CameraVirtual nula");
		
		Matriz mudancaBase = new Matriz(new double[][] {
			{cam.getVetorU().getX(), cam.getVetorU().getY(), cam.getVetorU().getZ()},
			{cam.getVetorV().getX(), cam.getVetorV().getY(), cam.getVetorV().getZ()},
			{cam.getVetorN().getX(), cam.getVetorN().getY(), cam.getVetorN().getZ()}
		});
		
		Vetor v = pMundial.subtrair(cam.getPontoC());
		
		Matriz vetorEntrada = new Matriz(new double[][] {
			{v.getX()},
			{v.getY()},
			{v.getZ()}
		});
		
		
		Matriz vista = mudancaBase.multiplicar(vetorEntrada);
		return new Ponto(vista.getMatriz()[0][0], 
						 vista.getMatriz()[1][0], 
						 vista.getMatriz()[2][0]);		
	}
	
	/**
	 * Recebe um ponto, em coordenada de vista, e retorna sua projecaoo em perspectiva
	 * @param pVista
	 * @param cam
	 * @return projecao em perspectiva do ponto de vista
	 */
	public static Ponto getProjecaoPerspectiva(Ponto pVista, CameraVirtual cam) {
		if(pVista == null)
			throw new RuntimeException("Ponto nulo");
		if(cam == null)
			throw new RuntimeException("CameraVirtual nula");
		return new Ponto(cam.getD() * (pVista.getX()/pVista.getZ()),
						 cam.getD() * (pVista.getY()/pVista.getZ()),
						 pVista.getZ()); //cam.getD());
	}	
	
	/**
	 * Normaliza um ponto em projecao perspectiva
	 * @param pPerspectiva
	 * @param cam
	 * @return ponto em projecao perspectiva normalizado
	 */
	public static Ponto getPerspectivaNormalizada(Ponto pPerspectiva, CameraVirtual cam) {
		if(pPerspectiva == null)
			throw new RuntimeException("Ponto nulo");
		if(cam == null)
			throw new RuntimeException("CameraVirtual nula");
		return new Ponto(pPerspectiva.getX()/cam.getHx(),
						 pPerspectiva.getY()/cam.getHy(),
						 pPerspectiva.getZ());//cam.getD());
	}
	/**
	 * Converte um ponto normalizado (em projecao perspectiva) para coordenadas de tela
	 * @param pPerspctiva
	 * @param width
	 * @param height
	 * @return coordenada de tela de um ponto
	 */
	public static Ponto getCoordenadaTela(Ponto ponto, int width, int height) {
		if(ponto == null) 
			throw new RuntimeException("Ponto nulo");
		return new Ponto(Math.floor( ((ponto.getX()+1)/2)*width + 0.5 ),
						 Math.floor( height - ((ponto.getY()+1)/2)*height + 0.5 ),
						 ponto.getZ());
	}
	
	/**
	 * Retorna a coordenada baricentrica referente a um ponto p
	 * baseado num triangulo
	 * @param ponto
	 * @param p1
	 * @param p2
	 * @param p3
	 * @return
	 * @throws NegocioException
	 */
	public static Ponto coordenadaBaricentrica(Ponto ponto, Ponto p1, Ponto p2,  Ponto p3) throws NegocioException{
		double a = p1.getX() - p3.getX();
		double b = p2.getX() - p3.getX();
		double c = p1.getY() - p3.getY();
		double d = p2.getY() - p3.getY();
		double det = 1/(a*d-b*c);
		double m1[][] = {{det*d, det*(-b)},{det*(-c), det*a}};
		double m2[][] = {{ponto.getX()-p3.getX()},{ponto.getY()-p3.getY()}};
		Matriz matriz1 = new  Matriz(m1);
		Matriz matriz2 = matriz1.multiplicar(new Matriz(m2));
		return new Ponto(matriz2.getMatriz()[0][0],
						 matriz2.getMatriz()[1][0], 
						 1 - matriz2.getMatriz()[0][0] - matriz2.getMatriz()[1][0]);
	}
	
	/**
	 * Retorna o ponto cartesiano referente a uma coordenada baricentrica
	 * @param coordBaricentrica
 	 * @param p1
	 * @param p2
	 * @param p3
	 * @return
	 * @throws NegocioException
	 */
	public static Ponto getCartesianoDaBaricentrica(Ponto coordBaricentrica, Ponto p1, Ponto p2,  Ponto p3) throws NegocioException{
		if(coordBaricentrica == null)
			throw new RuntimeException("Ponto nulo");
		double a, b, c;
		a = coordBaricentrica.getX();
		b = coordBaricentrica.getY();
		c = coordBaricentrica.getZ();
		return new Ponto(a*p1.getX()+b*p2.getX()+c*p3.getX(), 
						 a*p1.getY()+b*p2.getY()+c*p3.getY(),
						 a*p1.getZ()+b*p2.getZ()+c*p3.getZ());
	}
	
	/**
	 * Retorna o vetor normal à três pontos (triangulo)
	 * Ja normaliza este vetor
	 * @return vetor normal à triangulo, já normalizado
	 */
	public static Vetor getNormalDoTriangulo(Ponto p1, Ponto p2, Ponto p3) {
		Vetor a = p2.subtrair(p1);
		Vetor b = p3.subtrair(p1);
		Vetor c = a.produtoVetorial(b);
		return c.normalizar();
	}
	
	public static Vetor getNormalDoVertice(Ponto vertice, List<Triangulo> triangulos) {
		Vetor normalVertice = new Vetor(0,0,0);
		for (Triangulo triangulo : triangulos) {
			if( triangulo.pVista1.equals(vertice) || 
				triangulo.pVista2.equals(vertice) || 
				triangulo.pVista3.equals(vertice) ) {
				normalVertice = normalVertice.somar(triangulo.normal);
			}
		}
		return normalVertice.normalizar();
	}
	
	/**
	 * Retorna o baricentro de três pontos (triangulo)
	 * @param p1
	 * @param p2
	 * @param p3
	 * @return
	 */
	public static Ponto getBaricentro(Triangulo triangulo) {
		Ponto p1 = triangulo.pVista1;
		Ponto p2 = triangulo.pVista2;
		Ponto p3 = triangulo.pVista3;
		return new Ponto((p1.getX() + p2.getX() + p3.getX())/3,
						 (p1.getY() + p2.getY() + p3.getY())/3,
						 (p1.getZ() + p2.getZ() + p3.getZ())/3);
	}
	
	private static void intercala(int inicio, int meio, int fim, List<Triangulo> triangulos) {
		int i, j, k;
		i = inicio;
		j = meio;
		k = 0;
		List<Triangulo> aux = new ArrayList<Triangulo>();
		for (int l = 0; l < (fim-inicio); l++) {
			aux.add(new Triangulo());
		}
		while(i < meio && j < fim) {
			Ponto p1 = getBaricentro(triangulos.get(i));
			Ponto p2 = getBaricentro(triangulos.get(j));
			if(p1.getZ() <= p2.getZ()) {
				aux.set(k++, triangulos.get(i++));
			}
			else {
				aux.set(k++, triangulos.get(j++));
			}
		}
		while(i < meio) {
			aux.set(k++, triangulos.get(i++));
		}
		while(j < fim){
			aux.set(k++, triangulos.get(j++));
		}
		for(k=inicio ,i=0 ; k < fim ; k++, i++) {
			triangulos.set(k, aux.get(i));
		}
	}
	
	public static void mergesort (int inicio, int fim, List<Triangulo> triangulos) {
	   if (inicio < fim-1) {                 
	      int meio = (inicio + fim)/2;          
	      mergesort (inicio, meio, triangulos);        
	      mergesort (meio, fim, triangulos);        
	      intercala (inicio, meio, fim, triangulos);     
	   }
	}
}
