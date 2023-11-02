package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import dao.DAOAnimales;
import dao.DAOAviones;
import enums.TipoAeropuerto;
import excepciones.AnimalesException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Animal;
import model.Avion;
import model.Direccion;
import utilities.Utilidades;

public class AnimalesControllerEj implements Initializable {

    @FXML
    private MenuItem miAnadirAeropuerto;

    @FXML
    private MenuItem miAnadirAvion;

    @FXML
    private MenuItem miEditarAeropuerto;
    
    @FXML
    private MenuItem miBorrarAeropuerto;

    @FXML
    private MenuItem miActivarAvion;
    
    @FXML
    private MenuItem miBorrarAvion;
    
    @FXML
    private MenuItem miInfoAeropuertos;

    @FXML
    private RadioButton rbPrivados;

    @FXML
    private RadioButton rbPublicos;

    @FXML
    private TableColumn<Animal, Integer> tcAno;

    @FXML
    private TableColumn<Animal, Integer> tcCapacidad;

    @FXML
    private TableColumn<Animal, String> tcCiudad;

    @FXML
    private TableColumn<Animal, Integer> tcId;

    @FXML
    private TableColumn<Animal, String> tcNombre;

    @FXML
    private TableColumn<Animal, Integer> tcNumSocios;
    
    @FXML
    private TableColumn<Animal, Double> tcFinanciacion;
    
    @FXML
    private TableColumn<Animal, Integer> tcNumTrabajadores;

    @FXML
    private TableColumn<Animal, Integer> tcNumero;

    @FXML
    private TableColumn<Animal, String> tcPais;

    @FXML
    private TextField tfNombre;

    @FXML
    private ToggleGroup tipoAeropuerto;

    @FXML
    private TableView<Animal> tvAeropuertos;

    @FXML
    void anadirAeropuerto(ActionEvent event) {
    	abrirEditor();
    }

    @FXML
    void anadirAvion(ActionEvent event) {
    	abrirEditorAvion();
    }

    @FXML
    void buscar(KeyEvent event) {
    	filtrarFilas();
    }

    @FXML
    void editarAeropuerto(ActionEvent event) {
    	Animal seleccionado = tvAeropuertos.getSelectionModel().getSelectedItem();
    	if (seleccionado != null) {    		
    		abrirEditor(seleccionado);
    	}
    }
    
    @FXML
    void borrarAeropuerto(ActionEvent event) {
    	Animal seleccionado = tvAeropuertos.getSelectionModel().getSelectedItem();
    	if (seleccionado != null) {    		
    		Alert alert = new Alert(AlertType.CONFIRMATION, "¿Desea borrar el aeropuerto " + seleccionado.getNombre() + "?", ButtonType.OK, ButtonType.CANCEL);
    		alert.showAndWait();
    		ButtonType eleccion = alert.getResult();
    		if (ButtonType.OK.equals(eleccion)) {
    			try {
    				//PRIMERO HAY QUE BORRAR LOS AVIONES ASOCIADOS PARA NO VIOLAR LA CONSTRAINT
    				List<Avion> avionesABorrar = DAOAviones.getAviones(seleccionado);
    				avionesABorrar.forEach(avion -> {
						try {
							DAOAviones.borrarAvion(avion);
						} catch (SQLException | AnimalesException e) {
				    		Alert err = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
				    		err.show();
							e.printStackTrace();
						}
					});
					DAOAnimales.borrarAnimal(seleccionado);
					filtrarFilas();
				} catch (SQLException | AnimalesException e) {
		    		Alert err = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
		    		err.show();
					e.printStackTrace();
				}
    		}
    	}
    }

    @FXML
    void activarAvion(ActionEvent event) {
    	abrirActivadorAvion();
    }
    
    @FXML
    void borrarAvion(ActionEvent event) {
    	abrirBorrarAvion();
    }
    

    @FXML
    void mostrarInfoAeropuerto(ActionEvent event) {
    	try {    		
    		Animal seleccionado = tvAeropuertos.getSelectionModel().getSelectedItem();
    		if (seleccionado != null) {    		
    			Alert alert = new Alert(AlertType.INFORMATION, Utilidades.infoAeropuerto(seleccionado, DAOAviones.getAviones(seleccionado)), ButtonType.OK);
    			alert.setTitle("Información");
    			alert.showAndWait();
    		}
    	} catch (AnimalesException e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
    	}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tcAno.setCellValueFactory(new PropertyValueFactory<Animal, Integer>("anioInauguracion"));
		tcCapacidad.setCellValueFactory(new PropertyValueFactory<Animal, Integer>("capacidad"));
		tcId.setCellValueFactory(new PropertyValueFactory<Animal, Integer>("id"));
		tcNombre.setCellValueFactory(new PropertyValueFactory<Animal, String>("nombre"));
		tcNumSocios.setCellValueFactory(new PropertyValueFactory<Animal, Integer>("numeroSocios"));
		tcFinanciacion.setCellValueFactory(new PropertyValueFactory<Animal, Double>("financiacion"));
		tcNumTrabajadores.setCellValueFactory(new PropertyValueFactory<Animal, Integer>("numTrabajadores"));
		
		//PARA ESTOS CAMPOS HAY QUE ACCEDER A LA PROPIEDAD DIRECCIÓN, POR LO QUE USO CALLBACKS
		tcCiudad.setCellValueFactory(param -> {
			Animal aeropuerto = param.getValue();
			Direccion direccion = aeropuerto.getDireccion();
			if (direccion != null && direccion.getCiudad() != null) {
				return new SimpleStringProperty(direccion.getCiudad());
			}
			return new SimpleStringProperty();
		});
		tcNumero.setCellValueFactory(param -> {
			Animal aeropuerto = param.getValue();
			Direccion direccion = aeropuerto.getDireccion();
			if (direccion != null && direccion.getNumero() > 0) {
				return new SimpleIntegerProperty(direccion.getNumero()).asObject();
			}
			return new SimpleIntegerProperty().asObject();
		});
		tcPais.setCellValueFactory(param -> {
			Animal aeropuerto = param.getValue();
			Direccion direccion = aeropuerto.getDireccion();
			if (direccion != null && direccion.getPais() != null) {
				return new SimpleStringProperty(direccion.getPais());
			}
			return new SimpleStringProperty();
		});
		
		tipoAeropuerto.selectedToggleProperty().addListener(e -> filtrarFilas());
		
		//PARA CARGAR POR PRIMERA VEZ
		filtrarFilas();
		
		
	}

	protected void filtrarFilas() {
		String busqueda = tfNombre.getText() != null ? tfNombre.getText().toLowerCase() : "";
		tvAeropuertos.getItems().clear();
		TipoAeropuerto tipo = rbPrivados == tipoAeropuerto.getSelectedToggle() ? TipoAeropuerto.PRIVADO : TipoAeropuerto.PUBLICO;
		
		if (TipoAeropuerto.PUBLICO.equals(tipo)) {
			tcNumSocios.setVisible(false);
			tcFinanciacion.setVisible(true);
			tcNumTrabajadores.setVisible(true);
		} else {
			tcNumSocios.setVisible(true);
			tcFinanciacion.setVisible(false);
			tcNumTrabajadores.setVisible(false);			
		}
		
		try {
			tvAeropuertos.getItems().addAll(DAOAnimales.getAeropuertos(tipo, busqueda));
			tvAeropuertos.refresh();
		} catch (AnimalesException e) {
			e.printStackTrace();
		}
	}
	
	private void abrirEditor() {
		abrirEditor(null);
	}
	private void abrirEditor(Animal seleccionado) {
		FlowPane root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AnadirAeropuerto.fxml"));
			root = loader.load();
			AnadirAeropuertoController controladorAnadirAeropuerto = loader.getController();
			
			controladorAnadirAeropuerto
			.setTablaAeropuertos(tvAeropuertos)
			.setSeleccionado(seleccionado)
			.setControladorPrincipal(this);
			
			Stage stage = new Stage();
			if (seleccionado != null) {
				stage.setTitle("AEROPUERTOS - EDITAR AEROPUERTO");
			} else {
				stage.setTitle("AEROPUERTOS - AÑADIR AEROPUERTO");
			}
			stage.initModality(Modality.WINDOW_MODAL);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void abrirEditorAvion() {
		FlowPane root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AnadirAvion.fxml"));
			root = loader.load();
			AnadirAvionController controladorAnadirAvion= loader.getController();
			
			controladorAnadirAvion
			.setControladorPrincipal(this);
			
			Stage stage = new Stage();
			stage.setTitle("AVIONES - AÑADIR AVIÓN");
			stage.initModality(Modality.WINDOW_MODAL);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void abrirActivadorAvion() {
		FlowPane root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ActivarAvion.fxml"));
			root = loader.load();
			ActivarAvionController controladorActivarAvion= loader.getController();
			
			controladorActivarAvion
			.setControladorPrincipal(this);
			
			Stage stage = new Stage();
			stage.setTitle("AVIONES - ACTIVAR/DESACTIVAR AVIÓN");
			stage.initModality(Modality.WINDOW_MODAL);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void abrirBorrarAvion() {
		FlowPane root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BorrarAvion.fxml"));
			root = loader.load();
			BorrarAvionController controladorBorrarAvion= loader.getController();
			
			controladorBorrarAvion
			.setControladorPrincipal(this);
			
			Stage stage = new Stage();
			stage.setTitle("AVIONES - LOGIN");
			stage.initModality(Modality.WINDOW_MODAL);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

