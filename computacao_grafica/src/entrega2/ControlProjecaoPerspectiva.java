package entrega2;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;


public class ControlProjecaoPerspectiva implements Initializable{
	private ObservableList<String> obsList;
	private List<String> cameraFiles;
	private List<String> objetoFiles;
	private GraphicsContext graphic;
	
    @FXML
    private Canvas canvas;

    @FXML
    private Button btn_renderizar;

    @FXML
    private ComboBox<String> cbox_camera;

    @FXML
    private ComboBox<String> cbox_objeto;
    

    @FXML
    void renderizar(ActionEvent event) {
    	//TODO
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		graphic = canvas.getGraphicsContext2D();
		carregarComboBoxCamera();
		carregarComboBoxObjeto();
		inicializarCanvas();
	}
	
	public void carregarComboBoxCamera() {
		cameraFiles = new ArrayList<String>();
		
		File[] files = new File("CameraVirtual").listFiles();
		for (File file : files) {
		    if (file.isFile()) {
		    	String fileName = file.getName();
		    	if (fileName.indexOf(".") > 0)
		            fileName = fileName.substring(0, fileName.lastIndexOf("."));
		    	cameraFiles.add(fileName);
		    }
		}
		
		obsList = FXCollections.observableArrayList(cameraFiles);
		cbox_camera.setItems(obsList);
	}
	
	public void carregarComboBoxObjeto() {
		objetoFiles = new ArrayList<String>();

		File[] files = new File("Formas").listFiles();
		for (File file : files) {
		    if (file.isFile()) {
		    	String fileName = file.getName();
		    	if (fileName.indexOf(".") > 0)
		            fileName = fileName.substring(0, fileName.lastIndexOf("."));
		    	objetoFiles.add(fileName);
		    }
		}
		
		obsList = FXCollections.observableArrayList(objetoFiles);
		cbox_objeto.setItems(obsList);
	}
	
	public void inicializarCanvas() {
		graphic.fillRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
	}
	
}