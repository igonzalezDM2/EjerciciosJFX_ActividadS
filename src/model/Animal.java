package model;

import java.util.Date;

import enums.Sexo;

/**
 * Clase que representa un animal.
 */
public class Animal {
	private String codigo;
	private String nombre;
	private Especie especie;
	private String raza;
	private Sexo sexo;
	private Integer edad;
	private Double peso;
	private String observaciones;
	private Date primeraConsulta;
	private byte[] foto;
	
	/**
	 * Obtiene el código del animal.
	 * 
	 * @return el código del animal
	 */
	public String getCodigo() {
		return codigo;
	}
	
	/**
	 * Establece el código del animal.
	 * 
	 * @param codigo el código del animal
	 * @return la instancia actual del animal
	 */
	public Animal setCodigo(String codigo) {
		this.codigo = codigo;
		return this;
	}
	
	/**
	 * Obtiene el nombre del animal.
	 * 
	 * @return el nombre del animal
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Establece el nombre del animal.
	 * 
	 * @param nombre el nombre del animal
	 * @return la instancia actual del animal
	 */
	public Animal setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}
	
	/**
	 * Obtiene la especie del animal.
	 * 
	 * @return la especie del animal
	 */
	public Especie getEspecie() {
		return especie;
	}
	
	/**
	 * Establece la especie del animal.
	 * 
	 * @param especie la especie del animal
	 * @return la instancia actual del animal
	 */
	public Animal setEspecie(Especie especie) {
		this.especie = especie;
		return this;
	}
	
	/**
	 * Obtiene la raza del animal.
	 * 
	 * @return la raza del animal
	 */
	public String getRaza() {
		return raza;
	}
	
	/**
	 * Establece la raza del animal.
	 * 
	 * @param raza la raza del animal
	 * @return la instancia actual del animal
	 */
	public Animal setRaza(String raza) {
		this.raza = raza;
		return this;
	}
	
	/**
	 * Obtiene el sexo del animal.
	 * 
	 * @return el sexo del animal
	 */
	public Sexo getSexo() {
		return sexo;
	}
	
	/**
	 * Establece el sexo del animal.
	 * 
	 * @param sexo el sexo del animal
	 * @return la instancia actual del animal
	 */
	public Animal setSexo(Sexo sexo) {
		this.sexo = sexo;
		return this;
	}
	
	/**
	 * Obtiene la edad del animal.
	 * 
	 * @return la edad del animal
	 */
	public Integer getEdad() {
		return edad;
	}
	
	/**
	 * Establece la edad del animal.
	 * 
	 * @param edad la edad del animal
	 * @return la instancia actual del animal
	 */
	public Animal setEdad(Integer edad) {
		this.edad = edad;
		return this;
	}
	
	/**
	 * Obtiene el peso del animal.
	 * 
	 * @return el peso del animal
	 */
	public Double getPeso() {
		return peso;
	}
	
	/**
	 * Establece el peso del animal.
	 * 
	 * @param peso el peso del animal
	 * @return la instancia actual del animal
	 */
	public Animal setPeso(Double peso) {
		this.peso = peso;
		return this;
	}
	
	/**
	 * Obtiene las observaciones del animal.
	 * 
	 * @return las observaciones del animal
	 */
	public String getObservaciones() {
		return observaciones;
	}
	
	/**
	 * Establece las observaciones del animal.
	 * 
	 * @param observaciones las observaciones del animal
	 * @return la instancia actual del animal
	 */
	public Animal setObservaciones(String observaciones) {
		this.observaciones = observaciones;
		return this;
	}
	
	/**
	 * Obtiene la fecha de la primera consulta del animal.
	 * 
	 * @return la fecha de la primera consulta del animal
	 */
	public Date getPrimeraConsulta() {
		return primeraConsulta;
	}
	
	/**
	 * Establece la fecha de la primera consulta del animal.
	 * 
	 * @param primeraConsulta la fecha de la primera consulta del animal
	 * @return la instancia actual del animal
	 */
	public Animal setPrimeraConsulta(Date primeraConsulta) {
		this.primeraConsulta = primeraConsulta;
		return this;
	}
	
	/**
	 * Obtiene la foto del animal.
	 * 
	 * @return la foto del animal
	 */
	public byte[] getFoto() {
		return foto;
	}
	
	/**
	 * Establece la foto del animal.
	 * 
	 * @param foto la foto del animal
	 * @return la instancia actual del animal
	 */
	public Animal setFoto(byte[] foto) {
		this.foto = foto;
		return this;
	}	
}
