package view;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

public class ExemploJavaFX extends Application{
	@Override
	public void start(Stage primaryStage) throws Exception {
		final int WIDTH = 1400, HEIGHT = 800;
		
		Sphere sphere = new Sphere(50);
		sphere.translateXProperty().set(WIDTH/2);
		sphere.translateYProperty().set(HEIGHT/2);
		
//		sphere.setTranslateX(WIDTH/2);
//		sphere.setTranslateY(HEIGHT/2);
		
		Group group = new Group();
		group.getChildren().add(sphere);
		
		Scene scene = new Scene(group, WIDTH, HEIGHT);
		
		primaryStage.setTitle("Teste Esfera");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
