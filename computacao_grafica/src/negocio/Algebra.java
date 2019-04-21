package negocio;

import negocio.beans.CameraVirtual;
import negocio.beans.Matriz;
import negocio.beans.Ponto;
import negocio.beans.Vetor;
import negocio.exception.NegocioException;

public class Algebra {
	/**
	 * Realiza a mudança de base de um ponto em coordenadas 
	 * globais para coordenada de vista, baseado numa câmera virtual
	 * [ Ux Uy Uz ]
	 * [ Vx Vy Vz ]
	 * [ Nx Ny Nz ]
	 * @param pMundial
	 * @param cam
	 * @return
	 * @throws NegocioException
	 */
	public static Ponto baseMundialParaVista(Ponto pMundial, CameraVirtual cam) throws NegocioException {
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
		Matriz vista = mudancaBase.multiplicar(new Matriz(new double[][] {
			{v.getX()},
			{v.getY()},
			{v.getZ()}
		}));
		return new Ponto(vista.getMatriz()[0][0], 
						 vista.getMatriz()[1][0], 
						 vista.getMatriz()[2][0]);		
	}
	
	/**
	 * Recebe um ponto, em coordenada de vista, e retorna
	 * sua projeção em perspectiva, já normalizada, baseada
	 * numa câmera virtual
	 * 
	 * xS = d/hx * xV/zV
	 * yS = d/hy * yV/zV
	 * zS = d
	 * 
	 * @param pVista
	 * @param cam
	 * @return
	 */
	public static Ponto converterPontoPerspectivaNormalizado(Ponto pVista, CameraVirtual cam) {
		if(pVista == null)
			throw new RuntimeException("Ponto nulo");
		if(cam == null)
			throw new RuntimeException("CameraVirtual nula");
		return new Ponto((cam.getD()/cam.getHx()) * (pVista.getX()/pVista.getZ()),
						 (cam.getD()/cam.getHy()) * (pVista.getY()/pVista.getZ()),
						  cam.getD());
	}
	
	/**
	 * Recebe o ponto em perspectiva, já normalizado, e retorna
	 * sua coordenada de tela.
	 * 
	 * Perceba que a coordenada do ponto em perspectiva
	 * é onde ele será pintado no plano de vista
	 * @param pPerspctiva
	 * @param width
	 * @param height
	 * @return
	 */
	public static Ponto coverterPontoCoordenadaTela(Ponto ponto, int width, int height) {
		if(ponto == null)
			throw new RuntimeException("Ponto nulo");
		return new Ponto(Math.floor( ((ponto.getX()+1)*(width-1))/2 + 0.5 ),
						 Math.floor( (height-1) - ((ponto.getX()+1)*(height-1))/2 + 0.5 ),
						 ponto.getZ());
	}
	
	/** TODO corrigir método
	 * [ Ux Vx Nx ]
	 * [ Uy Vy Ny ]
	 * [ Uz Vz Nz ]
	 * @param pVista
	 * @param cam
	 * @return
	 * @throws NegocioException 
	 */
	public static Ponto baseVistaParaMundial(Ponto pVista, CameraVirtual cam) throws NegocioException {
		if(pVista == null)
			throw new RuntimeException("Ponto nulo");
		if(cam == null)
			throw new RuntimeException("CameraVirtual nula");
		Matriz mudancaBase = new Matriz(new double[][] {
			{cam.getVetorU().getX(), cam.getVetorV().getX(), cam.getVetorN().getX()},
			{cam.getVetorU().getY(), cam.getVetorV().getY(), cam.getVetorN().getY()},
			{cam.getVetorU().getZ(), cam.getVetorV().getZ(), cam.getVetorN().getZ()}
		});
		Vetor v = pVista.subtrair(cam.getPontoC());
		Matriz vista = mudancaBase.multiplicar(new Matriz(new double[][] {
			{v.getX()},
			{v.getY()},
			{v.getZ()}
		}));
		return new Ponto(vista.getMatriz()[0][0], 
						 vista.getMatriz()[1][0], 
						 vista.getMatriz()[2][0]);		
	}

}
