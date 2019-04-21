package unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import negocio.beans.CameraVirtual;
import negocio.beans.Ponto;
import negocio.beans.Vetor;
import negocio.exception.EntradaInvalidaException;
import negocio.exception.FormatoInvalidoException;
import negocio.exception.NegocioException;

/**
 * Testando 100% de comandos e de decisão
 * @author fabio
 *
 */
public class TesteCameraVirtual {
	CameraVirtual cam;
	String separator = System.getProperty("file.separator");
	String path = "Teste"+separator;
	Vetor u,v,n;
	Ponto c;
	
	@Test
	public void testInstanciandoArquivoValido() throws IOException, NegocioException {
		cam = new CameraVirtual(path+"teste1");
		c = new Ponto(1, 1, 2);
		u = new Vetor(-Math.sqrt(2)/2, Math.sqrt(2)/2, 0);
		v = new Vetor(-Math.sqrt(6)/6, -Math.sqrt(6)/6, Math.sqrt(6)/3);
		n = new Vetor(-Math.sqrt(3)/3, -Math.sqrt(3)/3, -Math.sqrt(3)/3);
		assertTrue(cam.getPontoC().equals(c));
		assertTrue(cam.getVetorU().equals(u));
		assertTrue(cam.getVetorV().equals(v));
		assertTrue(cam.getVetorN().equals(n));
		assertEquals(1,cam.getD(),2);
		assertEquals(1,cam.getHx(),2);
		assertEquals(1,cam.getHy(),2);
	}
	
	@Test(expected=FormatoInvalidoException.class)
	public void testInstanciandoLinhaPontoCVazia() throws IOException, NegocioException {
		cam = new CameraVirtual(path+"teste2");
	}
	
	@Test(expected=FormatoInvalidoException.class)
	public void testInstanciandoLinhaVetorNVazia() throws IOException, NegocioException {
		cam = new CameraVirtual(path+"teste3");
	}
	
	@Test(expected=FormatoInvalidoException.class)
	public void testInstanciandoLinhaVetorVVazia() throws IOException, NegocioException {
		cam = new CameraVirtual(path+"teste4");
	}
	
	@Test(expected=FormatoInvalidoException.class)
	public void testInstanciandoLinhaDVazia() throws IOException, NegocioException {
		cam = new CameraVirtual(path+"teste5");
	}
	
	@Test(expected=FormatoInvalidoException.class)
	public void testInstanciandoLinhaHxVazia() throws IOException, NegocioException {
		cam = new CameraVirtual(path+"teste6");
	}
	
	@Test(expected=FormatoInvalidoException.class)
	public void testInstanciandoLinhaHyVazia() throws IOException, NegocioException {
		cam = new CameraVirtual(path+"teste7");
	}
	
	@Test(expected=EntradaInvalidaException.class)
	public void testInstanciandoDNegativo() throws IOException, NegocioException {
		cam = new CameraVirtual(path+"teste8");
	}

	@Test(expected=EntradaInvalidaException.class)
	public void testInstanciandoHxNegativo() throws IOException, NegocioException {
		cam = new CameraVirtual(path+"teste9");
	}

	@Test(expected=EntradaInvalidaException.class)
	public void testInstanciandoHyNegativo() throws IOException, NegocioException {
		cam = new CameraVirtual(path+"teste10");
	}
	
	@Test
	public void testGetPontoC() throws IOException, NegocioException {
		cam = new CameraVirtual(path+"teste1");
		Ponto c = new Ponto(1, 1, 2);
		assertTrue(cam.getPontoC().equals(c));
	}
	
	@Test
	public void testGetVetorU() throws IOException, NegocioException {
		cam = new CameraVirtual(path+"teste1");
		u = new Vetor(-Math.sqrt(2)/2, Math.sqrt(2)/2, 0);
		assertTrue(cam.getVetorU().equals(u));
	}
	
	@Test
	public void testGetVetorV() throws IOException, NegocioException {
		cam = new CameraVirtual(path+"teste1");
		v = new Vetor(-Math.sqrt(6)/6, -Math.sqrt(6)/6, Math.sqrt(6)/3);
		assertTrue(cam.getVetorV().equals(v));
	}
	
	@Test
	public void testGetVetorN() throws IOException, NegocioException {
		cam = new CameraVirtual(path+"teste1");
		n = new Vetor(-Math.sqrt(3)/3, -Math.sqrt(3)/3, -Math.sqrt(3)/3);
		assertTrue(cam.getVetorN().equals(n));
	}
	
	@Test
	public void testGetD() throws IOException, NegocioException {
		cam = new CameraVirtual(path+"teste1");
		assertEquals(1, cam.getD(), 2);
	}
	
	@Test
	public void testGetHx() throws IOException, NegocioException {
		cam = new CameraVirtual(path+"teste1");
		assertEquals(1, cam.getHx(), 2);
	}
	
	@Test
	public void testGetHy() throws IOException, NegocioException {
		cam = new CameraVirtual(path+"teste1");
		assertEquals(1, cam.getHy(), 2);
	}
	
	@Test
	public void testInstanciarBaseJaOrtogonal() throws IOException, NegocioException {
		cam = new CameraVirtual(path+"teste11");
		c = new Ponto(2, 2, 2);
		u = new Vetor(0, 0, -1);
		v = new Vetor(1/Math.sqrt(2), -1/Math.sqrt(2), 0);
		n = new Vetor(1/Math.sqrt(2), 1/Math.sqrt(2), 0);
		assertTrue(cam.getPontoC().equals(c));
		assertTrue(cam.getVetorU().equals(u));
		assertTrue(cam.getVetorV().equals(v));
		assertTrue(cam.getVetorN().equals(n));
		assertEquals(1,cam.getD(),2);
		assertEquals(1,cam.getHx(),2);
		assertEquals(1,cam.getHy(),2);
	}
	
	@Test
	public void testInstanciarBaseJaOrtonormal() throws IOException, NegocioException {
		cam = new CameraVirtual(path+"teste11");
		c = new Ponto(2, 2, 2);
		u = new Vetor(0, 0, -1);
		v = new Vetor(1/Math.sqrt(2), -1/Math.sqrt(2), 0);
		n = new Vetor(1/Math.sqrt(2), 1/Math.sqrt(2), 0);
		assertTrue(cam.getPontoC().equals(c));
		assertTrue(cam.getVetorU().equals(u));
		assertTrue(cam.getVetorV().equals(v));
		assertTrue(cam.getVetorN().equals(n));
		assertEquals(1,cam.getD(),2);
		assertEquals(1,cam.getHx(),2);
		assertEquals(1,cam.getHy(),2);
	}
}
