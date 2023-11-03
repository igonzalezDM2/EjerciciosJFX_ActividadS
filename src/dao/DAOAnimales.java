package dao;

import static utilities.Utilidades.sqlDate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import excepciones.AnimalesException;
import model.Animal;
import utilities.Utilidades;


public class DAOAnimales extends DAOBase{
	
	public static List<Animal> getAnimales(String busqueda) throws AnimalesException {
		try(Connection con = getConexion()) {
			StringBuilder sb = new StringBuilder("SELECT "
					+ "animal.codigo AS codigo, "
					+ "animal.nombre AS nombre_animal, "
					+ "animal.especie AS id_especie, "
					+ "animal.raza AS raza, "
					+ "animal.sexo AS sexo, "
					+ "animal.edad AS edad, "
					+ "animal.peso AS peso, "
					+ "animal.observaciones AS observaciones, "
					+ "animal.primera_consulta AS primera_consulta, "
					+ "animal.foto AS foto, "
					+ "especie.nombre AS nombre_especie "
					+ "FROM animal\n"
					+ "INNER JOIN especie ON especie.id = animal.especie");
			
			if (busqueda != null && !busqueda.isBlank()) {
				sb.append("\n"
						+ "WHERE LOWER(codigo) LIKE '%" + busqueda.toLowerCase() + "%' "
						+ "OR LOWER(animal.nombre) LIKE '%" + busqueda.toLowerCase() + "%' "
						+ "OR LOWER(raza) LIKE '%" + busqueda.toLowerCase() + "%' "
						+ "OR LOWER(especie.nombre) LIKE '%" + busqueda.toLowerCase() + "%'");
			}
			
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sb.toString());
			
			List<Animal> animales = new LinkedList<>(); 
			
			while(rs.next()) {
				animales.add(Utilidades.mapAnimal(rs));
			}
			
			return animales;
			
		} catch (SQLException e) {
			throw new AnimalesException(e);
		}
		
	}
	
	public static List<Animal> getAnimales() throws AnimalesException {
		return getAnimales(null);
	}
	
	public static void anadirAnimal(Animal animal) throws AnimalesException, SQLException, IOException {
		if (animal != null && animal.getEspecie() != null && animal.getEspecie().getId() > 0) {
			
			String sql = "INSERT INTO animal ("
					+ "codigo, "
					+ "nombre, "
					+ "especie, "
					+ "raza, "
					+ "sexo, "
					+ "edad, "
					+ "peso, "
					+ "observaciones, "
					+ "primera_consulta, "
					+ "foto) "
					+ "VALUES (?,?,?,?,?,?,?,?,?,?)";
			
			Connection con = null;
			try {
				con = getConexion();
				con.setAutoCommit(false);
				
				try (PreparedStatement ps = con.prepareStatement(sql)) {
					ps.setString(1, animal.getCodigo());
					ps.setString(2, animal.getNombre());
					ps.setInt(3, animal.getEspecie().getId());
					ps.setString(4, animal.getRaza());
					ps.setString(5, animal.getSexo() != null ? animal.getSexo().getValor() : null);
					ps.setInt(6, animal.getEdad());
					ps.setDouble(7, animal.getPeso());
					ps.setString(8, animal.getObservaciones());
					ps.setDate(9, sqlDate(animal.getPrimeraConsulta()));
					if (animal.getFoto() != null) {						
						try (InputStream is = new ByteArrayInputStream(animal.getFoto())) {						
							ps.setBinaryStream(10, is);
						}
					} else {
						ps.setBinaryStream(10, null);
					}
					
					ps.executeUpdate();
				}
				con.commit();
			} catch (SQLException e) {
				e.printStackTrace();
				con.rollback();
				throw new AnimalesException(e);
			} finally {
				con.close();
			}			
		} else {			
			throw new AnimalesException("Los datos introducidos están incompletos");
		}
	}
	
	public static void modificarAnimal(Animal animal) throws AnimalesException, SQLException, IOException {
		if (animal != null && animal.getEspecie() != null && animal.getEspecie().getId() > 0) {
			
			String sql = "UPDATE animal SET "
					+ "nombre = ?, "
					+ "especie = ?, "
					+ "raza = ?, "
					+ "sexo = ?, "
					+ "edad = ?, "
					+ "peso = ?, "
					+ "observaciones = ?, "
					+ "primera_consulta = ?, "
					+ "foto = ? "
					+ "WHERE CODIGO = ?";
			
			Connection con = null;
			try {
				con = getConexion();
				con.setAutoCommit(false);
				
				try (PreparedStatement ps = con.prepareStatement(sql)) {
					ps.setString(1, animal.getNombre());
					ps.setInt(2, animal.getEspecie().getId());
					ps.setString(3, animal.getRaza());
					ps.setString(4, animal.getSexo() != null ? animal.getSexo().getValor() : null);
					ps.setInt(5, animal.getEdad());
					ps.setDouble(6, animal.getPeso());
					ps.setString(7, animal.getObservaciones());
					ps.setDate(8, sqlDate(animal.getPrimeraConsulta()));
					if (animal.getFoto() != null) {						
						try (InputStream is = new ByteArrayInputStream(animal.getFoto())) {						
							ps.setBinaryStream(9, is);
						}
					} else {
						ps.setBinaryStream(9, null);
					}
					ps.setString(10, animal.getCodigo());
					
					ps.executeUpdate();
				}
				con.commit();
			} catch (SQLException e) {
				e.printStackTrace();
				con.rollback();
				throw new AnimalesException(e);
			} finally {
				con.close();
			}			
		} else {			
			throw new AnimalesException("Los datos introducidos están incompletos");
		}
	}
	
	public static void borrarAnimal(Animal animal) throws SQLException, AnimalesException {
		if (animal == null || animal.getCodigo() == null || animal.getCodigo().isBlank()) {
			return;
		}
		String sql = "DELETE FROM animal WHERE codigo = ?";
		Connection con = null;
		try {
			con = getConexion();
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, animal.getCodigo());
			ps.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			throw new AnimalesException(e);
		} finally {
			con.close();
		}
		
	}
	
	
}
