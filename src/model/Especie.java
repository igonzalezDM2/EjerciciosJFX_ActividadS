package model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Clase que representa una especie de animal.
 */
public class Especie implements Serializable {
	
	private static final long serialVersionUID = -6882186734882593976L;
	
	private int id;
	private String nombre;
	
	/**
	 * Obtiene el ID de la especie.
	 * 
	 * @return el ID de la especie
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Establece el ID de la especie.
	 * 
	 * @param id el ID de la especie
	 * @return la instancia actual de la especie
	 */
	public Especie setId(int id) {
		this.id = id;
		return this;
	}
	
	/**
	 * Obtiene el nombre de la especie.
	 * 
	 * @return el nombre de la especie
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Establece el nombre de la especie.
	 * 
	 * @param nombre el nombre de la especie
	 * @return la instancia actual de la especie
	 */
	public Especie setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, nombre);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Especie other = (Especie) obj;
		return id == other.id;
	}
	
	@Override
	public String toString() {
		return nombre;
	}
	
}
