package negocio;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import negocio.beans.CameraVirtual;
import negocio.beans.Ponto;
import negocio.beans.Vetor;
import negocio.exception.NegocioException;

public class TestAlgebra {
	String separator = System.getProperty("file.separator");
	String path = "Teste"+separator;
	CameraVirtual cam;
	Ponto p1, p2, p3;
	
	@Test
	public void testMudancaDeBaseMundialVista() throws IOException, NegocioException {
		cam = new CameraVirtual(path+"teste1");
		p1 = new Ponto(1, -3, -5);
		p2 = new Ponto(-2*Math.sqrt(2), -5*Math.sqrt(6)/3, 11*Math.sqrt(3)/3);
		p3 = Algebra.baseMundialParaVista(p1, cam);
		assertTrue(p3.equals(p2));
	}
	
	@Test(expected=RuntimeException.class)
	public void testMudancaDeBaseMundialVistaPontoNulo() throws IOException, NegocioException {
		cam = new CameraVirtual(path+"teste1");
		p2 = Algebra.baseMundialParaVista(null, cam);
	}
	
	@Test(expected=RuntimeException.class)
	public void testMudancaDeBaseMundialVistaCameraNula() throws IOException, NegocioException {
		p1 = new Ponto(1, -3, -5);
		p2 = Algebra.baseMundialParaVista(p1, null);
	}
	
	
	
	@Ignore
	@Test
	public void testMudancaDeBaseVistaMundial() throws IOException, NegocioException {
		cam = new CameraVirtual(path+"teste1");
		p1 = new Ponto(-2*Math.sqrt(2), -5*Math.sqrt(6)/3, 11*Math.sqrt(3)/3);
		p2 = new Ponto(1, -3, -5);
		p3 = Algebra.baseVistaParaMundial(p1, cam);
		assertTrue(p3.equals(p2));
	}
	
	@Ignore
	@Test(expected=RuntimeException.class)
	public void testMudancaDeBaseVistaMundialPontoNulo() throws NegocioException {
		p1 = new Ponto(-2*Math.sqrt(2), -5*Math.sqrt(6)/3, 11*Math.sqrt(3)/3);
		p2 = Algebra.baseVistaParaMundial(p1, null);
	}
	
	@Ignore
	@Test(expected=RuntimeException.class)
	public void testMudancaDeBaseVistaMundialCameraNula() throws IOException, NegocioException {
		cam = new CameraVirtual(path+"teste1");
		p2 = Algebra.baseVistaParaMundial(null, cam);
	}
}
