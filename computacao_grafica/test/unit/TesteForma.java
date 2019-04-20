package unit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

import beans.Forma;
import beans.Ponto;
import negocio.exception.EntradaInvalidaException;
import negocio.exception.FormatoInvalidoException;
import negocio.exception.NegocioException;

public class TesteForma {
	ArrayList<Ponto> v;
	ArrayList<int[]> i;
	Forma f;
	
	@Test
	public void testInstanciarFormaValida() throws NegocioException {
		v = new ArrayList<Ponto>();
		v.add(new Ponto(1, 1, 1));
		v.add(new Ponto(2, 1, 2));
		v.add(new Ponto(2, 2, 3));
		i = new ArrayList<int[]>();
		i.add(new int[] {0,1,2});
		f = new Forma(v, i);
		assertNotNull(f);
	}

	
	@Test(expected=RuntimeException.class)
	public void testInstanciarFormaArrayListsNulos1() throws NegocioException {
		v = new ArrayList<Ponto>();
		f = new Forma(v, null);
	}
	
	@Test(expected=RuntimeException.class)
	public void testInstanciarFormaArrayListsNulos2() throws NegocioException {
		f = new Forma(null, null);
	}
	
	/**
	 * Forma que não possui triangulos
	 */
	@Test
	public void testInstanciarFormaSemTriangulos() throws NegocioException {
		v = new ArrayList<Ponto>();
		v.add(new Ponto(1, 1, 1));
		v.add(new Ponto(2, 1, 2));
		v.add(new Ponto(2, 2, 3));
		i = new ArrayList<int[]>();
		f = new Forma(v, i);
		assertNotNull(f);
	}

	/**
	 * Forma com vetor de indices de triangulos com 
	 * tamanho diferente de 3. Precisa ser 3, pois cada
	 * indice represente um ponto do ArratList de vertices
	 */
	@Test(expected=FormatoInvalidoException.class)
	public void testInstanciarFormaVetorIndicesDiferenteDeTres() throws NegocioException {
		v = new ArrayList<Ponto>();
		v.add(new Ponto(1, 1, 1));
		v.add(new Ponto(2, 1, 2));
		v.add(new Ponto(2, 2, 3));
		v.add(new Ponto(5, 6, 3));
		i = new ArrayList<int[]>();
		i.add(new int[] {0,1,2,3});
		f = new Forma(v, i);
	}

	@Test(expected=EntradaInvalidaException.class)
	public void testInstanciarFormaVetorIndicesNegativos() throws NegocioException {
		v = new ArrayList<Ponto>();
		v.add(new Ponto(1, 1, 1));
		v.add(new Ponto(2, 1, 2));
		v.add(new Ponto(2, 2, 3));
		i = new ArrayList<int[]>();
		i.add(new int[] {0,-1,-2});
		f = new Forma(v, i);
	}
	
	@Test(expected=EntradaInvalidaException.class)
	public void testInstanciarFormaVetorIndicesAcimaDoLimite() throws NegocioException {
		v = new ArrayList<Ponto>();
		v.add(new Ponto(1, 1, 1));
		v.add(new Ponto(2, 1, 2));
		v.add(new Ponto(2, 2, 3));
		i = new ArrayList<int[]>();
		i.add(new int[] {0,1,30});
		f = new Forma(v, i);
	}
	
	@Test
	public void testGetVertices() throws NegocioException {
		v = new ArrayList<Ponto>();
		Ponto p1 = new Ponto(1, 1, 1);
		Ponto p2 = new Ponto(2, 1, 2);
		v.add(p1);
		v.add(p2);
		i = new ArrayList<int[]>();
		f = new Forma(v, i);
		ArrayList<Ponto> v2 = f.getVertices();
		assertTrue(v2.get(0).equals(p1));
		assertTrue(v2.get(1).equals(p2));
		
	}

	
	@Test
	public void testGetIndiceTriangulos() throws NegocioException {
		v = new ArrayList<Ponto>();
		v.add(new Ponto(1, 1, 1));
		v.add(new Ponto(2, 1, 2));
		v.add(new Ponto(2, 2, 3));
		i = new ArrayList<int[]>();
		i.add(new int[] {0,1,2});
		f = new Forma(v, i);
		ArrayList<int[]> i2 = f.getIndiceTriangulos();
		assertTrue(i2.get(0)[0] == 0);
		assertTrue(i2.get(0)[1] == 1);
		assertTrue(i2.get(0)[2] == 2);
	}
	
	@Test
	public void testSetVerticesIndiceValido() throws NegocioException {
		v = new ArrayList<Ponto>();
		v.add(new Ponto(1, 1, 1));
		v.add(new Ponto(2, 1, 2));
		v.add(new Ponto(2, 2, 3));
		i = new ArrayList<int[]>();
		i.add(new int[] {0,1,2});
		f = new Forma(v, i);
		f.setVertices(0, new Ponto(0, 0, 0));
	}
	
	@Test(expected=EntradaInvalidaException.class)
	public void testSetVerticesIndiceNegativo() throws NegocioException {
		v = new ArrayList<Ponto>();
		v.add(new Ponto(1, 1, 1));
		v.add(new Ponto(2, 1, 2));
		v.add(new Ponto(2, 2, 3));
		i = new ArrayList<int[]>();
		i.add(new int[] {0,1,2});
		f = new Forma(v, i);
		f.setVertices(-1, new Ponto(0, 0, 0));
	}
	
	@Test(expected=EntradaInvalidaException.class)
	public void testSetVerticesIndiceAcimaDoLimite() throws NegocioException {
		v = new ArrayList<Ponto>();
		v.add(new Ponto(1, 1, 1));
		v.add(new Ponto(2, 1, 2));
		v.add(new Ponto(2, 2, 3));
		i = new ArrayList<int[]>();
		i.add(new int[] {0,1,2});
		f = new Forma(v, i);
		f.setVertices(3, new Ponto(0, 0, 0));
	}
	
	@Test(expected=RuntimeException.class)
	public void testSetVerticePontoNulo() throws NegocioException {
		v = new ArrayList<Ponto>();
		v.add(new Ponto(1, 1, 1));
		v.add(new Ponto(2, 1, 2));
		v.add(new Ponto(2, 2, 3));
		i = new ArrayList<int[]>();
		i.add(new int[] {0,1,2});
		f = new Forma(v, i);
		f.setVertices(0, null);
	}
}
