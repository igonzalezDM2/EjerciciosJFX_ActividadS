package enums;

import java.util.Arrays;

/**
 * Enumeración para los sexos.
 */
public enum Sexo {
	/**
	 * Representa el sexo masculino.
	 */
	MACHO("M"),
	
	/**
	 * Representa el sexo femenino.
	 */
	HEMBRA("H"),
	
	/**
	 * Representa otro sexo.
	 */
	OTRO("O");
	
	private String valor;
	
	/**
	 * Constructor de la enumeración Sexo.
	 * 
	 * @param valor el valor del sexo
	 */
	private Sexo(String valor) {
		this.valor = valor;
	}
	
	/**
	 * Obtiene el valor del sexo.
	 * 
	 * @return el valor del sexo
	 */
	public String getValor() {
		return valor;
	}
	
	/**
	 * Obtiene el sexo correspondiente al valor especificado.
	 * 
	 * @param valor el valor del sexo
	 * @return el sexo correspondiente al valor especificado, o null si no se encuentra
	 */
	public static Sexo getByValor(String valor) {
		return Arrays.stream(Sexo.values())
		.filter(s -> s.getValor().equals(valor))
		.findFirst().orElse(null);
	}
	
}
