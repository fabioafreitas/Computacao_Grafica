package view;

import java.util.ArrayList;

import beans.Forma;
import beans.Ponto;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import negocio.Drawner;

public class SegundaQuestao extends Application{
	@Override
	public void start(Stage primaryStage) throws Exception {
		final int WIDTH = 800;
		final int HEIGHT = 600;
		
		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		
		Forma form = Drawner.normalizar( Drawner.projecao(Drawner.lerArquivo("triangulo")) ,
					(int) canvas.getWidth(),
				    (int) canvas.getHeight());

		GraphicsContext graphic = canvas.getGraphicsContext2D();
		ArrayList<Ponto> pontos = form.getVertices();
		for (int i = 0; i < pontos.size() ; i++) {
			graphic.setFill(Color.WHITE);
			graphic.fillRect(pontos.get(i).x - 1 ,
						 pontos.get(i).y - 1 , 1, 1);
		}
		
		Group groups = new Group(canvas);
		Scene scene = new Scene(groups);
		primaryStage.setTitle("Testando Canvas");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
