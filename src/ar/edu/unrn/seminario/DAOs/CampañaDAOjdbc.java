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
import ar.edu.unrn.seminario.excepciones.DateOutOfBoundariesException;
import ar.edu.unrn.seminario.excepciones.InvalidDateRangeException;
import ar.edu.unrn.seminario.excepciones.InvalidStringLengthException;
import ar.edu.unrn.seminario.excepciones.JDBCException;
import ar.edu.unrn.seminario.modelo.Campa�a;
import ar.edu.unrn.seminario.modelo.Direccion;
import ar.edu.unrn.seminario.modelo.Institucion;
import ar.edu.unrn.seminario.servicio.DatabaseConfig;

public class Campa�aDAOjdbc implements ICampa�aDAO {
	
	private DatabaseConfig config;
	
	public Campa�aDAOjdbc(DatabaseConfig config) throws AppException {
		if(config == null)
			throw new AppException("Error de configuraci�n de base de datos");
		this.config = config;
	}
	
	public void create(Campa�a campa�a) throws JDBCException, DataEmptyException {
		
		Connection conexion=null;
		
		try {
			
			conexion = config.conectarse();
			
			PreparedStatement sentencia = conexion.prepareStatement("insert into campa�as (fecha_inicio, fecha_fin, descripcion, motivo, nombre, idinstitucion)"
					+ " values (?, ?, ?, ?, ?, ?)");
			
			sentencia.setDate(1, new java.sql.Date(campa�a.getFechaInicio().getTime()));
			sentencia.setDate(2, new java.sql.Date(campa�a.getFechaFin().getTime()));
			sentencia.setString(3, campa�a.getDescripcion());
			sentencia.setString(4, campa�a.getMotivo());
			sentencia.setString(5, campa�a.getNombre());
			sentencia.setLong(6, campa�a.getInstitucion().getId());
			
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
				sb.append("C�digo de error del Proveedor: ");
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
					sb2.append("C�digo de error del Proveedor: ");
					sb2.append(e.getErrorCode());
					sb2.append("\n");
					
					e = e.getNextException();
					
					System.out.println(sb.toString());
				}
			}
			throw new JDBCException("Creaci�n: Error de base de datos");
		  }
		  finally {
			try {
				if(conexion!=null) {
					conexion.close();
				}
			}
			catch(SQLException sqlEx2) {
				throw new JDBCException("Creaci�n: Error de base de datos");
			}
		  }
	}
	
	public void update(Campa�a campa�a) throws JDBCException, DataEmptyException{
		
		Connection conexion=null;
		
		try {
			
			conexion = config.conectarse();
			
			PreparedStatement sentencia = conexion.prepareStatement("update campa�as set fecha_inicio=?, fecha_fin=?, nombre=?, descripcion=?, motivo=? where idcampa�a=?");
			sentencia.setDate(1, new java.sql.Date(campa�a.getFechaInicio().getTime()));
			sentencia.setDate(2, new java.sql.Date(campa�a.getFechaFin().getTime()));
			sentencia.setString(3, campa�a.getNombre());
			sentencia.setString(4, campa�a.getDescripcion());
			sentencia.setString(5, campa�a.getMotivo());
			sentencia.setLong(6, campa�a.getId());
			
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
				sb.append("C�digo de error del Proveedor: ");
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
					sb2.append("C�digo de error del Proveedor: ");
					sb2.append(e.getErrorCode());
					sb2.append("\n");
					
					e = e.getNextException();
					
					System.out.println(sb.toString());
				}
			}
			throw new JDBCException("Actualizaci�n: Error de base de datos");
		  }
		  finally {
			try {
				if(conexion!=null) {
					conexion.close();
				}
			}
			catch(SQLException sqlEx2) {
				throw new JDBCException("Actualizaci�n: Error de base de datos");
			}
		  }
		
	}
	
	public void remove(Long id) throws JDBCException, DataEmptyException {
		
		Connection conexion=null;
		
		try {
			
			conexion = config.conectarse();
			
			PreparedStatement sentencia = conexion.prepareStatement("update campa�as set activo = 0 where idcampa�a=?");
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
				sb.append("C�digo de error del Proveedor: ");
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
					sb2.append("C�digo de error del Proveedor: ");
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
	
	public void remove(Campa�a campa�a) throws JDBCException, DataEmptyException {
		
		try {
			remove(campa�a.getId());
		}
		catch (JDBCException e) {
			throw e;
		}
	}
	
	public Campa�a find(Long id) throws JDBCException, InvalidStringLengthException, DataEmptyException, InvalidDateRangeException, DateOutOfBoundariesException {
		
		Connection conexion = null;
		
		try {
			
			conexion = config.conectarse();
		 	
			PreparedStatement sentencia = conexion.prepareStatement("Select c.*, i.idinstitucion, i.contacto, i.nombre as inombre, i.cuil, d.* from campa�as c join instituciones i on (c.idinstitucion=i.idinstitucion)"
					+ " join direcciones d on (d.iddireccion=i.iddireccion) where idcampa�a=?");
		 	sentencia.setLong(1, id);
		 	
		 	ResultSet resultado = sentencia.executeQuery();
		 	
		 	Campa�a campa�a=null;
		 	
		 	if (resultado.next()) {
				
		 		Direccion direccion = new Direccion(resultado.getString("calle"), resultado.getInt("numero"), resultado.getString("latitud"), resultado.getString("longitud"), resultado.getString("ciudad"));
		 		
		 		Institucion institucion = new Institucion(resultado.getString("inombre"), resultado.getString("cuil"), resultado.getString("contacto"), direccion);
		 		
		 		campa�a = new Campa�a(resultado.getDate("fecha_inicio"), resultado.getDate("fecha_fin"), resultado.getString("descripcion"), resultado.getString("motivo"), resultado.getString("nombre"), institucion);
				campa�a.setId(resultado.getLong("idcampa�a"));
				campa�a.setActivo(resultado.getBoolean("c.activo"));
				
				campa�a.getInstitucion().setId(resultado.getLong("idinstitucion"));
				
			}
		 	
		 	resultado.close();
		 	
		 	sentencia.close();
		 	
		 	conexion.close();
			
		 	return campa�a;
		 	
		}
		catch (SQLException e) {
			StringBuffer sb = new StringBuffer();
			
			while (e!=null) {
				
				sb.append("SQL Exception: ");
				sb.append(e.getMessage());
				sb.append("Error SQL ANSI-92: ");
				sb.append(e.getSQLState());
				sb.append("C�digo de error del Proveedor: ");
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
					sb2.append("C�digo de error del Proveedor: ");
					sb2.append(e.getErrorCode());
					sb2.append("\n");
					
					e = e.getNextException();
					
					System.out.println(sb.toString());
				}
			}
			throw new JDBCException("B�squeda: Error de base de datos");
		  }
		  finally {
			try {
				if(conexion!=null) {
					conexion.close();
				}
			}
			catch(SQLException sqlEx2) {
				throw new JDBCException("B�squeda: Error de base de datos");
			}
		  }
	}
	
	
	public List<Campa�a> findAll() throws JDBCException, DataEmptyException, InvalidDateRangeException, DateOutOfBoundariesException, InvalidStringLengthException {
		
		Connection conexion = null;
		
		try {
			
			conexion = config.conectarse();
			
			Statement sentencia = conexion.createStatement();
			
			ResultSet resultado = sentencia.executeQuery("Select c.*, i.idinstitucion, i.contacto, i.nombre as inombre, i.cuil, d.* from campa�as c join instituciones i on (c.idinstitucion=i.idinstitucion)"
					+ " join direcciones d on (d.iddireccion=i.iddireccion)");
			
			ArrayList<Campa�a> lista = new ArrayList<Campa�a>();
			Campa�a campa�a=null;
			
			while(resultado.next()) {
				
				Direccion direccion = 
						new Direccion(resultado.getString("calle"), resultado.getInt("numero"), resultado.getString("latitud"), resultado.getString("longitud"), resultado.getString("ciudad"));
						
				Institucion institucion = new Institucion(resultado.getString("inombre"), resultado.getString("cuil"), resultado.getString("contacto"), direccion);
				
				campa�a = new Campa�a(resultado.getDate("fecha_inicio"), resultado.getDate("fecha_fin"), resultado.getString("descripcion"), resultado.getString("motivo"), resultado.getString("nombre"), institucion);
				campa�a.setId(resultado.getLong("idcampa�a"));
				campa�a.setActivo(resultado.getBoolean("c.activo"));
				
				campa�a.getInstitucion().setId(resultado.getLong("idinstitucion"));
				lista.add(campa�a);
				
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
				sb.append("C�digo de error del Proveedor: ");
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
					sb2.append("C�digo de error del Proveedor: ");
					sb2.append(e.getErrorCode());
					sb2.append("\n");
					
					e = e.getNextException();
					
					System.out.println(sb.toString());
				}
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