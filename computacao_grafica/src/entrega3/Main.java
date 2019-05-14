package entrega3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		String sep = System.getProperty("file.separator");
		Pane root = FXMLLoader.load(getClass().getResource("tela"+sep+"Tela.fxml"));
		primaryStage.setTitle("Computação Gráfica - 3ª Entrega");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
