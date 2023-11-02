package model;

import java.io.InputStream;
import java.util.Date;

import enums.Sexo;

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
	private InputStream foto;
	
	public String getCodigo() {
		return codigo;
	}
	public Animal setCodigo(String codigo) {
		this.codigo = codigo;
		return this;
	}
	public String getNombre() {
		return nombre;
	}
	public Animal setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}
	public Especie getEspecie() {
		return especie;
	}
	public Animal setEspecie(Especie especie) {
		this.especie = especie;
		return this;
	}
	public String getRaza() {
		return raza;
	}
	public Animal setRaza(String raza) {
		this.raza = raza;
		return this;
	}
	public Sexo getSexo() {
		return sexo;
	}
	public Animal setSexo(Sexo sexo) {
		this.sexo = sexo;
		return this;
	}
	public Integer getEdad() {
		return edad;
	}
	public Animal setEdad(Integer edad) {
		this.edad = edad;
		return this;
	}
	public Double getPeso() {
		return peso;
	}
	public Animal setPeso(Double peso) {
		this.peso = peso;
		return this;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public Animal setObservaciones(String observaciones) {
		this.observaciones = observaciones;
		return this;
	}
	public Date getPrimeraConsulta() {
		return primeraConsulta;
	}
	public Animal setPrimeraConsulta(Date primeraConsulta) {
		this.primeraConsulta = primeraConsulta;
		return this;
	}
	public InputStream getFoto() {
		return foto;
	}
	public Animal setFoto(InputStream foto) {
		this.foto = foto;
		return this;
	}	
	
}
