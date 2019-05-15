package entrega3.tela;

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
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;
import negocio.beans.CameraVirtual;
import negocio.beans.Forma;

public class ControladorTela implements Initializable {
	private ObservableList<String> obsList;
	private List<String> objetoFiles;
	private GraphicsContext graphic;
	private CameraVirtual camera;
	private Forma objeto;
	
    @FXML
    private Canvas canvas;

    @FXML
    private ComboBox<String> comboBoxObjeto;

    @FXML
    private Accordion accordion;

    @FXML
    private TitledPane titlePaneCamera;

    @FXML
    private TitledPane titlePaneRotacao;

    @FXML
    private TitledPane titlePaneReflexao;

    @FXML
    private TitledPane titlePaneCisalhamento;

    @FXML
    private TitledPane titlePaneEscala;

    @FXML
    private TitledPane titlePaneTranslacao;
	
    @FXML
    private Button buttonRenderizar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.carregarComboBoxObjeto();

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
		comboBoxObjeto.setItems(obsList);
	}
}
