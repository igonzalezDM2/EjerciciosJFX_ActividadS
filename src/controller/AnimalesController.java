package controller;

import static utilities.Utilidades.getSeleccionTabla;
import static utilities.Utilidades.lanzarError;
import static utilities.Utilidades.mostrarInfo;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.DAOAnimales;
import enums.Sexo;
import excepciones.AnimalesException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Animal;
import model.Especie;

/**
 * Controlador para la gestión de animales.
 */
public class AnimalesController implements Initializable {

    /**
     * Elemento de menú para añadir especie.
     */
    @FXML
    private MenuItem miAnadirEspecie;

    /**
     * Elemento de menú para borrar especie.
     */
    @FXML
    private MenuItem miBorrarEspecie;

    /**
     * Elemento de menú para editar especie.
     */
    @FXML
    private MenuItem miEditarEspecie;

    /**
     * Columna de código en la tabla de animales.
     */
    @FXML
    private TableColumn<Animal, String> tcCodigo;

    /**
     * Columna de edad en la tabla de animales.
     */
    @FXML
    private TableColumn<Animal, Integer> tcEdad;

    /**
     * Columna de especie en la tabla de animales.
     */
    @FXML
    private TableColumn<Animal, Especie> tcEspecie;

    /**
     * Columna de nombre en la tabla de animales.
     */
    @FXML
    private TableColumn<Animal, String> tcNombre;

    /**
     * Columna de peso en la tabla de animales.
     */
    @FXML
    private TableColumn<Animal, Double> tcPeso;

    /**
     * Columna de raza en la tabla de animales.
     */
    @FXML
    private TableColumn<Animal, String> tcRaza;

    /**
     * Columna de sexo en la tabla de animales.
     */
    @FXML
    private TableColumn<Animal, Sexo> tcSexo;

    /**
     * Campo de texto para la búsqueda de animales.
     */
    @FXML
    private TextField tfBusqueda;

    /**
     * Tabla de visualización de animales.
     */
    @FXML
    private TableView<Animal> tvAnimales;

    /**
     * Método para añadir una especie.
     * @param event Evento de acción.
     */
    @FXML
    void anadirEspecie(ActionEvent event) {
    	abrirAnadidorDeEspecie();
    }

    /**
     * Método para borrar una especie.
     * @param event Evento de acción.
     */
    @FXML
    void borrarEspecie(ActionEvent event) {
    	abrirEditorEspecie(true);
    }

    /**
     * Método para buscar animales.
     * @param event Evento de acción.
     */
    @FXML
    void buscar(KeyEvent event) {
    	filtrarFilas();
    }

    /**
     * Método para editar una especie.
     * @param event Evento de acción.
     */
    @FXML
    void editarEspecie(ActionEvent event) {
    	abrirEditorEspecie();
    }
    
    /**
     * Método para inicializar el controlador.
     * @param location Ubicación de los recursos.
     * @param resources Recursos.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tcCodigo.setCellValueFactory(new PropertyValueFactory<Animal, String>("codigo"));
		tcNombre.setCellValueFactory(new PropertyValueFactory<Animal, String>("nombre"));
		tcEspecie.setCellValueFactory(new PropertyValueFactory<Animal, Especie>("especie"));
		tcNombre.setCellValueFactory(new PropertyValueFactory<Animal, String>("nombre"));
		tcRaza.setCellValueFactory(new PropertyValueFactory<Animal, String>("raza"));
		tcSexo.setCellValueFactory(new PropertyValueFactory<Animal, Sexo>("sexo"));
		tcEdad.setCellValueFactory(new PropertyValueFactory<Animal, Integer>("edad"));
		tcPeso.setCellValueFactory(new PropertyValueFactory<Animal, Double>("peso"));
		
		ContextMenu cm = new ContextMenu();
		MenuItem miAnadirAnimal = new MenuItem("Añadir");
		miAnadirAnimal.setOnAction(ev -> {
			System.out.println();
			abrirEditor();
		});
		MenuItem miEditarAnimal = new MenuItem("Editar");
		miEditarAnimal.setOnAction(ev -> {
			Animal seleccionado = getSeleccionTabla(tvAnimales);
			if (seleccionado != null) {
				abrirEditor(seleccionado);
			}
		});
		
		MenuItem miBorrarAnimal = new MenuItem("Borrar");
		miBorrarAnimal.setOnAction(ev -> {
			Animal seleccionado = getSeleccionTabla(tvAnimales);
			if (seleccionado != null) {
				Alert alert = new Alert(AlertType.CONFIRMATION, "¿Desea borrar el animal seleccionado de la Base de Datos?", ButtonType.YES, ButtonType.NO);
				Optional<ButtonType> decision = alert.showAndWait();
				if (decision.isPresent() && ButtonType.YES.equals(decision.get())) {
					try {
						DAOAnimales.borrarAnimal(seleccionado);
						filtrarFilas();
						mostrarInfo("El animal fue borrado");
					} catch (AnimalesException | SQLException e) {
						lanzarError(e);
					}
				}
			}
		});
		
		cm.getItems().addAll(miAnadirAnimal, miEditarAnimal, miBorrarAnimal);
		
		
		cm.setOnShowing(ev -> {
			if (getSeleccionTabla(tvAnimales) != null) {
				miBorrarAnimal.setVisible(true);
				miEditarAnimal.setVisible(true);
			} else {
				miBorrarAnimal.setVisible(false);
				miEditarAnimal.setVisible(false);
				
			}
		});
		tvAnimales.setContextMenu(cm);
		
		//PARA CARGAR POR PRIMERA VEZ
		filtrarFilas();
		
		
	}
	
    /**
     * Método para filtrar las filas de la tabla de animales.
     */
	protected void filtrarFilas() {
		String busqueda = tfBusqueda.getText() != null ? tfBusqueda.getText().toLowerCase() : "";
		tvAnimales.getItems().clear();
		try {
			tvAnimales.getItems().addAll(DAOAnimales.getAnimales(busqueda));
			tvAnimales.refresh();
		} catch (AnimalesException e) {
			e.printStackTrace();
		}
	}

    /**
     * Método para abrir el editor de animales.
     */
	private void abrirEditor() {
		abrirEditor(null);
	}
	
    /**
     * Método para abrir el editor de animales con un animal seleccionado.
     * @param seleccionado Animal seleccionado.
     */
	private void abrirEditor(Animal seleccionado) {
		FlowPane root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AnadirAnimal.fxml"));
			root = loader.load();
			AnadirAnimalController controladorAnadirAnimal = loader.getController();
			
			controladorAnadirAnimal
			.setSeleccionado(seleccionado)
			.setControladorPrincipal(this);
			
			Stage stage = new Stage();
			if (seleccionado != null) {
				stage.setTitle("ANIMALES - CAMBIAR DATOS");
			} else {
				stage.setTitle("ANIMALES - ALTA");
			}
			stage.initModality(Modality.WINDOW_MODAL);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    /**
     * Método para abrir el añadidor de especies.
     */
	private void abrirAnadidorDeEspecie() {
		FlowPane root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AnadirEspecie.fxml"));
			root = loader.load();
			
			Stage stage = new Stage();
			stage.setTitle("AÑADIR ESPECIE");
			stage.initModality(Modality.WINDOW_MODAL);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    /**
     * Método para abrir el editor de especies.
     */
	private void abrirEditorEspecie() {
		abrirEditorEspecie(false);
	}
	
    /**
     * Método para abrir el editor de especies con opción de borrar.
     * @param borrar Indica si se va a borrar la especie.
     */
	private void abrirEditorEspecie(boolean borrar) {
		FlowPane root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BorrarEspecie.fxml"));
			root = loader.load();
			ModificarEspecieController controlador = loader.getController();
			
			controlador
			.setControladorPrincipal(this)
			.setBorrar(borrar);
			
			Stage stage = new Stage();
			if (borrar) {
				stage.setTitle("BORRAR ESPECIE");
			} else {
				stage.setTitle("MODIFICAR ESPECIE");
			}
			stage.initModality(Modality.WINDOW_MODAL);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
