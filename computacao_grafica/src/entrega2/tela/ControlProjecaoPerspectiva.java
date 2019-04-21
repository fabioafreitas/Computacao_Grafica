package entrega2.tela;

import java.io.File;
import java.io.IOException;
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
import negocio.Drawner;
import negocio.beans.CameraVirtual;
import negocio.beans.Forma;
import negocio.exception.NegocioException;


public class ControlProjecaoPerspectiva implements Initializable{
	private ObservableList<String> obsList;
	private List<String> cameraFiles;
	private List<String> objetoFiles;
	private GraphicsContext graphic;
	private CameraVirtual camera;
	private Forma objeto;
	
    @FXML
    private Canvas canvas;

    @FXML
    private Button btn_renderizar;

    @FXML
    private ComboBox<String> cbox_camera;

    @FXML
    private ComboBox<String> cbox_objeto;
    
    @FXML
    void renderizar(ActionEvent event) throws IOException, NegocioException {
    	inicializarCanvas();
    	
    	String fileCamera = cbox_camera.getSelectionModel().getSelectedItem();
    	String fileObjeto = cbox_objeto.getSelectionModel().getSelectedItem();
    	
    	camera = new CameraVirtual(fileCamera);
    	objeto = new Forma(fileObjeto);
    	
    	
    	Drawner.projecaoPerspectiva(canvas, camera, objeto);
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
		graphic.setFill(Color.BLACK);
		graphic.fillRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
	}
	
}