package ufrpe.gui;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import ufrpe.negocio.beans.CameraVirtual;
import ufrpe.negocio.beans.Objeto;

public class ControladorTela implements Initializable {
	private ObservableList<String> obsList;
	private List<String> objetoFiles;
	private GraphicsContext graphic;
	private CameraVirtual camera;
	private Objeto objeto;
	
	@FXML
    private Canvas canvas;

    @FXML
    private ComboBox<String> cb_objeto;

    @FXML
    private Button btn_renderizar;

    @FXML
    private TextField tf_pontoc;

    @FXML
    private TextField tf_vetorn;

    @FXML
    private TextField tf_vetorv;

    @FXML
    private TextField tf_d;

    @FXML
    private TextField tf_hx;

    @FXML
    private TextField tf_hy;

    @FXML
    private TextField tf_lamb;

    @FXML
    private TextField tf_li;

    @FXML
    private TextField tf_ka;

    @FXML
    private TextField tf_ks;

    @FXML
    private TextField tf_n;

    @FXML
    private TextField tf_vetorkd;

    @FXML
    private TextField tf_vetorod;

    @FXML
    private TextField tf_pontopl;


    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		graphic = canvas.getGraphicsContext2D();
		this.carregarComboBoxObjeto();
		this.pintarCanvasPreto();
	}
	
	@FXML
    void ClickButtonRenderizar(ActionEvent event) {

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
		Collections.sort(objetoFiles);
		
		obsList = FXCollections.observableArrayList(objetoFiles);
		cb_objeto.setItems(obsList);
	}
	
	public void pintarCanvasPreto() {
		graphic.setFill(Color.BLACK);
		graphic.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}
}
