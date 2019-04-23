package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import negocio.beans.Forma;
import negocio.beans.Ponto;
import negocio.exception.EntradaInvalidaException;
import negocio.exception.FormatoInvalidoException;
import negocio.exception.NegocioException;

/**
 * Testando 100% de comandos e de decisão
 * @author fabio
 *
 */
public class TesteForma {
	String separator = System.getProperty("file.separator");
	String path = "Teste"+separator;
	Forma f;
	
	@Test
	public void testInstanciarFormaValida() throws NegocioException, IOException {
		f = new Forma(path+"teste1");
		assertNotNull(f);
	}

	
	@Test(expected=FormatoInvalidoException.class)
	public void testInstanciarFormaInvalidaPrimeiraLinhaVazia() throws NegocioException, IOException {
		f = new Forma(path+"teste2");
	}
	
	@Test(expected=FormatoInvalidoException.class)
	public void testInstanciarFormaInvalidaLinhaPontosVazia() throws NegocioException, IOException {
		f = new Forma(path+"teste3");
	}
	
	@Test(expected=FormatoInvalidoException.class)
	public void testInstanciarFormaInvalidaLinhaTriangulosVazia() throws NegocioException, IOException {
		f = new Forma(path+"teste4");
	}

	
	/**
	 * Forma que não possui triangulos
	 * @throws IOException 
	 */
	@Test
	public void testInstanciarFormaSemTriangulos() throws NegocioException, IOException {
		f = new Forma(path+"teste5");
		assertEquals(0, f.getIndiceTriangulos().size(), 2);
	}

	/**
	 * Forma com vetor de indices de triangulos com 
	 * tamanho diferente de 3. Precisa ser 3, pois cada
	 * indice represente um ponto do ArratList de vertices
	 * @throws IOException 
	 */
	@Test(expected=FormatoInvalidoException.class)
	public void testInstanciarFormaVetorIndicesDiferenteDeTres() throws NegocioException, IOException {
		f = new Forma(path+"teste6");
	}

	@Test(expected=EntradaInvalidaException.class)
	public void testInstanciarFormaVetorIndicesNegativos() throws NegocioException, IOException {
		f = new Forma(path+"teste7");
	}
	
	@Test(expected=EntradaInvalidaException.class)
	public void testInstanciarFormaVetorIndicesAcimaDoLimite() throws NegocioException, IOException {
		f = new Forma(path+"teste8");
	}
	
	@Test
	public void testGetVertices() throws NegocioException, IOException {
		Ponto p1 = new Ponto(1, 1, 1);
		Ponto p2 = new Ponto(2, 1, 2);
		f = new Forma(path+"teste9");
		ArrayList<Ponto> v2 = f.getVertices();
		assertTrue(v2.get(0).equals(p1));
		assertTrue(v2.get(1).equals(p2));
	}

	
	@Test
	public void testGetIndiceTriangulos() throws NegocioException, IOException {
		f = new Forma(path+"teste1");
		ArrayList<int[]> i2 = f.getIndiceTriangulos();
		assertTrue(i2.get(0)[0] == 0);
		assertTrue(i2.get(0)[1] == 1);
		assertTrue(i2.get(0)[2] == 2);
	}
	
	@Test
	public void testSetVerticesIndiceValido() throws NegocioException, IOException {
		f = new Forma(path+"teste1");
		Ponto p = new Ponto(0, 0, 0);
		f.setVertices(0,p);
		assertTrue(f.getVertices().get(0).equals(p));
	}
	
	@Test(expected=EntradaInvalidaException.class)
	public void testSetVerticesIndiceNegativo() throws NegocioException, IOException {
		f = new Forma(path+"teste1");
		f.setVertices(-1, new Ponto(0, 0, 0));
	}
	
	@Test(expected=EntradaInvalidaException.class)
	public void testSetVerticesIndiceAcimaDoLimite() throws NegocioException, IOException {
		f = new Forma(path+"teste1");
		f.setVertices(3, new Ponto(0, 0, 0));
	}
	
	@Test(expected=RuntimeException.class)
	public void testSetVerticePontoNulo() throws NegocioException, IOException {
		f = new Forma(path+"teste1");
		f.setVertices(0, null);
	}
	
	@Test
	public void testNormalizarValido() throws NegocioException, IOException {
		f = new Forma(path+"teste11");
		f.normalizarProjecaoOrtogonal(11, 11);
		Ponto p1 = new Ponto(8,8,0);
		Ponto p2 = new Ponto(4,4,0);
		Ponto p3 = new Ponto(6,6,0);
		Ponto p4 = new Ponto(0,0,0);
		Ponto p5 = new Ponto(10,10,0);
		assertTrue(f.getVertices().get(0).equals(p1));
		assertTrue(f.getVertices().get(1).equals(p2));
		assertTrue(f.getVertices().get(2).equals(p3));
		assertTrue(f.getVertices().get(3).equals(p4));
		assertTrue(f.getVertices().get(4).equals(p5));		
	}
	
	@Test(expected=EntradaInvalidaException.class)
	public void testNormalizarInalido1() throws NegocioException, IOException {
		f = new Forma(path+"teste1");
		f.normalizarProjecaoOrtogonal(-100, -100);
	}
	
	@Test(expected=EntradaInvalidaException.class)
	public void testNormalizarInalido2() throws NegocioException, IOException {
		f = new Forma(path+"teste1");
		f.normalizarProjecaoOrtogonal(100, -100);
	}
	
	
}
