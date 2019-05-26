package ufrpe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		String sep = System.getProperty("file.separator");
		Pane root = FXMLLoader.load(getClass().getResource("gui"+sep+"TelaFinal.fxml"));
		primaryStage.setTitle("Computa��o Gr�fica - 3� Entrega");
		primaryStage.getIcons().add(new Image("file:icon.png"));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
