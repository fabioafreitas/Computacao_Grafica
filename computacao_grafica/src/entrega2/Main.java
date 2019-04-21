package entrega2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 * criar interface gráfica da entrega 2
 -ter um selectbox para escolher
        -a camera virtual
        -a forma a ser desenhada
 -tela com tamanho padrao 800 600
 -canvas no lado direito com tamanho 550 550
 -selects boxes no lado esquerdo
 -lado esquerdo inferior botão "renderizar"

 * @author fabio
 *
 */
public class Main extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane root = FXMLLoader.load(getClass().getResource("TelaProjecaoPerspectiva.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
