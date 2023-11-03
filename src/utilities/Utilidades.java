package utilities;

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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.control.Alert.AlertType;
import model.Animal;
import model.Especie;

public class Utilidades {
	
	private Utilidades() throws IllegalAccessException {
		throw new IllegalAccessException("Clase de utilidad");
	}
	
	public static Animal mapAnimal(ResultSet rs) throws AnimalesException {
		try {
			return new Animal()
					.setCodigo(rs.getString("codigo"))
					.setNombre(rs.getString("nombre"))
					.setEspecie(new Especie()
							.setId(rs.getInt("id_especie"))
							.setNombre(rs.getString("especie")))
					.setRaza(rs.getString("raza"))
					.setSexo(Sexo.getByValor(rs.getString("sexo")))
					.setEdad(rs.getInt("edad"))
					.setPeso(rs.getDouble("peso"))
					.setObservaciones(rs.getString("observaciones"))
					.setPrimeraConsulta(rs.getDate("primera_consulta") != null ? new Date(rs.getDate("primera_consulta").getTime()) : null)
					.setFoto(rs.getBinaryStream("foto"));
			
		} catch (SQLException e) {
			throw new AnimalesException(e);
		}
	}
	
	public static double parseDouble(String str) throws AnimalesException {
		if (str != null && !str.isBlank()) {
			try {
				return Double.parseDouble(str.replace(',', '.'));
			} catch (NumberFormatException e) {/*QUE SALTE A LA EXCEPCIÓN*/}
		}
		throw new AnimalesException("Formato de número decimal incorrecto");
	}
	
	public static int parseInt(String str) throws AnimalesException {
		if (str != null && !str.isBlank()) {
			try {
				return Integer.parseInt(str);
			} catch (NumberFormatException e) {/*QUE SALTE A LA EXCEPCIÓN*/}
		}
		throw new AnimalesException("Formato de número entero incorrecto");
	}
	
	public static String emptyIfNull(String str) {
		if (str != null) {
			return str;
		}
		return "";
	}
	
	public static <T extends Number> String num2str(T num) {
		if (num != null) {
			if (num instanceof Double || num instanceof Float) {
				return String.format("%,.2f", (Double)num);
			}
			return "" + num;
		}
		return "";
	}
	
	public static Date local2Date(LocalDate ld) {
		if (ld != null) {			
			return Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
		}
		return null;
	}
	
	public static LocalDate date2Local(Date date) {
		if (date != null) {			
			return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		}
		return null;
	}
	
	public static Image inputStream2Image(InputStream is) {
		if (is != null) {
			return new Image(is);
		}
		return null;
	}
	
	public static void checkCampoDouble(TextField tf) throws AnimalesException {
		String strNum = tf.getText();
		Pattern doublePattern = Pattern.compile("\\d+([\\.,]\\d+)?");
		Matcher matcher = doublePattern.matcher(strNum);
		if (!matcher.matches()) {
			throw new AnimalesException("El campo " + tf.getId() + " contiene un formato incorrecto o está vacío");
		}
	}

	public static void checkCampoInt(TextField tf) throws AnimalesException {
		String strNum = tf.getText();
		Pattern intPattern = Pattern.compile("\\d+");
		Matcher matcher = intPattern.matcher(strNum);
		if (!matcher.matches()) {
			throw new AnimalesException("El campo " + tf.getId() + " contiene un formato incorrecto o está vacío");
		}
	}

	public static void checkCampoStrNotNull(TextField tf) throws AnimalesException {
		String str = tf.getText();
		if (str == null || str.isBlank()) {
			throw new AnimalesException("El campo" + tf.getId() + " está vacío");
		}
	}
	
	public static void lanzarError(Throwable e) {
		Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
		alert.showAndWait();
		e.printStackTrace();
	}
	
	public static void mostrarInfo(String info) {
		Alert alert = new Alert(AlertType.INFORMATION, info, ButtonType.OK);
		alert.show();
	}
	
	public static <T> T getSeleccionTabla(TableView<T> tabla) {
		if (tabla != null) {			
			return tabla.getSelectionModel().getSelectedItem();
		}
		return null;
	}
	
	public static java.sql.Date sqlDate(Date date) {
		if (date != null) {			
			return new java.sql.Date(date.getTime());
		}
		return null;
	}
	
}
