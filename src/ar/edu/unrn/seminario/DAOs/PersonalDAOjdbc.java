package ar.edu.unrn.seminario.DAOs;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.excepciones.AppException;
import ar.edu.unrn.seminario.excepciones.JDBCException;
import ar.edu.unrn.seminario.modelo.*;
import ar.edu.unrn.seminario.servicio.DatabaseConfig;

import java.sql.*;

public class PersonalDAOjdbc implements IPersonalDAO {

	private DatabaseConfig config;
	
	public PersonalDAOjdbc(DatabaseConfig config) throws AppException {
		if(config == null)
			throw new AppException("Error de configuración de base de datos");
		this.config = config;
	}
	
	@Override
	public void create(Personal personal) throws Exception {
		Connection conexion = null;
		
		try {
			
			conexion = config.conectarse();
			
			PreparedStatement crearPersonal = conexion.prepareStatement("insert into personales (email, idpersona, idinstitucion, foto)"
					+ " values (?, ?, ?, ?)");
			
			crearPersonal.setString(1, personal.getEmail());
			crearPersonal.setLong(2, personal.getPersona().getId());
			crearPersonal.setLong(3, personal.getInstitucion().getId());
			crearPersonal.setString(4, personal.getFoto());
			
			crearPersonal.executeUpdate();
			
			crearPersonal.close();
			
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
	public void update(Personal personal) throws Exception {
		Connection conexion = null;
		
		try {

			conexion = config.conectarse();
			
			PreparedStatement actualizarPersonal = conexion.prepareStatement("update personales set email = ?, idinstitucion = ?, foto = ? where idpersonal = ?");
			
			actualizarPersonal.setString(1, personal.getEmail());
			actualizarPersonal.setLong(2, personal.getInstitucion().getId());
			actualizarPersonal.setString(3, personal.getFoto());
			actualizarPersonal.setLong(4, personal.getId());
			
			actualizarPersonal.executeUpdate();
			
			actualizarPersonal.close();
			
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
	public void remove(Long id) throws Exception {
		Connection conexion=null;
		
		try {

			conexion = config.conectarse();
			
			PreparedStatement sentencia = conexion.prepareStatement("update personales set activo = 0 where idpersonal=?");
			sentencia.setLong(1, id);
			
			int resultado = sentencia.executeUpdate();
			
			sentencia.close();
			
			conexion.close();
			
		} catch (SQLException e) {
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
	public void remove(Personal personal) throws Exception {
		try {
			remove(personal.getId());
		}
		catch (JDBCException e) {
			throw e;
		}
		
	}

	@Override
	public Personal find(Long id) throws Exception {
		Connection conexion=null;
		
		try {

			conexion = config.conectarse();
			
			PreparedStatement sentencia = conexion.prepareStatement("select per.*, p.idpersona, p.nombre as nombrePersona, p.apellido, p.dni, i.* " 
					+ "from personales per join personas p on (per.idpersona=p.idpersona) join instituciones i on (per.idinstitucion=i.idinstitucion) where idpersonal = ?");
			
			sentencia.setLong(1, id);
			
			ResultSet resultado = sentencia.executeQuery();
			
			Personal personal = null;
			
			if(resultado.next()) {
				Persona persona = new Persona(resultado.getString("nombrePersona"), resultado.getString("apellido"), resultado.getInt("dni"));
				persona.setId(resultado.getLong("idpersona"));
				
				Institucion institucion = new Institucion(resultado.getString("nombre"));
				institucion.setId(resultado.getLong("idinstitucion"));
				
				personal = new Personal(resultado.getString("foto"), resultado.getString("email"), persona, institucion);
				personal.setId(resultado.getLong("idpersonal"));
				personal.setActivo(resultado.getBoolean("per.activo"));
			
			}
			
			sentencia.close();
			
			conexion.close();
			
			return personal;
			
		} catch (SQLException e) {
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
	public List<Personal> findAll() throws Exception {
			Connection conexion = null;
		
		try {

			conexion = config.conectarse();
			
			Statement sentencia = conexion.createStatement();
			
			ResultSet resultado = sentencia.executeQuery("select per.*, p.idpersona, p.nombre as nombrePersona, p.apellido, p.dni, i.* from personales per join personas p on (per.idpersona=p.idpersona)"
					+ " join instituciones i on (per.idinstitucion=i.idinstitucion)");
			
			ArrayList<Personal> lista = new ArrayList<Personal>();
			
			while (resultado.next()) {
				
				Persona persona = new Persona(resultado.getString("nombrePersona"), resultado.getString("apellido"), resultado.getInt("dni"));
				persona.setId(resultado.getLong("idpersona"));
				
				Institucion institucion = new Institucion(resultado.getString("nombre"));
				institucion.setId(resultado.getLong("idinstitucion"));
				
				Personal personal = new Personal(resultado.getString("foto"), resultado.getString("email"), persona, institucion);
				personal.setId(resultado.getLong("idpersonal"));
				personal.setActivo(resultado.getBoolean("per.activo"));
				
				lista.add(personal);

			}
			
			resultado.close();
			
			sentencia.close();
			
			conexion.close();
			
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
