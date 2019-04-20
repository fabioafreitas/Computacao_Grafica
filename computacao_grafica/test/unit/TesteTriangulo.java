package unit;

import static org.junit.Assert.*;

import org.junit.Test;

import beans.Ponto;
import beans.Triangulo;
import negocio.exception.NegocioException;
import negocio.exception.PontosColinearesException;

public class TesteTriangulo {
	Ponto p1, p2, p3;
	Triangulo t1, t2;
	
	@Test
	public void testInstanciarPontosValidos() throws NegocioException {
		p1 = new Ponto(1, 1, 0);
		p2 = new Ponto(1, 2, 0);
		p3 = new Ponto(2, 2, 0);
		t1 = new Triangulo(p1, p2, p3);
		assertNotNull(t1);
	}

	@Test(expected=RuntimeException.class)
	public void testInstanciarPontosNulos1() throws NegocioException {
		p1 = null;
		p2 = new Ponto(1, 2, 0);
		p3 = new Ponto(2, 2, 0);
		t1 = new Triangulo(p1, p2, p3);
	}
	
	@Test(expected=RuntimeException.class)
	public void testInstanciarPontosNulos2() throws NegocioException {
		p1 = new Ponto(1, 1, 0);
		p2 = null;
		p3 = new Ponto(2, 2, 0);
		t1 = new Triangulo(p1, p2, p3);
	}
	
	@Test(expected=RuntimeException.class)
	public void testInstanciarPontosNulos3() throws NegocioException {
		p1 = new Ponto(1, 1, 0);
		p2 = new Ponto(1, 2, 0);
		p3 = null;
		t1 = new Triangulo(p1, p2, p3);
	}
	
	@Test(expected=PontosColinearesException.class)
	public void testInstanciarPontosColineares() throws NegocioException {
		p1 = new Ponto(1, 1, 0);
		p2 = new Ponto(2, 2, 0);
		p3 = new Ponto(3, 3, 0);
		t1 = new Triangulo(p1, p2, p3);
	}
	
	@Test
	public void testGetP1() throws NegocioException {
		p1 = new Ponto(1, 1, 0);
		p2 = new Ponto(1, 2, 0);
		p3 = new Ponto(2, 2, 0);
		t1 = new Triangulo(p1, p2, p3);
		assertEquals(1,t1.getP1().getX(),2);
		assertEquals(1,t1.getP1().getY(),2);
		assertEquals(0,t1.getP1().getZ(),2);	
	}

	@Test
	public void testGetP2() throws NegocioException {
		p1 = new Ponto(1, 1, 0);
		p2 = new Ponto(1, 2, 0);
		p3 = new Ponto(2, 2, 0);
		t1 = new Triangulo(p1, p2, p3);
		assertEquals(2,t1.getP3().getX(),2);
		assertEquals(2,t1.getP3().getY(),2);
		assertEquals(0,t1.getP3().getZ(),2);		
	}

	@Test
	public void testGetP3() throws NegocioException {
		p1 = new Ponto(1, 1, 0);
		p2 = new Ponto(1, 2, 0);
		p3 = new Ponto(2, 2, 0);
		t1 = new Triangulo(p1, p2, p3);
		assertEquals(1,t1.getP2().getX(),2);
		assertEquals(2,t1.getP2().getY(),2);
		assertEquals(0,t1.getP2().getZ(),2);	
	}

	@Test
	public void testSetP1NaoColinear() throws NegocioException {
		p1 = new Ponto(1, 1, 0);
		p2 = new Ponto(1, 2, 0);
		p3 = new Ponto(2, 2, 0);
		t1 = new Triangulo(p1, p2, p3);
		t1.setP1(new Ponto(0, 0, 0));
		assertEquals(0,t1.getP1().getX(),2);
		assertEquals(0,t1.getP1().getY(),2);
		assertEquals(0,t1.getP1().getZ(),2);	
	}
	
	@Test(expected=PontosColinearesException.class)
	public void testSetP1Colinear() throws NegocioException {
		p1 = new Ponto(1, 1, 0);
		p2 = new Ponto(1, 2, 0);
		p3 = new Ponto(2, 2, 0);
		t1 = new Triangulo(p1, p2, p3);
		t1.setP1(new Ponto(0, 2, 0));
	}
	
	@Test(expected=RuntimeException.class)
	public void testSetP1Nulo() throws NegocioException {
		p1 = new Ponto(1, 1, 0);
		p2 = new Ponto(1, 2, 0);
		p3 = new Ponto(2, 2, 0);
		t1 = new Triangulo(p1, p2, p3);
		t1.setP1(null);
	}
	
	
	
	@Test
	public void testSetP2NaoColinear() throws NegocioException {
		p1 = new Ponto(1, 1, 0);
		p2 = new Ponto(1, 2, 0);
		p3 = new Ponto(2, 2, 0);
		t1 = new Triangulo(p1, p2, p3);
		t1.setP2(new Ponto(1, 3, 0));
		assertEquals(1,t1.getP2().getX(),2);
		assertEquals(3,t1.getP2().getY(),2);
		assertEquals(0,t1.getP2().getZ(),2);	
	}
	
	@Test(expected=PontosColinearesException.class)
	public void testSetP2Colinear() throws NegocioException {
		p1 = new Ponto(1, 1, 0);
		p2 = new Ponto(1, 2, 0);
		p3 = new Ponto(2, 2, 0);
		t1 = new Triangulo(p1, p2, p3);
		t1.setP2(new Ponto(3, 3, 0));
	}
	
	@Test(expected=RuntimeException.class)
	public void testSetP2Nulo() throws NegocioException {
		p1 = new Ponto(1, 1, 0);
		p2 = new Ponto(1, 2, 0);
		p3 = new Ponto(2, 2, 0);
		t1 = new Triangulo(p1, p2, p3);
		t1.setP2(null);
	}
	
	
	@Test
	public void testSetP3NaoColinear() throws NegocioException {
		p1 = new Ponto(1, 1, 0);
		p2 = new Ponto(1, 2, 0);
		p3 = new Ponto(2, 2, 0);
		t1 = new Triangulo(p1, p2, p3);
		t1.setP3(new Ponto(3, 3, 0));
		assertEquals(3,t1.getP3().getX(),2);
		assertEquals(3,t1.getP3().getY(),2);
		assertEquals(0,t1.getP3().getZ(),2);	
	}
	
	@Test(expected=PontosColinearesException.class)
	public void testSetP3Colinear() throws NegocioException {
		p1 = new Ponto(1, 1, 0);
		p2 = new Ponto(1, 2, 0);
		p3 = new Ponto(2, 2, 0);
		t1 = new Triangulo(p1, p2, p3);
		t1.setP3(new Ponto(1, 3, 0));
	}
	
	@Test(expected=RuntimeException.class)
	public void testSetP3Nulo() throws NegocioException {
		p1 = new Ponto(1, 1, 0);
		p2 = new Ponto(1, 2, 0);
		p3 = new Ponto(2, 2, 0);
		t1 = new Triangulo(p1, p2, p3);
		t1.setP3(null);
	}
	
	@Test
	public void testEqualsTrue() throws NegocioException {
		p1 = new Ponto(1, 1, 0);
		p2 = new Ponto(1, 2, 0);
		p3 = new Ponto(2, 2, 0);
		t1 = new Triangulo(p1, p2, p3);
		t2 = new Triangulo(p1, p2, p3);
		assertTrue(t1.equals(t2));
	}
	
	@Test
	public void testEqualsFalse1() throws NegocioException {
		p1 = new Ponto(1, 1, 0);
		p2 = new Ponto(1, 2, 0);
		p3 = new Ponto(2, 2, 0);
		t1 = new Triangulo(p1, p2, p3);
		p1 = new Ponto(1, 1, 0);
		p2 = new Ponto(1, 2, 0);
		p3 = new Ponto(2, 10, 0);
		t2 = new Triangulo(p1, p2, p3);
		assertFalse(t1.equals(t2));
	}
	
	@Test
	public void testEqualsFalse2() throws NegocioException {
		p1 = new Ponto(1, 1, 0);
		p2 = new Ponto(1, 2, 0);
		p3 = new Ponto(2, 2, 0);
		t1 = new Triangulo(p1, p2, p3);
		p1 = new Ponto(1, 1, 0);
		p2 = new Ponto(1, 10, 0);
		p3 = new Ponto(2, 2, 0);
		t2 = new Triangulo(p1, p2, p3);
		assertFalse(t1.equals(t2));
	}
	
	@Test
	public void testEqualsFalse3() throws NegocioException {
		p1 = new Ponto(1, 1, 0);
		p2 = new Ponto(1, 2, 0);
		p3 = new Ponto(2, 2, 0);
		t1 = new Triangulo(p1, p2, p3);
		p1 = new Ponto(1, 10, 0);
		p2 = new Ponto(1, 2, 0);
		p3 = new Ponto(2, 2, 0);
		t2 = new Triangulo(p1, p2, p3);
		assertFalse(t1.equals(t2));
	}
	
	@Test(expected=RuntimeException.class)
	public void testEqualsTrianguloNulo() throws NegocioException {
		p1 = new Ponto(1, 1, 0);
		p2 = new Ponto(1, 2, 0);
		p3 = new Ponto(2, 2, 0);
		t1 = new Triangulo(p1, p2, p3);
		t2 = null;
		boolean a = t1.equals(t2);
	}
	
	@Test
	public void testCoordenadaBaricentricaPontoNaoNulo() throws NegocioException {
		p1 = new Ponto(0, 0, 0);
		p2 = new Ponto(2, 0, 0);
		p3 = new Ponto(1, 1, 0);
		t1 = new Triangulo(p1, p2, p3);
		Ponto b = t1.coordenadaBaricentrica(new Ponto(0, 4, 0));
		assertEquals(-1, b.getX(), 2);
		assertEquals(-2, b.getY(), 2);
		assertEquals(4, b.getZ(), 2);
	}
	
	@Test(expected=RuntimeException.class)
	public void testCoordenadaBaricentricaPontoNulo() throws NegocioException {
		p1 = new Ponto(0, 0, 0);
		p2 = new Ponto(2, 0, 0);
		p3 = new Ponto(1, 1, 0);
		t1 = new Triangulo(p1, p2, p3);
		Ponto b = t1.coordenadaBaricentrica(null);
	}
	
	@Test
	public void testPontoCartesianoBaricentricoNaoNulo() throws NegocioException {
		p1 = new Ponto(0, 0, 0);
		p2 = new Ponto(2, 0, 0);
		p3 = new Ponto(1, 1, 0);
		t1 = new Triangulo(p1, p2, p3);
		Ponto c = t1.pontoCartesianoBaricentrico(new Ponto(-1, -2, 4));
		assertEquals(0, c.getX(), 2);
		assertEquals(4, c.getY(), 2);
	}
	
	@Test(expected=RuntimeException.class)
	public void testPontoCartesianoBaricentricoNulo() throws NegocioException {
		p1 = new Ponto(0, 0, 0);
		p2 = new Ponto(2, 0, 0);
		p3 = new Ponto(1, 1, 0);
		t1 = new Triangulo(p1, p2, p3);
		Ponto c = t1.pontoCartesianoBaricentrico(null);
	}
}
