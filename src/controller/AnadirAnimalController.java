package controller;

import static utilities.Utilidades.date2Local;
import static utilities.Utilidades.emptyIfNull;
import static utilities.Utilidades.inputStream2Image;
import static utilities.Utilidades.lanzarError;
import static utilities.Utilidades.num2str;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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

public class AnadirAnimalController implements Initializable {
	
	private AnimalesController controladorPrincipal;
	
	private Animal seleccionado;
	private InputStream imagenSeleccionada;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnImagen;

    @FXML
    private ComboBox<Especie> cbEspecie;

    @FXML
    private ComboBox<Sexo> cbSexo;

    @FXML
    private DatePicker dpPrimeraConsulta;

    @FXML
    private TextArea taObservaciones;

    @FXML
    private TextField tfCodigo;

    @FXML
    private TextField tfEdad;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfPeso;

    @FXML
    private TextField tfRaza;

    @FXML
    private ImageView tvImagen;
    
    public AnadirAnimalController setControladorPrincipal(AnimalesController controladorPrincipal) {
    	this.controladorPrincipal = controladorPrincipal;
    	return this;
    }
    
    public AnadirAnimalController setSeleccionado(Animal animalSeleccionado) {
    	this.seleccionado = animalSeleccionado;
    	rellenarEditor();
    	return this;
    }

    @FXML
    void cancelar(ActionEvent event) {
    	((Stage)((Node) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    void guardar(ActionEvent event) {
    	try {
			comprobarDatos();
			Animal animal = construirAnimal();
			if (this.seleccionado != null) {
				DAOAnimales.modificarAnimal(animal);
			} else {				
				DAOAnimales.anadirAnimal(animal);
			}
			
			controladorPrincipal.filtrarFilas();
			Utilidades.mostrarInfo("EL animal fue " + seleccionado != null ? "modificado" : "insertado");
			Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
			stage.close();
		} catch (AnimalesException | SQLException e) {
			lanzarError(e);
		}

    }
    
    @FXML
    void seleccionarImagen(ActionEvent event) {
    	FileChooser fc = new FileChooser();
    	fc.getExtensionFilters().add(new ExtensionFilter("Imágenes JPG y PNG", Arrays.asList("*.jpg", "*.png")));
    	File fichero = fc.showOpenDialog(((Node)event.getSource()).getScene().getWindow());
    	if (fichero != null) {
    		try {
				FileInputStream fis = new FileInputStream(fichero);
				this.imagenSeleccionada = fis;
				tvImagen.setImage(inputStream2Image(fis));
			} catch (FileNotFoundException e) {
				lanzarError(e);
			}
    	}
    }
    
    private void comprobarDatos() throws AnimalesException {
    	Utilidades.checkCampoStrNotNull(tfCodigo);
    	Utilidades.checkCampoStrNotNull(tfNombre);
    	Utilidades.checkCampoInt(tfEdad);
    	Utilidades.checkCampoDouble(tfPeso);
    }
    
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
    
    private void rellenarEditor() {
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
			tvImagen.setImage(inputStream2Image(seleccionado.getFoto()));
		} else {
			cbSexo.getSelectionModel().selectFirst();				
			cbEspecie.getSelectionModel().selectFirst();				
		}
    }

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

