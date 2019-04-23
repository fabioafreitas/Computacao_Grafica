package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import negocio.Algebra;
import negocio.beans.CameraVirtual;
import negocio.beans.Ponto;
import negocio.exception.NegocioException;

public class TesteAlgebra {
	String separator = System.getProperty("file.separator");
	String path = "Teste"+separator;
	CameraVirtual cam;
	Ponto p1, p2, p3;
	
	@Test
	public void testCalcularCoordenadaDeVista() throws IOException, NegocioException {
		cam = new CameraVirtual(path+"teste1");
		p2 = Algebra.getCoordenadasVista(new Ponto(1, -3, -5),  cam);
		assertEquals(-2.82842, p2.getX(),5);
		assertEquals(-4.08248, p2.getY(),5);
		assertEquals(6.35085,  p2.getZ(),5);
	}
	
	@Test(expected=RuntimeException.class)
	public void testCalcularCoordenadaDeVistaPontoNulo() throws IOException, NegocioException {
		cam = new CameraVirtual(path+"teste1");
		p2 = Algebra.getCoordenadasVista(null,  cam);
	}
	
	@Test(expected=RuntimeException.class)
	public void testCalcularCoordenadaDeVistaCameraNula() throws IOException, NegocioException {
		cam = new CameraVirtual(path+"teste1");
		p2 = Algebra.getCoordenadasVista(new Ponto(1, -3, -5),  null);
	}
	
	@Test
	public void testCalcularCoordenadaDePerspectiva() throws IOException, NegocioException {
		cam = new CameraVirtual(path+"teste1");
		p2 = Algebra.getCoordenadasVista(new Ponto(1, -3, -5),  cam);
		p2 = Algebra.getProjecaoPerspectiva(p2, cam);
		assertEquals(-0.44536, p2.getX(),5);
		assertEquals(-0.64282, p2.getY(),5);
		assertEquals(1,  p2.getZ(),5);
	}
	
	@Test(expected=RuntimeException.class)
	public void testCalcularCoordenadaDePerspectivaPontoNulo() throws IOException, NegocioException {
		cam = new CameraVirtual(path+"teste1");
		p2 = Algebra.getCoordenadasVista(new Ponto(1, -3, -5),  cam);
		p2 = Algebra.getProjecaoPerspectiva(null, cam);
	}
	
	@Test(expected=RuntimeException.class)
	public void testCalcularCoordenadaDePerspectivaCameraNula() throws IOException, NegocioException {
		cam = new CameraVirtual(path+"teste1");
		p2 = Algebra.getCoordenadasVista(new Ponto(1, -3, -5),  cam);
		p2 = Algebra.getProjecaoPerspectiva(p2, null);
	}
	
	@Test
	public void testCalcularCoordenadaNormalizada() throws IOException, NegocioException {
		cam = new CameraVirtual(path+"teste1");
		p2 = Algebra.getCoordenadasVista(new Ponto(1, -3, -5),  cam);
		p2 = Algebra.getProjecaoPerspectiva(p2, cam);
		p2 = Algebra.getPerspectivaNormalizada(p2, cam);
		assertEquals(-0.44536, p2.getX(),5);
		assertEquals(-0.64282, p2.getY(),5);
		assertEquals(1,  p2.getZ(),5);
	}
	
	@Test(expected=RuntimeException.class)
	public void testCalcularCoordenadaNormalizadaPontoNulo() throws IOException, NegocioException {
		cam = new CameraVirtual(path+"teste1");
		p2 = Algebra.getCoordenadasVista(new Ponto(1, -3, -5),  cam);
		p2 = Algebra.getProjecaoPerspectiva(p2, cam);
		p2 = Algebra.getPerspectivaNormalizada(null, cam);
	}
	
	@Test(expected=RuntimeException.class)
	public void testCalcularCoordenadaNormalizadaCameraNula() throws IOException, NegocioException {
		cam = new CameraVirtual(path+"teste1");
		p2 = Algebra.getCoordenadasVista(new Ponto(1, -3, -5),  cam);
		p2 = Algebra.getProjecaoPerspectiva(p2, cam);
		p2 = Algebra.getPerspectivaNormalizada(p2, null);
	}
	
	@Test
	public void testCalcularCoordenadaDeTela() throws IOException, NegocioException {
		p1 = new Ponto(194, 534, 1);
		cam = new CameraVirtual(path+"teste1");
		p2 = Algebra.getCoordenadasVista(new Ponto(1, -3, -5),  cam);
		p2 = Algebra.getProjecaoPerspectiva(p2, cam);
		p2 = Algebra.getPerspectivaNormalizada(p2, cam);
		p2 = Algebra.getCoordenadaTela(p2, 700, 650);
		assertTrue(p2.equals(p1));
	}
	
	@Test(expected=RuntimeException.class)
	public void testCalcularCoordenadaDeTelaPontoNulo() throws IOException, NegocioException {
		cam = new CameraVirtual(path+"teste1");
		p2 = Algebra.getCoordenadasVista(new Ponto(1, -3, -5),  cam);
		p2 = Algebra.getProjecaoPerspectiva(p2, cam);
		p2 = Algebra.getPerspectivaNormalizada(p2, cam);
		p2 = Algebra.getCoordenadaTela(null, 700, 650);
	}
}
