package unit;

/**
 * Testando 100% de comandos e de decisão
 * @author fabio
 *
 */
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import negocio.beans.Ponto;
import negocio.beans.Vetor;

public class TestePonto {
	
	Ponto p1, p2;
	
	@Test
	public void testInstanciar() {
		p1 = new Ponto(1, 1, 1);
		assertNotNull(p1);
	}
	
	@Test
	public void testGetX() {
		p1 = new Ponto(1, 2, 3);
		assertEquals(1, p1.getX(), 2);
	}
	
	@Test
	public void testGetY() {
		p1 = new Ponto(1, 2, 3);
		assertEquals(2, p1.getX(), 2);
	}
	
	@Test
	public void testGetZ() {
		p1 = new Ponto(1, 2, 3);
		assertEquals(3, p1.getX(), 2);
	}
	
	@Test
	public void testSetX() {
		p1 = new Ponto(1, 2, 3);
		p1.setX(10);
		assertEquals(10, p1.getX(), 2);
	}
	
	@Test
	public void testSetY() {
		p1 = new Ponto(1, 2, 3);
		p1.setY(10);
		assertEquals(10, p1.getY(), 2);
	}
	
	@Test
	public void testSetZ() {
		p1 = new Ponto(1, 2, 3);
		p1.setZ(10);
		assertEquals(10, p1.getZ(), 2);
	}
	
	@Test
	public void testToString() {
		p1 = new Ponto(1, 2, 3);
		assertNotNull(p1.toString());
	}
	
	@Test
	public void testMetodoSubtrair() {
		p1 = new Ponto(1, 1, 1);
		p2 = new Ponto(2, 2, 2);
		Vetor v = p1.subtrair(p2);
		assertEquals(-1, v.getX(), 2);
		assertEquals(-1, v.getY(), 2);
		assertEquals(-1, v.getZ(), 2);
	}
	
	@Test(expected=RuntimeException.class)
	public void testMetodoSubtrairObjetoNulo() {
		p1 = new Ponto(1, 1, 1);
		p2 = null;
		Vetor v = p1.subtrair(p2);
	}
	
	@Test
	public void testEqualsTrue() {
		p1 = new Ponto(1, 1, 1);
		p2 = new Ponto(1, 1, 1);
		assertTrue(p1.equals(p2));
	}
	
	@Test
	public void testEqualsFalse1() {
		p1 = new Ponto(1, 1, 1);
		p2 = new Ponto(2, 2, 2);
		assertFalse(p1.equals(p2));
	}
	
	@Test
	public void testEqualsFalse2() {
		p1 = new Ponto(1, 1, 1);
		p2 = new Ponto(1, 2, 2);
		assertFalse(p1.equals(p2));
	}
	
	@Test
	public void testEqualsFalse3() {
		p1 = new Ponto(1, 1, 1);
		p2 = new Ponto(1, 1, 2);
		assertFalse(p1.equals(p2));
	}

	
	@Test(expected=RuntimeException.class)
	public void testEqualsObjetoNulo() {
		p1 = new Ponto(1, 1, 1);
		p2 = null;
		boolean a = p1.equals(p2);
	}
}
