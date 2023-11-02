package model;

import java.io.Serializable;
import java.util.Objects;

public class Especie implements Serializable {
	
	private static final long serialVersionUID = -6882186734882593976L;
	
	private int id;
	private String nombre;
	public int getId() {
		return id;
	}
	public Especie setId(int id) {
		this.id = id;
		return this;
	}
	public String getNombre() {
		return nombre;
	}
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
