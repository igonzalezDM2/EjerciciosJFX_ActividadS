package controller;

import static utilities.Utilidades.byte2Image;
import static utilities.Utilidades.date2Local;
import static utilities.Utilidades.emptyIfNull;
import static utilities.Utilidades.lanzarError;
import static utilities.Utilidades.num2str;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;

import dao.DAOAnimales;
import dao.DAOEspecies;
import enums.Sexo;
import excepciones.AnimalesException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.Animal;
import model.Especie;
import utilities.Utilidades;

/**
 * Controlador para añadir animales.
 */
public class AnadirAnimalController implements Initializable {
	
	private AnimalesController controladorPrincipal;
	
	private Animal seleccionado;
	private byte[] imagenSeleccionada;

    /**
     * Botón para cancelar la operación.
     */
    @FXML
    private Button btnCancelar;

    /**
     * Botón para guardar los cambios.
     */
    @FXML
    private Button btnGuardar;

    /**
     * Botón para seleccionar una imagen.
     */
    @FXML
    private Button btnImagen;

    /**
     * ComboBox para seleccionar la especie del animal.
     */
    @FXML
    private ComboBox<Especie> cbEspecie;

    /**
     * ComboBox para seleccionar el sexo del animal.
     */
    @FXML
    private ComboBox<Sexo> cbSexo;

    /**
     * DatePicker para seleccionar la fecha de la primera consulta.
     */
    @FXML
    private DatePicker dpPrimeraConsulta;

    /**
     * TextArea para introducir las observaciones.
     */
    @FXML
    private TextArea taObservaciones;

    /**
     * TextField para introducir el código del animal.
     */
    @FXML
    private TextField tfCodigo;

    /**
     * TextField para introducir la edad del animal.
     */
    @FXML
    private TextField tfEdad;

    /**
     * TextField para introducir el nombre del animal.
     */
    @FXML
    private TextField tfNombre;

    /**
     * TextField para introducir el peso del animal.
     */
    @FXML
    private TextField tfPeso;

    /**
     * TextField para introducir la raza del animal.
     */
    @FXML
    private TextField tfRaza;

    /**
     * ImageView para mostrar la imagen del animal.
     */
    @FXML
    private ImageView tvImagen;
    
    /**
     * Método para establecer el controlador principal.
     * @param controladorPrincipal El controlador principal.
     * @return El controlador de añadir animal.
     */
    public AnadirAnimalController setControladorPrincipal(AnimalesController controladorPrincipal) {
    	this.controladorPrincipal = controladorPrincipal;
    	return this;
    }
    
    /**
     * Método para establecer el animal seleccionado.
     * @param animalSeleccionado El animal seleccionado.
     * @return El controlador de añadir animal.
     */
    public AnadirAnimalController setSeleccionado(Animal animalSeleccionado) {
    	this.seleccionado = animalSeleccionado;
    	rellenarEditor();
    	return this;
    }

    /**
     * Método para cancelar la operación.
     * @param event El evento de acción.
     */
    @FXML
    void cancelar(ActionEvent event) {
    	((Stage)((Node) event.getSource()).getScene().getWindow()).close();
    }

    /**
     * Método para guardar los cambios.
     * @param event El evento de acción.
     */
    @FXML
    void guardar(ActionEvent event) {
    	try {
			comprobarDatos();
			Animal animal = construirAnimal();
			try {
				if (this.seleccionado != null) {
					DAOAnimales.modificarAnimal(animal);
				} else {				
						DAOAnimales.anadirAnimal(animal);
				}
			} catch (IOException e) {
				lanzarError(e);
			}
			
			controladorPrincipal.filtrarFilas();
			Utilidades.mostrarInfo("EL animal fue " + seleccionado != null ? "modificado" : "insertado");
			Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
			stage.close();
		} catch (AnimalesException | SQLException e) {
			lanzarError(e);
		}

    }
    
    /**
     * Método para seleccionar una imagen.
     * @param event El evento de acción.
     */
    @FXML
    void seleccionarImagen(ActionEvent event) {
    	FileChooser fc = new FileChooser();
    	fc.getExtensionFilters().add(new ExtensionFilter("Imágenes JPG y PNG", Arrays.asList("*.jpg", "*.png")));
    	File fichero = fc.showOpenDialog(((Node)event.getSource()).getScene().getWindow());
    	if (fichero != null) {
    		try (FileInputStream fis = new FileInputStream(fichero)) {
				
				this.imagenSeleccionada = fis.readAllBytes();
				tvImagen.setImage(byte2Image(this.imagenSeleccionada));
			} catch (IOException | AnimalesException e) {
				lanzarError(e);
			}
    	}
    }
    
    /**
     * Método para comprobar los datos.
     * @throws AnimalesException Si los datos no son válidos.
     */
    private void comprobarDatos() throws AnimalesException {
    	Utilidades.checkCampoStrNotNull(tfCodigo);
    	Utilidades.checkCampoStrNotNull(tfNombre);
    	Utilidades.checkCampoInt(tfEdad);
    	Utilidades.checkCampoDouble(tfPeso);
    }
    
    /**
     * Método para construir un animal.
     * @return El animal construido.
     * @throws AnimalesException Si los datos no son válidos.
     */
    private Animal construirAnimal() throws AnimalesException {
    	return new Animal()
    			.setCodigo(tfCodigo.getText())
    			.setNombre(tfNombre.getText())
    			.setEspecie(cbEspecie.getValue())
    			.setRaza(tfRaza.getText())
    			.setSexo(cbSexo.getValue())
    			.setEdad(Utilidades.parseInt(tfEdad.getText()))
    			.setPeso(Utilidades.parseDouble(tfPeso.getText()))
    			.setObservaciones(taObservaciones.getText())
    			.setPrimeraConsulta(Utilidades.local2Date(dpPrimeraConsulta.getValue()))
    			.setFoto(imagenSeleccionada);
    }
    
    /**
     * Método para rellenar el editor.
     */
    private void rellenarEditor() {
    	try {
    		if (seleccionado != null) {
    			cbEspecie.getSelectionModel().select(seleccionado.getEspecie());
    			cbSexo.getSelectionModel().select(seleccionado.getSexo());
    			tfCodigo.setText(emptyIfNull(seleccionado.getCodigo()));
    			
    			//DESHABILITO EL CAMPO DE LA CLAVE PRIMARIA AL EDITAR
    			tfCodigo.setDisable(true);
    			
    			tfNombre.setText(emptyIfNull(seleccionado.getNombre()));
    			tfRaza.setText(emptyIfNull(seleccionado.getRaza()));
    			tfEdad.setText(num2str(seleccionado.getEdad()));
    			tfPeso.setText(num2str(seleccionado.getPeso()));
    			taObservaciones.setText(emptyIfNull(seleccionado.getObservaciones()));
    			dpPrimeraConsulta.setValue(date2Local(seleccionado.getPrimeraConsulta()));
    			tvImagen.setImage(byte2Image(seleccionado.getFoto()));
    		} else {
    			cbSexo.getSelectionModel().selectFirst();				
    			cbEspecie.getSelectionModel().selectFirst();				
    		}
    	} catch (AnimalesException e) {
    		lanzarError(e);
    	}
    }

    /**
     * Método para inicializar el controlador.
     * @param location La ubicación para la inicialización.
     * @param resources Los recursos para la inicialización.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			cbEspecie.getItems().addAll(DAOEspecies.getEspecies().stream().sorted((e1, e2) -> e1.getNombre().compareTo(e2.getNombre())).toList());
			cbSexo.getItems().addAll(Sexo.values());
		} catch (AnimalesException e) {
			lanzarError(e);
		}
	}

}

