package ufrpe.gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import ufrpe.negocio.Drawner;
import ufrpe.negocio.beans.CameraVirtual;
import ufrpe.negocio.beans.Cor;
import ufrpe.negocio.beans.Iluminacao;
import ufrpe.negocio.beans.Objeto;
import ufrpe.negocio.beans.Ponto;
import ufrpe.negocio.beans.Vetor;
import ufrpe.negocio.exception.NegocioException;

public class ControladorTela implements Initializable {
	private BufferedReader reader;
	private BufferedWriter writer;
	private ObservableList<String> obsList;
	private List<String> objetoFiles;
	private GraphicsContext pincel;
	private Iluminacao iluminacao;
	private CameraVirtual camera;
	private Objeto objeto;
	
	@FXML private Canvas canvas;
	@FXML private ComboBox<String> cb_camera;
    @FXML private ComboBox<String> cb_objeto;
    @FXML private Button btn_renderizar;
    @FXML private TextField tf_pontoc;
    @FXML private TextField tf_vetorn;
    @FXML private TextField tf_vetorv;
    @FXML private TextField tf_d;
    @FXML private TextField tf_hx;
    @FXML private TextField tf_hy;
    @FXML private TextField tf_iamb;
    @FXML private TextField tf_il;
    @FXML private TextField tf_ka;
    @FXML private TextField tf_ks;
    @FXML private TextField tf_eta;
    @FXML private TextField tf_vetorkd;
    @FXML private TextField tf_vetorod;
    @FXML private TextField tf_pontopl;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		pincel = canvas.getGraphicsContext2D();
		this.carregarComboBoxObjeto();
		this.carregarComboBoxCamera();
		this.pintarCanvasPreto();
		try {
			this.lerArquivoCameraVirtual("camera");
			this.lerArquivoIluminacao();
		} catch (IOException e) {
			e.printStackTrace();
		}
		cb_objeto.setValue("cow");
	}
	
	@FXML
    void ClickButtonRenderizar(ActionEvent event) throws IOException, NegocioException {
		this.renderizar();
    }
	
	@FXML
    void KeyPressedRenderizar(KeyEvent event) throws IOException, NegocioException {
		if(event.getCode().equals(KeyCode.ENTER)) {
			this.renderizar();
		}
    }
	
	private void renderizar() throws IOException, NegocioException {
		String fileObjeto = cb_objeto.getSelectionModel().getSelectedItem();
		String fileCamera = cb_camera.getSelectionModel().getSelectedItem();
		if(fileObjeto != null) {
			this.escreverArquivoCameraVirtual();
			this.escreverArquivoIluminacao();
			if(fileCamera == null) {
				this.lerArquivoCameraVirtual("camera");
			} else {
				this.lerArquivoCameraVirtual(fileCamera);
				cb_camera.setValue(null);
			}
			this.lerArquivoIluminacao();
			this.objeto = new Objeto(fileObjeto);
			Drawner.renderizar(canvas, camera, objeto, iluminacao);
		}
	}
	
	private void carregarComboBoxObjeto() {
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
	
	private void carregarComboBoxCamera() {
		objetoFiles = new ArrayList<String>();

		File[] files = new File("CameraVirtual").listFiles();
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
		cb_camera.setItems(obsList);
	}
	
	private void pintarCanvasPreto() {
		pincel.setFill(Color.BLACK);
		pincel.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}
	
	private void lerArquivoCameraVirtual(String fileName) throws IOException {
		String separator = System.getProperty("file.separator");
		String path = "CameraVirtual"+separator+fileName+".txt";
		reader = new BufferedReader(new FileReader(path));
		
		String line = reader.readLine();
		tf_pontoc.setText(line);
		String[] split = line.split(" ");
		Ponto pontoC = new Ponto(Double.parseDouble(split[0]), 
								 Double.parseDouble(split[1]), 
								 Double.parseDouble(split[2]));
		
		line = reader.readLine();
		tf_vetorn.setText(line);
		split = line.split(" ");
		Vetor vetorN = new Vetor(Double.parseDouble(split[0]), 
								Double.parseDouble(split[1]), 
								Double.parseDouble(split[2]));
		
		line = reader.readLine();
		tf_vetorv.setText(line);
		split = line.split(" ");
		Vetor vetorV = new Vetor(Double.parseDouble(split[0]), 
								Double.parseDouble(split[1]), 
								Double.parseDouble(split[2]));
		
		line = reader.readLine();
		tf_d.setText(line);
		double d  = Double.parseDouble(line);
		
		line = reader.readLine();
		tf_hx.setText(line);
		double hx = Double.parseDouble(line);
		
		line = reader.readLine();
		tf_hy.setText(line);
		double hy = Double.parseDouble(line);
		
		camera = new CameraVirtual(pontoC, vetorN, vetorV, d, hx, hy);
		reader.close();
	}

	private void lerArquivoIluminacao() throws IOException {
		String separator = System.getProperty("file.separator");
		String path = "Iluminacao"+separator+"iluminacao.txt";
		reader = new BufferedReader(new FileReader(path));
		
		String line = reader.readLine();
		tf_iamb.setText(line);
		String split[] = line.split(" ");
		Cor luzAmb = new Cor(Double.parseDouble(split[0]), 
							  Double.parseDouble(split[1]), 
							  Double.parseDouble(split[2]));
		
		line = reader.readLine();
		tf_il.setText(line);
		split = line.split(" ");
		Cor luzL = new Cor(Double.parseDouble(split[0]), 
							Double.parseDouble(split[1]), 
							Double.parseDouble(split[2]));
		
		line = reader.readLine();
		tf_ka.setText(line);
		double ka = Double.parseDouble(line);
		
		line = reader.readLine();
		tf_ks.setText(line);
		double ks = Double.parseDouble(line);
		
		line = reader.readLine();
		tf_eta.setText(line);
		double eta = Double.parseDouble(line);
		
		line = reader.readLine();
		tf_vetorkd.setText(line);
		split = line.split(" ");
		Vetor kd = new Vetor(Double.parseDouble(split[0]), 
							Double.parseDouble(split[1]), 
							Double.parseDouble(split[2]));
		
		line = reader.readLine();
		tf_vetorod.setText(line);
		split = line.split(" ");
		Vetor od = new Vetor(Double.parseDouble(split[0]), 
							Double.parseDouble(split[1]), 
							Double.parseDouble(split[2]));
		
		line = reader.readLine();
		tf_pontopl.setText(line);
		split = line.split(" ");
		Ponto pl = new Ponto(Double.parseDouble(split[0]), 
							Double.parseDouble(split[1]), 
							Double.parseDouble(split[2]));
		
//		kd = kd.normalizar(); //TODO
//		od = od.normalizar();
		iluminacao = new Iluminacao(luzAmb, luzL, ka, ks, eta, kd, od, pl);
		reader.close();
	}
	
	private void escreverArquivoCameraVirtual() throws IOException {
		String separator = System.getProperty("file.separator");
		String path = "CameraVirtual"+separator+"camera.txt";
		writer = new BufferedWriter(new FileWriter(path));
		writer.write(tf_pontoc.getText()+"\n");
		writer.write(tf_vetorn.getText()+"\n");
		writer.write(tf_vetorv.getText()+"\n");
		writer.write(tf_d.getText()+"\n");
		writer.write(tf_hx.getText()+"\n");
		writer.write(tf_hy.getText()+"\n");
		writer.close();
	}
	
	private void escreverArquivoIluminacao() throws IOException {
		String separator = System.getProperty("file.separator");
		String path = "Iluminacao"+separator+"iluminacao.txt";
		writer = new BufferedWriter(new FileWriter(path));
		writer.write(tf_iamb.getText()+"\n");
		writer.write(tf_il.getText()+"\n");
		writer.write(tf_ka.getText()+"\n");
		writer.write(tf_ks.getText()+"\n");
		writer.write(tf_eta.getText()+"\n");
		writer.write(tf_vetorkd.getText()+"\n");
		writer.write(tf_vetorod.getText()+"\n");
		writer.write(tf_pontopl.getText()+"\n");
		writer.close();
	}
	
}
