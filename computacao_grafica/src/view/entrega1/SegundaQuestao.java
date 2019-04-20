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
		//ADICIONE O NOME DO ARQUIVO
		String fileName = "calice2"; 
		Scene scene = Drawner.desenharProjecaoOrtogonal(600, 600, fileName);
		primaryStage.setTitle("Objeto: "+fileName+".byu");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
