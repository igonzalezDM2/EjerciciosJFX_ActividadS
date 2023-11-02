package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dao.DAOAnimales;
import dao.DAOAviones;
import excepciones.AnimalesException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.Animal;
import model.Avion;
import utilities.Utilidades;

public class AnadirAvionController implements Initializable {
	
	private AnimalesControllerEj controladorPrincipal;

	@FXML
    private Button btnCancelar;

    @FXML
    private Button btnGuardar;

    @FXML
    private ComboBox<Animal> cbAeropuerto;

    @FXML
    private RadioButton rbActivado;

    @FXML
    private RadioButton rbDesactivado;

    @FXML
    private TextField tfAsientos;

    @FXML
    private TextField tfModelo;

    @FXML
    private TextField tfVelMax;

    @FXML
    private ToggleGroup tgActivo;
    
    public void setControladorPrincipal(AnimalesControllerEj controladorPrincipal) {
		this.controladorPrincipal = controladorPrincipal;
	}

    @FXML
    void cancelar(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
    }

    @FXML
    void guardar(ActionEvent event) {
    	try {
			try {
				comprobarDatos();
				Avion avion = construirAvion();
				DAOAviones.checkExiste(avion);
				DAOAviones.anadirAvion(avion);
				Alert alert = new Alert(AlertType.INFORMATION, "El aeropuerto fue insertado", ButtonType.OK);
				alert.show();
				resetearCampos();
				controladorPrincipal.filtrarFilas();
			} catch (SQLException e) {
				Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
	    		alert.showAndWait();
				e.printStackTrace();
			}			
			
		} catch (AnimalesException e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
    		alert.showAndWait();
    		e.printStackTrace();
		}
    	
    }
    
	private void comprobarDatos() throws AnimalesException  {
		AnadirAeropuertoController.checkCampoStrNotNull(tfModelo);
		AnadirAeropuertoController.checkCampoStrNotNull(tfAsientos);
		AnadirAeropuertoController.checkCampoStrNotNull(tfVelMax);
		AnadirAeropuertoController.checkCampoInt(tfAsientos);
		AnadirAeropuertoController.checkCampoInt(tfVelMax);
		Animal aeropuerto = cbAeropuerto.getSelectionModel().getSelectedItem();
		if (aeropuerto == null) {
			Alert alert = new Alert(AlertType.ERROR, "Debes seleccionar un aeropuerto", ButtonType.OK);
    		alert.showAndWait();
		}
		
	}
	
	private Avion construirAvion() throws AnimalesException {
		return new Avion()
				.setActivado(rbActivado.equals(tgActivo.getSelectedToggle()))
				.setAeropuerto(cbAeropuerto.getSelectionModel().getSelectedItem())
				.setModelo(tfModelo.getText())
				.setNumeroAsientos(Utilidades.parseInt(tfAsientos.getText()))
				.setVelocidadMaxima(Utilidades.parseInt(tfVelMax.getText()));
	}
	
	private void resetearCampos() {
		tfModelo.clear();
		tfAsientos.clear();
		tfVelMax.clear();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			cbAeropuerto.getItems().addAll(DAOAnimales.getAnimales(null));
			cbAeropuerto.getSelectionModel().selectFirst();
		} catch (AnimalesException e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
    		alert.showAndWait();
    		e.printStackTrace();
		}
		
		
	}

}
