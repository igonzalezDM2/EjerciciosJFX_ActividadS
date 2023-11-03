package utilities;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import enums.Sexo;
import excepciones.AnimalesException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import model.Animal;
import model.Especie;

/**
 * Clase de utilidades para el proyecto.
 */
public class Utilidades {
	
	private Utilidades() throws IllegalAccessException {
		throw new IllegalAccessException("Clase de utilidad");
	}
	
	/**
	 * Mapea un ResultSet a un objeto Animal.
	 * 
	 * @param rs ResultSet a mapear
	 * @return Objeto Animal mapeado
	 * @throws AnimalesException si ocurre un error al mapear el ResultSet
	 */
	public static Animal mapAnimal(ResultSet rs) throws AnimalesException {
		try {
			return new Animal()
					.setCodigo(rs.getString("codigo"))
					.setNombre(rs.getString("nombre_animal"))
					.setEspecie(new Especie()
							.setId(rs.getInt("id_especie"))
							.setNombre(rs.getString("nombre_especie")))
					.setRaza(rs.getString("raza"))
					.setSexo(Sexo.getByValor(rs.getString("sexo")))
					.setEdad(rs.getInt("edad"))
					.setPeso(rs.getDouble("peso"))
					.setObservaciones(rs.getString("observaciones"))
					.setPrimeraConsulta(rs.getDate("primera_consulta") != null ? new Date(rs.getDate("primera_consulta").getTime()) : null)
					.setFoto(rs.getBinaryStream("foto").readAllBytes());
			
		} catch (SQLException | IOException e) {
			throw new AnimalesException(e);
		}
	}
	
	/**
	 * Convierte una cadena en formato decimal a un valor double.
	 * 
	 * @param str cadena a convertir
	 * @return valor double convertido
	 * @throws AnimalesException si la cadena no tiene un formato decimal válido
	 */
	public static double parseDouble(String str) throws AnimalesException {
		if (str != null && !str.isBlank()) {
			try {
				return Double.parseDouble(str.replace(',', '.'));
			} catch (NumberFormatException e) {/*QUE SALTE A LA EXCEPCIÓN*/}
		}
		throw new AnimalesException("Formato de número decimal incorrecto");
	}
	
	/**
	 * Convierte una cadena en formato entero a un valor int.
	 * 
	 * @param str cadena a convertir
	 * @return valor int convertido
	 * @throws AnimalesException si la cadena no tiene un formato entero válido
	 */
	public static int parseInt(String str) throws AnimalesException {
		if (str != null && !str.isBlank()) {
			try {
				return Integer.parseInt(str);
			} catch (NumberFormatException e) {/*QUE SALTE A LA EXCEPCIÓN*/}
		}
		throw new AnimalesException("Formato de número entero incorrecto");
	}
	
	/**
	 * Devuelve una cadena vacía si la cadena de entrada es nula.
	 * 
	 * @param str cadena de entrada
	 * @return cadena vacía si la cadena de entrada es nula, de lo contrario, la cadena de entrada sin cambios
	 */
	public static String emptyIfNull(String str) {
		if (str != null) {
			return str;
		}
		return "";
	}
	
	/**
	 * Convierte un número en una cadena.
	 * 
	 * @param num número a convertir
	 * @return cadena que representa el número
	 */
	public static <T extends Number> String num2str(T num) {
		if (num != null) {
			if (num instanceof Double || num instanceof Float) {
				return String.format("%,.2f", (Double)num);
			}
			return "" + num;
		}
		return "";
	}
	
	/**
	 * Convierte una fecha LocalDate a un objeto Date.
	 * 
	 * @param ld fecha LocalDate a convertir
	 * @return objeto Date convertido
	 */
	public static Date local2Date(LocalDate ld) {
		if (ld != null) {			
			return Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
		}
		return null;
	}
	
	/**
	 * Convierte un objeto Date a una fecha LocalDate.
	 * 
	 * @param date objeto Date a convertir
	 * @return fecha LocalDate convertida
	 */
	public static LocalDate date2Local(Date date) {
		if (date != null) {			
			return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		}
		return null;
	}
	
	/**
	 * Convierte un array de bytes en una imagen.
	 * 
	 * @param bytes array de bytes a convertir
	 * @return imagen convertida
	 * @throws AnimalesException si ocurre un error al convertir el array de bytes en imagen
	 */
	public static Image byte2Image(byte[] bytes) throws AnimalesException {
		if (bytes != null) {
			try (InputStream is = new ByteArrayInputStream(bytes)) {				
				return new Image(is);
			} catch (IOException e) {
				throw new AnimalesException(e);
			}
		}
		return null;
	}
	
	/**
	 * Verifica que el contenido de un TextField sea un número decimal válido.
	 * 
	 * @param tf TextField a verificar
	 * @throws AnimalesException si el contenido del TextField no es un número decimal válido
	 */
	public static void checkCampoDouble(TextField tf) throws AnimalesException {
		String strNum = tf.getText();
		Pattern doublePattern = Pattern.compile("\\d+([\\.,]\\d+)?");
		Matcher matcher = doublePattern.matcher(strNum);
		if (!matcher.matches()) {
			throw new AnimalesException("El campo " + tf.getId() + " contiene un formato incorrecto o está vacío");
		}
	}

	/**
	 * Verifica que el contenido de un TextField sea un número entero válido.
	 * 
	 * @param tf TextField a verificar
	 * @throws AnimalesException si el contenido del TextField no es un número entero válido
	 */
	public static void checkCampoInt(TextField tf) throws AnimalesException {
		String strNum = tf.getText();
		Pattern intPattern = Pattern.compile("\\d+");
		Matcher matcher = intPattern.matcher(strNum);
		if (!matcher.matches()) {
			throw new AnimalesException("El campo " + tf.getId() + " contiene un formato incorrecto o está vacío");
		}
	}

	/**
	 * Verifica que el contenido de un TextField no sea nulo ni vacío.
	 * 
	 * @param tf TextField a verificar
	 * @throws AnimalesException si el contenido del TextField es nulo o vacío
	 */
	public static void checkCampoStrNotNull(TextField tf) throws AnimalesException {
		String str = tf.getText();
		if (str == null || str.isBlank()) {
			throw new AnimalesException("El campo" + tf.getId() + " está vacío");
		}
	}
	
	/**
	 * Lanza un diálogo de error con el mensaje de error y muestra la traza de la excepción en la consola.
	 * 
	 * @param e excepción a lanzar
	 */
	public static void lanzarError(Throwable e) {
		Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
		alert.showAndWait();
		e.printStackTrace();
	}
	
	/**
	 * Muestra un diálogo de información con el mensaje de información.
	 * 
	 * @param info mensaje de información a mostrar
	 */
	public static void mostrarInfo(String info) {
		Alert alert = new Alert(AlertType.INFORMATION, info, ButtonType.OK);
		alert.show();
	}
	
	/**
	 * Devuelve el elemento seleccionado en una tabla.
	 * 
	 * @param tabla tabla de la que se obtendrá el elemento seleccionado
	 * @return elemento seleccionado en la tabla
	 */
	public static <T> T getSeleccionTabla(TableView<T> tabla) {
		if (tabla != null) {			
			return tabla.getSelectionModel().getSelectedItem();
		}
		return null;
	}
	
	/**
	 * Convierte un objeto Date a un objeto java.sql.Date.
	 * 
	 * @param date objeto Date a convertir
	 * @return objeto java.sql.Date convertido
	 */
	public static java.sql.Date sqlDate(Date date) {
		if (date != null) {			
			return new java.sql.Date(date.getTime());
		}
		return null;
	}
	
}
