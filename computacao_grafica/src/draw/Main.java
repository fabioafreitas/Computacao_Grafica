package draw;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

public class Main extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		Sphere sphere = new Sphere(50);

		Group group = new Group();
		group.getChildren().add(sphere);
		
		Scene scene = new Scene(group, 1400, 800);
		
		primaryStage.setTitle("Teste Esfera");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
