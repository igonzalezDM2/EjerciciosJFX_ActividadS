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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Especie;
import utilities.Utilidades;

public class ModificarEspecieController implements Initializable{
	
	private boolean borrar;
	
	public void setBorrar(boolean borrar) {
		if (this.borrar = borrar) {
			btnModificar.setText("Borrar");
			grid.getChildren().remove(tfNuevoNombre);
			grid.getChildren().remove(lblNuevoNombre);
			grid.getRowConstraints().remove(1);
			titulo.setText("BORRAR ESPECIE");
		}
	}

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnModificar;

    @FXML
    private ComboBox<Especie> cbEspecie;

    @FXML
    private Label lblNuevoNombre;

    @FXML
    private TextField tfNuevoNombre;
    
    @FXML
    private GridPane grid;
    
    @FXML
    private Label titulo;

    @FXML
    void cancelar(ActionEvent event) {
    	((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    void modificar(ActionEvent event) {
    	Especie especie = cbEspecie.getSelectionModel().getSelectedItem();
    	try {
	    	if (especie != null) {
	    		if (this.borrar) {
					DAOEspecies.borrarEspecie(especie);
					Utilidades.mostrarInfo("La especie fue borrada");
	    		} else if (tfNuevoNombre.getText() != null && !tfNuevoNombre.getText().isBlank()) {
	    			DAOEspecies.modificarEspecie(especie.setNombre(tfNuevoNombre.getText().trim()));
	    			Utilidades.mostrarInfo("La especie fue modificada");
	    		} else {
	    			Utilidades.mostrarInfo("No ha puesto un nombre válido");	    			
	    		}
	    	}
    	} catch (AnimalesException | SQLException e) {
    		Utilidades.lanzarError(e);
    	}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			btnModificar.setText("Guardar");
			titulo.setText("MODIFICAR ESPECIE");			
			cbEspecie.getItems().addAll(DAOEspecies.getEspecies());
			cbEspecie.getSelectionModel().selectFirst();
		} catch (AnimalesException e) {
			Utilidades.lanzarError(e);
		}
	}

}
