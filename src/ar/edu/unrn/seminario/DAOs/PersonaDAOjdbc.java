package ar.edu.unrn.seminario.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.excepciones.AppException;
import ar.edu.unrn.seminario.excepciones.DataEmptyException;
import ar.edu.unrn.seminario.excepciones.InvalidStringLengthException;
import ar.edu.unrn.seminario.excepciones.JDBCException;
import ar.edu.unrn.seminario.modelo.Persona;
import ar.edu.unrn.seminario.servicio.DatabaseConfig;

public class PersonaDAOjdbc implements IPersonaDAO {

	private DatabaseConfig config;
	
	public PersonaDAOjdbc(DatabaseConfig config) throws AppException {
		if(config == null)
			throw new AppException("Error de configuración de base de datos");
		this.config = config;
	}
	
	@Override
	public void create(Persona persona) throws JDBCException, DataEmptyException {
		
		Connection conexion = null;
		
		try {
			
			conexion = config.conectarse();
		
			PreparedStatement crearPersona = conexion.prepareStatement("insert into personas (nombre, apellido, dni)"
					+ " values (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
			
			crearPersona.setString(1, persona.getNombre());
			crearPersona.setString(2, persona.getApellido());
			crearPersona.setInt(3, persona.getDni());
			
			crearPersona.executeUpdate();
			
			ResultSet clavePersona = crearPersona.getGeneratedKeys();
			
			clavePersona.next();
			
			Long idPersona = clavePersona.getLong(1);
			
			persona.setId(idPersona);
			
			crearPersona.close();
			
			clavePersona.close();
		
			conexion.close();
			}
		catch (SQLException e) {
			StringBuffer sb = new StringBuffer();
			
			while (e!=null) {
				
				sb.append("SQL Exception: ");
				sb.append(e.getMessage());
				sb.append("Error SQL ANSI-92: ");
				sb.append(e.getSQLState());
				sb.append("Código de error del Proveedor: ");
				sb.append(e.getErrorCode());
				sb.append("\n");
				
				e = e.getNextException();
				
				System.out.println(sb.toString());
			}
			try {
				if(conexion!=null) {
					conexion.rollback();
				}
			}
			catch (SQLException sqlEx) {
				StringBuffer sb2 = new StringBuffer();
				
				while (e!=null) {
					
					sb2.append("SQL Exception: ");
					sb2.append(e.getMessage());
					sb2.append("Error SQL ANSI-92: ");
					sb2.append(e.getSQLState());
					sb2.append("Código de error del Proveedor: ");
					sb2.append(e.getErrorCode());
					sb2.append("\n");
					
					e = e.getNextException();
					
					System.out.println(sb.toString());
				}
			}
			throw new JDBCException("Creación: Error de base de datos");
		  }
		  finally {
			try {
				if(conexion!=null) {
					conexion.close();
				}
			}
			catch(SQLException sqlEx2) {
				throw new JDBCException("Creación: Error de base de datos");
			}
		  }
		
	}

	@Override
	public void update(Persona persona) throws JDBCException, DataEmptyException {
		
		Connection conexion = null;
		
		try {

			conexion = config.conectarse();
			
			PreparedStatement actualizarPersona = conexion.prepareStatement("update personas set nombre = ?, apellido = ?, dni = ? where idpersona = ? ");
			
			actualizarPersona.setString(1, persona.getNombre());
			actualizarPersona.setString(2, persona.getApellido());
			actualizarPersona.setInt(3, persona.getDni());
			actualizarPersona.setLong(4, persona.getId());
			
			actualizarPersona.executeUpdate();
			
			actualizarPersona.close();
			
			conexion.close();
			
		}
		catch (SQLException e) {
			StringBuffer sb = new StringBuffer();
			
			while (e!=null) {
				
				sb.append("SQL Exception: ");
				sb.append(e.getMessage());
				sb.append("Error SQL ANSI-92: ");
				sb.append(e.getSQLState());
				sb.append("Código de error del Proveedor: ");
				sb.append(e.getErrorCode());
				sb.append("\n");
				
				e = e.getNextException();
				
				System.out.println(sb.toString());
			}
			try {
				if(conexion!=null) {
					conexion.rollback();
				}
			}
			catch (SQLException sqlEx) {
				StringBuffer sb2 = new StringBuffer();
				
				while (e!=null) {
					
					sb2.append("SQL Exception: ");
					sb2.append(e.getMessage());
					sb2.append("Error SQL ANSI-92: ");
					sb2.append(e.getSQLState());
					sb2.append("Código de error del Proveedor: ");
					sb2.append(e.getErrorCode());
					sb2.append("\n");
					
					e = e.getNextException();
					
					System.out.println(sb.toString());
				}
			}
			throw new JDBCException("Actualización: Error de base de datos");
		  }
		  finally {
			try {
				if(conexion!=null) {
					conexion.close();
				}
			}
			catch(SQLException sqlEx2) {
				throw new JDBCException("Actualización: Error de base de datos");
			}
		  }
		
	}

	@Override
	public void remove(Long id) throws JDBCException, DataEmptyException {
		
		Connection conexion=null;
		
		try {

			conexion = config.conectarse();
			
			PreparedStatement sentencia = conexion.prepareStatement("update personas set activo = 0 where idpersona = ?");
			
			sentencia.setLong(1, id);
			
			sentencia.executeUpdate();
			
			int resultado = sentencia.executeUpdate();
			
			sentencia.close();
			
			conexion.close();
			
		}
		catch (SQLException e) {
			StringBuffer sb = new StringBuffer();
			
			while (e!=null) {
				
				sb.append("SQL Exception: ");
				sb.append(e.getMessage());
				sb.append("Error SQL ANSI-92: ");
				sb.append(e.getSQLState());
				sb.append("Código de error del Proveedor: ");
				sb.append(e.getErrorCode());
				sb.append("\n");
				
				e = e.getNextException();
				
				System.out.println(sb.toString());
			}
			try {
				if(conexion!=null) {
					conexion.rollback();
				}
			}
			catch (SQLException sqlEx) {
				StringBuffer sb2 = new StringBuffer();
				
				while (e!=null) {
					
					sb2.append("SQL Exception: ");
					sb2.append(e.getMessage());
					sb2.append("Error SQL ANSI-92: ");
					sb2.append(e.getSQLState());
					sb2.append("Código de error del Proveedor: ");
					sb2.append(e.getErrorCode());
					sb2.append("\n");
					
					e = e.getNextException();
					
					System.out.println(sb.toString());
				}
			}
			throw new JDBCException("Borrado: Error de base de datos");
		  }
		  finally {
			try {
				if(conexion!=null) {
					conexion.close();
				}
			}
			catch(SQLException sqlEx2) {
				throw new JDBCException("Borrado: Error de base de datos");
			}
		  }
	}

	@Override
	public void remove(Persona persona) throws JDBCException, DataEmptyException {
		try {
			remove(persona.getId());
		}
		catch (JDBCException e) {
			throw e;
		}
	}

	@Override
	public Persona find(Long id) throws JDBCException, DataEmptyException, InvalidStringLengthException {
		
		Connection conexion = null;
		
		try {

			conexion = config.conectarse();
			
			PreparedStatement sentencia = conexion.prepareStatement("select * from personas where idpersona = ?");
			
			sentencia.setLong(1, id);
			
			ResultSet resultado = sentencia.executeQuery();
			
			Persona persona = null;
			
			if(resultado.next()) {
				
				persona = new Persona(resultado.getString("nombre"), resultado.getString("apellido"), resultado.getInt("dni"));
				persona.setId(resultado.getLong("idpersona"));
				persona.setActivo(resultado.getBoolean("activo"));
			}
			
			return persona;
			
		}
		catch (SQLException e) {
			StringBuffer sb = new StringBuffer();
			
			while (e!=null) {
				
				sb.append("SQL Exception: ");
				sb.append(e.getMessage());
				sb.append("Error SQL ANSI-92: ");
				sb.append(e.getSQLState());
				sb.append("Código de error del Proveedor: ");
				sb.append(e.getErrorCode());
				sb.append("\n");
				
				e = e.getNextException();
				
				System.out.println(sb.toString());
			}
			try {
				if(conexion!=null) {
					conexion.rollback();
				}
			}
			catch (SQLException sqlEx) {
				StringBuffer sb2 = new StringBuffer();
				
				while (e!=null) {
					
					sb2.append("SQL Exception: ");
					sb2.append(e.getMessage());
					sb2.append("Error SQL ANSI-92: ");
					sb2.append(e.getSQLState());
					sb2.append("Código de error del Proveedor: ");
					sb2.append(e.getErrorCode());
					sb2.append("\n");
					
					e = e.getNextException();
					
					System.out.println(sb.toString());
				}
			}
			throw new JDBCException("Búsqueda: Error de base de datos");
		  }
		  finally {
			try {
				if(conexion!=null) {
					conexion.close();
				}
			}
			catch(SQLException sqlEx2) {
				throw new JDBCException("Búsqueda: Error de base de datos");
			}
		  }
		
	}

	@Override
	public List<Persona> findAll() throws JDBCException, DataEmptyException, InvalidStringLengthException {
		
		Connection conexion = null;
		
		try {

			conexion = config.conectarse();
			
			Statement sentencia = conexion.createStatement();
			
			ResultSet resultado = sentencia.executeQuery("select * from personas");
			
			ArrayList<Persona> lista = new ArrayList<Persona>();
			
			while(resultado.next()) {
				
				Persona persona = new Persona(resultado.getString("nombre"), resultado.getString("apellido"), resultado.getInt("dni"));
				persona.setId(resultado.getLong("idpersona"));
				persona.setActivo(resultado.getBoolean("activo"));
				
				lista.add(persona);
			}
			
			return lista;
			
		}
		catch (SQLException e) {
			StringBuffer sb = new StringBuffer();
			
			while (e!=null) {
				
				sb.append("SQL Exception: ");
				sb.append(e.getMessage());
				sb.append("Error SQL ANSI-92: ");
				sb.append(e.getSQLState());
				sb.append("Código de error del Proveedor: ");
				sb.append(e.getErrorCode());
				sb.append("\n");
				
				e = e.getNextException();
				
				System.out.println(sb.toString());
			}
			
			throw new JDBCException("Listado: Error de base de datos");
		}
		finally {
			try {
				if(conexion!=null) {
					conexion.close();
				}
			}
			catch(SQLException sqlEx2) {
				throw new JDBCException("Listado: Error de base de datos");
			}
		}
	}

}
