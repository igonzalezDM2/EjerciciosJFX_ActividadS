package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dao.DAOEspecies;
import excepciones.AnimalesException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Especie;
import utilities.Utilidades;

public class AnadirEspecieController implements Initializable {

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnGuardar;

    @FXML
    private TextField tfNombre;

    @FXML
    void guardar(ActionEvent event) {
    	if (tfNombre.getText() != null && !tfNombre.getText().isBlank()) {    		
    		try {
    			DAOEspecies.anadirEspecie(new Especie().setNombre(tfNombre.getText().trim()));
    			Utilidades.mostrarInfo("La especie fue insertada");
    			//PARA CERRARLO
    			cancelar(event);
    		} catch (AnimalesException | SQLException e) {
    			Utilidades.lanzarError(e);
    		}
    	}
    }

    @FXML
    void cancelar(ActionEvent event) {
    	((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
