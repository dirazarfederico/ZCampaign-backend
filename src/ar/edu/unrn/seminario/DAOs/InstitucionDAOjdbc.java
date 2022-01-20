package ar.edu.unrn.seminario.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.modelo.*;
import ar.edu.unrn.seminario.servicio.DatabaseConfig;
import ar.edu.unrn.seminario.excepciones.*;

public class InstitucionDAOjdbc implements IInstitucionDAO {
	
	private DatabaseConfig config;
	
	public InstitucionDAOjdbc(DatabaseConfig config) throws AppException {
		if(config == null)
			throw new AppException("Error de configuración de base de datos");
		this.config = config;
	}
	
	public void create(Institucion institucion) throws JDBCException, DataEmptyException {
		
		Connection conexion=null;
		
		try {
			
			conexion = config.conectarse();
			
			conexion.setAutoCommit(false);
			
			PreparedStatement crearDireccion = conexion.prepareStatement("insert into direcciones (calle, numero, latitud, longitud, ciudad)"
					+ "values (?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
			
			crearDireccion.setString(1, institucion.getDireccion().getCalle());
			crearDireccion.setInt(2, institucion.getDireccion().getNumero());
			crearDireccion.setString(3, institucion.getDireccion().getLatitud());
			crearDireccion.setString(4, institucion.getDireccion().getLongitud());
			crearDireccion.setString(5, institucion.getDireccion().getCiudad());
			
			crearDireccion.executeUpdate();
			
			ResultSet direccion = crearDireccion.getGeneratedKeys();
			
			direccion.next();
			
			PreparedStatement sentencia = conexion.prepareStatement("insert into instituciones (nombre, cuil, contacto, iddireccion)"
					+ "values (?, ?, ?, ?)",PreparedStatement.RETURN_GENERATED_KEYS);
			
			sentencia.setString(1, institucion.getNombre());
			sentencia.setString(2, institucion.getCuil());
			sentencia.setString(3, institucion.getContacto());
			sentencia.setLong(4, direccion.getLong(1));
			
			int resultado = sentencia.executeUpdate();
			
			conexion.commit();
			
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

	public void update(Institucion institucion) throws JDBCException, DataEmptyException {

		Connection conexion=null;
		
		try {
			
			conexion = config.conectarse();
			
			conexion.setAutoCommit(false);
			
			PreparedStatement actualizarDireccion = conexion.prepareStatement("update direcciones set calle=?, numero=?, latitud=?, longitud=?, ciudad=? where iddireccion=?");
			
			actualizarDireccion.setString(1, institucion.getDireccion().getCalle());
			actualizarDireccion.setInt(2, institucion.getDireccion().getNumero());
			actualizarDireccion.setString(3, institucion.getDireccion().getLatitud());
			actualizarDireccion.setString(4, institucion.getDireccion().getLongitud());
			actualizarDireccion.setString(5, institucion.getDireccion().getCiudad());
			actualizarDireccion.setLong(6, institucion.getDireccion().getId());
			
			PreparedStatement sentencia = conexion.prepareStatement("update instituciones set nombre=?, cuil=?, contacto=? where idinstitucion=?");
			
			sentencia.setString(1, institucion.getNombre());
			sentencia.setString(2, institucion.getCuil());
			sentencia.setString(3, institucion.getContacto());
			sentencia.setLong(4, institucion.getId());
			
			int okInstitucion, okDireccion;
			
			okDireccion = actualizarDireccion.executeUpdate();
			okInstitucion = sentencia.executeUpdate();
			
			conexion.commit();
			
			actualizarDireccion.close();
			
			sentencia.close();
			
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
	
	public void remove(Long id) throws JDBCException, DataEmptyException {
		
		Connection conexion=null;
		
		try {

			conexion = config.conectarse();
			
			PreparedStatement sentencia = conexion.prepareStatement("update instituciones set activo = 0 where idinstitucion=?");
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

	public void remove(Institucion institucion) throws JDBCException, DataEmptyException {
		try {
			remove(institucion.getId());
		}
		catch (JDBCException e) {
			throw e;
		}
	}
	
	public Institucion find(Long id) throws JDBCException, DataEmptyException, InvalidStringLengthException {
		
		Connection conexion = null;
		
		try {
			
			conexion = config.conectarse();
			
			PreparedStatement sentencia = conexion.prepareStatement("select i.*, d.calle, d.numero, d.latitud, d.longitud, d.ciudad from instituciones i join direcciones d on (i.iddireccion=d.iddireccion)"
					+ "where idinstitucion=?");
			
			sentencia.setLong(1, id);
			
			ResultSet resultado = sentencia.executeQuery();
			
			Institucion institucion=null;
			
			if(resultado.next()) {
				Direccion direccion = new Direccion(resultado.getString("calle"), resultado.getInt("numero"), resultado.getString("latitud"), resultado.getString("longitud"), resultado.getString("ciudad"));
				direccion.setId(resultado.getLong("iddireccion"));
				
				institucion = new Institucion(resultado.getString("nombre"), resultado.getString("cuil"), resultado.getString("contacto"), direccion);
			
				institucion.setId(resultado.getLong("idinstitucion"));
				institucion.setActivo(resultado.getBoolean("i.activo"));
			}
			
			resultado.close();
			
			sentencia.close();
			
			conexion.close();
			
			return institucion;
			
		}
		catch (DataEmptyException dEEx) {
			throw dEEx;
		}
		catch(InvalidStringLengthException iSLEx) {
			throw iSLEx;
		}
		catch(SQLException e) {
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
	
	public List<Institucion> findAll() throws JDBCException, InvalidStringLengthException, DataEmptyException {
		
		Connection conexion = null;
		
		try {

			conexion = config.conectarse();
			
			Statement sentencia = conexion.createStatement();
			
			ResultSet resultado = sentencia.executeQuery("select i.*, d.calle, d.numero, d.latitud, d.longitud, d.ciudad, d.iddireccion from instituciones i join direcciones d on (i.iddireccion=d.iddireccion)");
			
			ArrayList<Institucion> lista = new ArrayList<Institucion>();
			
			while (resultado.next()) {
				
				Direccion direccion = new Direccion(resultado.getString("calle"), resultado.getInt("numero"), resultado.getString("latitud"), resultado.getString("longitud"), resultado.getString("ciudad"));
				direccion.setId(resultado.getLong("iddireccion"));
				

				Institucion institucion = new Institucion(resultado.getString("nombre"), resultado.getString("cuil"), resultado.getString("contacto"), direccion);
				
				institucion.setId(resultado.getLong("idinstitucion"));
				institucion.setActivo(resultado.getBoolean("i.activo"));
				
				lista.add(institucion);
				
			}

			resultado.close();
			
			sentencia.close();
			
			conexion.close();
			
			return lista;
		}
		catch(InvalidStringLengthException iSLEx) {
			throw iSLEx;
		}
		catch(DataEmptyException e) {
			throw e;
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
