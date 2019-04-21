package unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import negocio.beans.Matriz;
import negocio.exception.EntradaInvalidaException;
import negocio.exception.MultiplicacaoMatrizInvalidaException;
import negocio.exception.NegocioException;

public class TesteMatriz {
	
	Matriz m1, m2;
	
	@Test
	public void testInstanciarLinhaColunaValidas() throws NegocioException {
		m1 = new Matriz(2,2);
		assertNotNull(m1);
	}
	
	@Test(expected=EntradaInvalidaException.class)
	public void testInstanciarLinhaColunaInvalidas1() throws NegocioException {
		m1 = new Matriz(2,-2);
	}

	@Test(expected=EntradaInvalidaException.class)
	public void testInstanciarLinhaColunaInvalidas2() throws NegocioException {
		m1 = new Matriz(-2,-2);
	}

	
	@Test
	public void testInstnaciarComObjetoValido() {
		m2 = new Matriz(new double[][] {{1,2},{3,4}});
		assertNotNull(m2);
	}
	
	@Test(expected=RuntimeException.class)
	public void testInstnaciarComObjetoNulo() {
		m2 = new Matriz(null);
	}
	
	@Test
	public void testToString() {
		m2 = new Matriz(new double[][] {{1,2},{3,4}});
		assertNotNull(m2.toString());
	}

	@Test
	public void testGetNumLinhas() {
		m2 = new Matriz(new double[][] {{1,2},{3,4}});
		assertEquals(2,m2.getNumLinhas(),2);
	}

	@Test
	public void testGetNumColunas() {
		m2 = new Matriz(new double[][] {{1,2},{3,4}});
		assertEquals(2,m2.getNumColunas(),2);
	}

	@Test
	public void testMetodoGetMatriz() {
		m2 = new Matriz(new double[][] {{1,2},{3,4}});
		double a[][] = m2.getMatriz();
		assertNotNull(a);
	}

	@Test(expected=RuntimeException.class)
	public void testEqualsMatrizNula() {
		m1 = new Matriz(new double[][] {{1,2},{3,4}});
		m2 = null;
		boolean a = m1.equals(m2);
	}

	@Test
	public void testEqualsMatrizLinhasDiferentes() {
		m1 = new Matriz(new double[][] {{1,2},{3,4},{5,6}});
		m2 = new Matriz(new double[][] {{1,2},{3,4}});
		assertFalse(m1.equals(m2));
	}
	
	@Test
	public void testEqualsMatrizColunasDiferentes() {
		m1 = new Matriz(new double[][] {{1,2},{3,4}});
		m2 = new Matriz(new double[][] {{1,2,3},{4,5,6}});
		assertFalse(m1.equals(m2));
	}

	@Test
	public void testEqualsTrue() {
		m1 = new Matriz(new double[][] {{1,2},{3,4}});
		m2 = new Matriz(new double[][] {{1,2},{3,4}});
		assertTrue(m1.equals(m2));
	}
	
	@Test
	public void testEqualsFalse() {
		m1 = new Matriz(new double[][] {{1,2},{3,4}});
		m2 = new Matriz(new double[][] {{3,1},{7,4}});
		assertFalse(m1.equals(m2));
	}
	
	@Test
	public void testSetEntradaIndicesValidos() throws EntradaInvalidaException {
		m1 = new Matriz(new double[][] {{1,2},{3,4}});
		m1.setEntrada(1, 1, 10);
		assertEquals(10, m1.getMatriz()[1][1], 2);
	}
	
	@Test(expected=EntradaInvalidaException.class)
	public void testSetEntradaIndicesInvalidos1() throws EntradaInvalidaException {
		m1 = new Matriz(new double[][] {{1,2},{3,4}});
		m1.setEntrada(1, -1, 10);
	}
	
	@Test(expected=EntradaInvalidaException.class)
	public void testSetEntradaIndicesInvalidos2() throws EntradaInvalidaException {
		m1 = new Matriz(new double[][] {{1,2},{3,4}});
		m1.setEntrada(-1, -1, 10);
	}
	
	@Test
	public void testMultiplicarMatrizesValidas() throws NegocioException {
		m1 = new Matriz(new double[][] {{1,2},{3,4}});
		m2 = new Matriz(new double[][] {{1},{2}});
		Matriz m3 = m1.multiplicar(m2);
		assertNotNull(m3);
		assertEquals(2, m3.getNumLinhas());
		assertEquals(1, m3.getNumColunas());
	}
	
	@Test(expected=RuntimeException.class)
	public void testMultiplicarMatrizPorNula() throws NegocioException {
		m1 = new Matriz(new double[][] {{1,2},{3,4}});
		m2 = null;
		Matriz m3 = m1.multiplicar(m2);
	}
	
	@Test(expected=MultiplicacaoMatrizInvalidaException.class)
	public void testMultiplicarMatrizesInvalidas() throws NegocioException {
		m1 = new Matriz(new double[][] {{1,2},{3,4}});
		m2 = new Matriz(new double[][] {{1,2}});
		Matriz m3 = m1.multiplicar(m2);
	}
}
