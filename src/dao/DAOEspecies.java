package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import excepciones.AnimalesException;
import model.Especie;


public class DAOEspecies extends DAOBase{
	
	public static void anadirEspecie(Especie especie) throws AnimalesException, SQLException {
		if (especie != null) {
			String sql = "INSERT INTO especie(nombre) VALUES (?)";
			Connection con = null;
			try {
				con = getConexion();
				con.setAutoCommit(false);
				PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setString(1, especie.getNombre());
				ps.executeUpdate();
				ResultSet claves = ps.getGeneratedKeys();
				if (claves.first()) {					
					especie.setId(claves.getInt("id"));
				}
			} catch (SQLException e) {
				con.rollback();
				throw new AnimalesException(e);
			} finally {
				con.close();
			}
		}
	}
	
	public static void modificarEspecie(Especie especie) throws AnimalesException, SQLException {
		if (especie != null && especie.getId() > 0) {
			String sql = "UPDATE especie SET nombre = ? WHERE id = ?";
			Connection con = null;
			try {
				con = getConexion();
				con.setAutoCommit(false);
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, especie.getNombre());
				ps.setInt(2, especie.getId());
				ps.executeUpdate();
			} catch (SQLException e) {
				con.rollback();
				throw new AnimalesException(e);
			} finally {
				con.close();
			}
		}
	}
	
	public static void borrarEspecie(Especie especie) throws AnimalesException, SQLException {
		if (especie != null && especie.getId() > 0) {
			String sql = "DELETE FROM especie WHERE id = ?";
			Connection con = null;
			try {
				con = getConexion();
				con.setAutoCommit(false);
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, especie.getId());
				ps.executeUpdate();
			} catch (SQLException e) {
				con.rollback();
				throw new AnimalesException(e);
			} finally {
				con.close();
			}
		}
	}
	
	public static List<Especie> getEspecies() throws AnimalesException {
		List<Especie> lista = new LinkedList<>();
		String sql = "SELECT * FROM especie";
		try(Connection con = getConexion()) {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				lista.add(new Especie()
						.setId(rs.getInt("id"))
						.setNombre(rs.getString("nombre")));
			}
			return lista;
		} catch (SQLException e) {
			throw new AnimalesException(e);
		}
	}
	
	
}
