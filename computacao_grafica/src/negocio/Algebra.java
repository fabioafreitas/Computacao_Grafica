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
	public static Ponto getCoordenadasVista(Ponto pMundial, CameraVirtual cam) throws NegocioException {
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
		
		Matriz vetorEntrada = new Matriz(new double[][] {
			{v.getX()},
			{v.getY()},
			{v.getZ()}
		});
		
		
		Matriz vista = mudancaBase.multiplicar(vetorEntrada);
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
	public static Ponto getProjecaoPerspectiva(Ponto pVista, CameraVirtual cam) {
		if(pVista == null)
			throw new RuntimeException("Ponto nulo");
		if(cam == null)
			throw new RuntimeException("CameraVirtual nula");
		return new Ponto(cam.getD() * (pVista.getX()/pVista.getZ()),
						 cam.getD() * (pVista.getY()/pVista.getZ()),
						 cam.getD());
	}	
	
	public static Ponto getPerspectivaNormalizada(Ponto pPerspectiva, CameraVirtual cam) {
		if(pPerspectiva == null)
			throw new RuntimeException("Ponto nulo");
		if(cam == null)
			throw new RuntimeException("CameraVirtual nula");
		return new Ponto(pPerspectiva.getX()/cam.getHx(),
						 pPerspectiva.getY()/cam.getHy(),
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
	public static Ponto getCoordenadaTela(Ponto ponto, int width, int height) {
		if(ponto == null) 
			throw new RuntimeException("Ponto nulo");
		return new Ponto(Math.floor( ((ponto.getX()+1)/2)*width + 0.5 ),
						 Math.floor( height - ((ponto.getY()+1)/2)*height + 0.5 ),
						 ponto.getZ());
	}
}
