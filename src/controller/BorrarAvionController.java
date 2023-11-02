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
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.Animal;
import model.Avion;

public class BorrarAvionController implements Initializable {

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnBorrar;

    @FXML
    private ComboBox<Animal> cbAeropuertos;

    @FXML
    private ComboBox<Avion> cbAviones;

	private AnimalesControllerEj controladorPrincipal;

    @FXML
    void cancelar(ActionEvent event) {
    	((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    void borrar(ActionEvent event) {
    	try {
    		Avion avion = cbAviones.getSelectionModel().getSelectedItem();
			DAOAviones.borrarAvion(avion);
			Alert alert = new Alert(AlertType.INFORMATION, "El avión fue borrado correctamente", ButtonType.OK);
			alert.show();
			((Stage)((Node)event.getSource()).getScene().getWindow()).close();
			controladorPrincipal.filtrarFilas();
		} catch (AnimalesException | SQLException e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
			e.printStackTrace();
		}
    }
    
    @FXML
    void cambiarAviones(ActionEvent event) {
		Animal aeropuerto = cbAeropuertos.getSelectionModel().getSelectedItem();
		try {
			cbAviones.getItems().clear();
			cbAviones.getItems().addAll(DAOAviones.getAviones(aeropuerto));
			cbAviones.getSelectionModel().selectFirst();
		} catch (AnimalesException e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
			e.printStackTrace();
		}
    }
    
	public BorrarAvionController setControladorPrincipal(AnimalesControllerEj controladorPrincipal) {
		this.controladorPrincipal = controladorPrincipal;
		return this;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			cbAeropuertos.getItems().addAll(DAOAnimales.getAnimales(null));
			cbAeropuertos.getSelectionModel().selectFirst();
			cambiarAviones(null);
		} catch (AnimalesException e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
		}
	}

}
