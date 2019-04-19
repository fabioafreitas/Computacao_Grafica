package view.entrega1;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

import negocio.Drawner;

public class SegundaQuestao extends Application{
	@Override
	public void start(Stage primaryStage) throws Exception {
		/**
		 * INSITA O NOME DO ARQUIVO, SEM A EXTENÇÃO, NA STRING ABAIXO
		 */
		String fileName = "calice2"; 
		
		//Dimensões do canvas
		final int WIDTH = 800;	
		final int HEIGHT = 600;
		
		//Espaçamento do canvas para a tela
		final int PLUS = 50;	
		
		//Criando e posicionando o canvas
		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		canvas.setTranslateX(PLUS/2);
		canvas.setTranslateY(PLUS/2);
		
		//Desenhando a forma no canvas
		Drawner.desenharProjecaoOrtogonal(canvas, fileName);
		
		//Adicionando o canvas num grupo, e este na cena
		Group group = new Group(canvas);
		Scene scene = new Scene(group, WIDTH+PLUS, HEIGHT+PLUS);
		
		//Iniciando a cena
		primaryStage.setTitle("Objeto: "+fileName+".byu");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
