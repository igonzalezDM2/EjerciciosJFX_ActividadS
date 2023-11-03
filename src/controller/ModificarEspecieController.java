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

/**
 * Controlador para modificar especies.
 */
public class ModificarEspecieController implements Initializable{
	
	/**
	 * Controlador principal de animales.
	 */
	AnimalesController controladorPrincipal;
	
	/**
	 * Variable para determinar si se va a borrar una especie.
	 */
	private boolean borrar;
	
	/**
	 * Método para establecer si se va a borrar una especie.
	 * @param borrar Valor booleano para determinar si se va a borrar una especie.
	 */
	public void setBorrar(boolean borrar) {
		if (this.borrar = borrar) {
			btnModificar.setText("Borrar");
			grid.getChildren().remove(tfNuevoNombre);
			grid.getChildren().remove(lblNuevoNombre);
			grid.getRowConstraints().remove(1);
			titulo.setText("BORRAR ESPECIE");
		}
	}
	
	/**
	 * Método para establecer el controlador principal.
	 * @param controladorPrincipal Controlador principal de animales.
	 * @return Retorna la instancia de este controlador.
	 */
	public ModificarEspecieController setControladorPrincipal(AnimalesController controladorPrincipal) {
		this.controladorPrincipal = controladorPrincipal;
		return this;
	}

    /**
     * Botón para cancelar la acción.
     */
    @FXML
    private Button btnCancelar;

    /**
     * Botón para modificar la especie.
     */
    @FXML
    private Button btnModificar;

    /**
     * ComboBox para seleccionar la especie a modificar.
     */
    @FXML
    private ComboBox<Especie> cbEspecie;

    /**
     * Etiqueta para el nuevo nombre de la especie.
     */
    @FXML
    private Label lblNuevoNombre;

    /**
     * Campo de texto para ingresar el nuevo nombre de la especie.
     */
    @FXML
    private TextField tfNuevoNombre;
    
    /**
     * GridPane para la interfaz de usuario.
     */
    @FXML
    private GridPane grid;
    
    /**
     * Etiqueta para el título de la interfaz de usuario.
     */
    @FXML
    private Label titulo;

    /**
     * Método para cancelar la acción.
     * @param event Evento de acción.
     */
    @FXML
    void cancelar(ActionEvent event) {
    	((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }

    /**
     * Método para modificar la especie.
     * @param event Evento de acción.
     */
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
	    		controladorPrincipal.filtrarFilas();
	    		//PARA SALIR
	    		cancelar(event);
	    	}
    	} catch (AnimalesException | SQLException e) {
    		Utilidades.lanzarError(e);
    	}
    }

	/**
	 * Método para inicializar la interfaz de usuario.
	 * @param location Ubicación de la interfaz de usuario.
	 * @param resources Recursos para la interfaz de usuario.
	 */
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
