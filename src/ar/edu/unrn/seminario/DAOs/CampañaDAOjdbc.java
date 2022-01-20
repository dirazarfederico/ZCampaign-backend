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
import ar.edu.unrn.seminario.modelo.Campaña;
import ar.edu.unrn.seminario.modelo.Direccion;
import ar.edu.unrn.seminario.modelo.Institucion;
import ar.edu.unrn.seminario.servicio.DatabaseConfig;

public class CampañaDAOjdbc implements ICampañaDAO {
	
	private DatabaseConfig config;
	
	public CampañaDAOjdbc(DatabaseConfig config) throws AppException {
		if(config == null)
			throw new AppException("Error de configuración de base de datos");
		this.config = config;
	}
	
	public void create(Campaña campaña) throws JDBCException, DataEmptyException {
		
		Connection conexion=null;
		
		try {
			
			conexion = config.conectarse();
			
			PreparedStatement sentencia = conexion.prepareStatement("insert into campañas (fecha_inicio, fecha_fin, descripcion, motivo, nombre, idinstitucion)"
					+ " values (?, ?, ?, ?, ?, ?)");
			
			sentencia.setDate(1, new java.sql.Date(campaña.getFechaInicio().getTime()));
			sentencia.setDate(2, new java.sql.Date(campaña.getFechaFin().getTime()));
			sentencia.setString(3, campaña.getDescripcion());
			sentencia.setString(4, campaña.getMotivo());
			sentencia.setString(5, campaña.getNombre());
			sentencia.setLong(6, campaña.getInstitucion().getId());
			
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
	
	public void update(Campaña campaña) throws JDBCException, DataEmptyException{
		
		Connection conexion=null;
		
		try {
			
			conexion = config.conectarse();
			
			PreparedStatement sentencia = conexion.prepareStatement("update campañas set fecha_inicio=?, fecha_fin=?, nombre=?, descripcion=?, motivo=? where idcampaña=?");
			sentencia.setDate(1, new java.sql.Date(campaña.getFechaInicio().getTime()));
			sentencia.setDate(2, new java.sql.Date(campaña.getFechaFin().getTime()));
			sentencia.setString(3, campaña.getNombre());
			sentencia.setString(4, campaña.getDescripcion());
			sentencia.setString(5, campaña.getMotivo());
			sentencia.setLong(6, campaña.getId());
			
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
			
			PreparedStatement sentencia = conexion.prepareStatement("update campañas set activo = 0 where idcampaña=?");
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
	
	public void remove(Campaña campaña) throws JDBCException, DataEmptyException {
		
		try {
			remove(campaña.getId());
		}
		catch (JDBCException e) {
			throw e;
		}
	}
	
	public Campaña find(Long id) throws JDBCException, InvalidStringLengthException, DataEmptyException, InvalidDateRangeException, DateOutOfBoundariesException {
		
		Connection conexion = null;
		
		try {
			
			conexion = config.conectarse();
		 	
			PreparedStatement sentencia = conexion.prepareStatement("Select c.*, i.idinstitucion, i.contacto, i.nombre as inombre, i.cuil, d.* from campañas c join instituciones i on (c.idinstitucion=i.idinstitucion)"
					+ " join direcciones d on (d.iddireccion=i.iddireccion) where idcampaña=?");
		 	sentencia.setLong(1, id);
		 	
		 	ResultSet resultado = sentencia.executeQuery();
		 	
		 	Campaña campaña=null;
		 	
		 	if (resultado.next()) {
				
		 		Direccion direccion = new Direccion(resultado.getString("calle"), resultado.getInt("numero"), resultado.getString("latitud"), resultado.getString("longitud"), resultado.getString("ciudad"));
		 		
		 		Institucion institucion = new Institucion(resultado.getString("inombre"), resultado.getString("cuil"), resultado.getString("contacto"), direccion);
		 		
		 		campaña = new Campaña(resultado.getDate("fecha_inicio"), resultado.getDate("fecha_fin"), resultado.getString("descripcion"), resultado.getString("motivo"), resultado.getString("nombre"), institucion);
				campaña.setId(resultado.getLong("idcampaña"));
				campaña.setActivo(resultado.getBoolean("c.activo"));
				
				campaña.getInstitucion().setId(resultado.getLong("idinstitucion"));
				
			}
		 	
		 	resultado.close();
		 	
		 	sentencia.close();
		 	
		 	conexion.close();
			
		 	return campaña;
		 	
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
	
	
	public List<Campaña> findAll() throws JDBCException, DataEmptyException, InvalidDateRangeException, DateOutOfBoundariesException, InvalidStringLengthException {
		
		Connection conexion = null;
		
		try {
			
			conexion = config.conectarse();
			
			Statement sentencia = conexion.createStatement();
			
			ResultSet resultado = sentencia.executeQuery("Select c.*, i.idinstitucion, i.contacto, i.nombre as inombre, i.cuil, d.* from campañas c join instituciones i on (c.idinstitucion=i.idinstitucion)"
					+ " join direcciones d on (d.iddireccion=i.iddireccion)");
			
			ArrayList<Campaña> lista = new ArrayList<Campaña>();
			Campaña campaña=null;
			
			while(resultado.next()) {
				
				Direccion direccion = 
						new Direccion(resultado.getString("calle"), resultado.getInt("numero"), resultado.getString("latitud"), resultado.getString("longitud"), resultado.getString("ciudad"));
						
				Institucion institucion = new Institucion(resultado.getString("inombre"), resultado.getString("cuil"), resultado.getString("contacto"), direccion);
				
				campaña = new Campaña(resultado.getDate("fecha_inicio"), resultado.getDate("fecha_fin"), resultado.getString("descripcion"), resultado.getString("motivo"), resultado.getString("nombre"), institucion);
				campaña.setId(resultado.getLong("idcampaña"));
				campaña.setActivo(resultado.getBoolean("c.activo"));
				
				campaña.getInstitucion().setId(resultado.getLong("idinstitucion"));
				lista.add(campaña);
				
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