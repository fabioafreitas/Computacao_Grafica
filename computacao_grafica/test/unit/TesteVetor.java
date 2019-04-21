package unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import negocio.beans.Vetor;

/**
 * Testando 100% de comandos e de decisão
 * @author fabio
 *
 */
public class TesteVetor {

	Vetor v1;
	Vetor v2;
	
	@Test
	public void testInstancia() {
		v1 = new Vetor(1, 2, 3);
		assertNotNull(v1);
	}
	
	@Test
	public void testGetX() {
		v1 = new Vetor(1, 2, 3);
		assertEquals(1, v1.getX(), 2);
	}
	
	@Test
	public void testGetY() {
		v1 = new Vetor(1, 2, 3);
		assertEquals(2, v1.getX(), 2);
	}
	
	@Test
	public void testGetZ() {
		v1 = new Vetor(1, 2, 3);
		assertEquals(3, v1.getX(), 2);
	}
	
	@Test
	public void testSetX() {
		v1 = new Vetor(1, 2, 3);
		v1.setX(10);
		assertEquals(10, v1.getX(), 2);
	}
	
	@Test
	public void testSetY() {
		v1 = new Vetor(1, 2, 3);
		v1.setY(10);
		assertEquals(10, v1.getY(), 2);
	}
	
	@Test
	public void testSetZ() {
		v1 = new Vetor(1, 2, 3);
		v1.setZ(10);
		assertEquals(10, v1.getZ(), 2);
	}
	
	@Test
	public void testToString() {
		v1 = new Vetor(1, 2, 3);
		assertNotNull(v1.toString());
	}
	
	@Test
	public void testProdutoEscalar3D() {
		v1 = new Vetor(1, 2, 3);
		v2 = new Vetor(1, 1, 1);
		assertEquals(6, v1.produtoEscalar3D(v2), 2);
	}
	
	@Test(expected=RuntimeException.class)
	public void testProdutoEscalar3DObjetoNulo() {
		v1 = new Vetor(1, 2, 3);
		v2 = null;
		double a = v1.produtoEscalar3D(v2);
	}

	@Test
	public void testProdutoEscalar2D() {
		v1 = new Vetor(1, 2, 0);
		v2 = new Vetor(1, 1, 0);
		assertEquals(3, v1.produtoEscalar2D(v2), 2);
	}
	
	@Test(expected=RuntimeException.class)
	public void testProdutoEscalar2DObjetoNulo() {
		v1 = new Vetor(1, 2, 3);
		v2 = null;
		double a = v1.produtoEscalar2D(v2);
	}
	
	@Test
	public void testProdutoVetorial() {
		Vetor v1 = new Vetor(3.5 ,1.5 ,2);
		Vetor v2 = new Vetor(1 ,2 ,1.5);
		Vetor v3 = v1.produtoVetorial(v2);
		assertEquals(-1.75, v3.getX(), 2);
		assertEquals(-3.25, v3.getY(), 2);
		assertEquals(5.5, v3.getZ(), 2);
	}
	
	@Test(expected=RuntimeException.class)
	public void testProdutoVetorialObjetoNulo() {
		Vetor v1 = new Vetor(3.5 ,1.5 ,2);
		Vetor v2 = null;
		Vetor v3 = v1.produtoVetorial(v2);
	}
	
	@Test
	public void testNorma() {
		v1 = new Vetor(1, 1, 1);
		assertEquals(Math.sqrt(3), v1.norma(), 2);
	}
	
	@Test
	public void testNormalizar() {
		v1 = new Vetor(1, 1, 1);
		v2 = v1.normalizar();
		assertEquals(1/Math.sqrt(3), v2.getX(), 2);
		assertEquals(1/Math.sqrt(3), v2.getY(), 2);
		assertEquals(1/Math.sqrt(3), v2.getZ(), 2);
	}
	
	@Test
	public void testEqualsVetorIgual() {
		v1 = new Vetor(1, 1, 1);
		v2 = new Vetor(1, 1, 1);
		assertTrue(v1.equals(v2));
	}
	
	@Test
	public void testEqualsVetorDiferente1() {
		v1 = new Vetor(1, 1, 1);
		v2 = new Vetor(2, 2, 2);
		assertFalse(v1.equals(v2));
	}
	
	@Test
	public void testEqualsVetorDiferente2() {
		v1 = new Vetor(1, 1, 1);
		v2 = new Vetor(1, 2, 2);
		assertFalse(v1.equals(v2));
	}
	
	@Test
	public void testEqualsVetorDiferente3() {
		v1 = new Vetor(1, 1, 1);
		v2 = new Vetor(1, 1, 2);
		assertFalse(v1.equals(v2));
	}
	
	@Test(expected=RuntimeException.class)
	public void testEqualsObjetoNulo() {
		v1 = new Vetor(1, 1, 1);
		v2 = null;
		boolean a = v1.equals(v2);
	}
	
	@Test
	public void testSubtrairNaoNulo() {
		v1 = new Vetor(1, 1, 1);
		v2 = v1.subtrair(new Vetor(1, 1, 1));
		Vetor v3 = new Vetor(0, 0, 0);
		assertTrue(v2.equals(v3));
	}
	
	@Test(expected=RuntimeException.class)
	public void testSubtrairNulo() {
		v1 = new Vetor(1, 1, 1);
		v2 = v1.subtrair(null);
	}
	
	@Test
	public void testSomarNaoNulo() {
		v1 = new Vetor(1, 1, 1);
		v2 = v1.somar(new Vetor(1, 1, 1));
		Vetor v3 = new Vetor(2, 2, 2);
		assertTrue(v2.equals(v3));
	}
	
	@Test(expected=RuntimeException.class)
	public void testSomarNulo() {
		v1 = new Vetor(1, 1, 1);
		v2 = v1.somar(null);
	}
	
	@Test
	public void testMultiplicarEscalar() {
		v1 = new Vetor(1, 1, 1);
		v2 = v1.multplicarEscalar(2);
		Vetor v3 = new Vetor(2, 2, 2);
		assertTrue(v2.equals(v3));
	}
}
