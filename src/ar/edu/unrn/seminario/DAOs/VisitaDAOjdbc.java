package ar.edu.unrn.seminario.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.excepciones.AppException;
import ar.edu.unrn.seminario.excepciones.JDBCException;
import ar.edu.unrn.seminario.modelo.OrdenDeRetiro;
import ar.edu.unrn.seminario.modelo.Visita;
import ar.edu.unrn.seminario.servicio.DatabaseConfig;

public class VisitaDAOjdbc implements IVisitaDAO {

	private OrdenDeRetiroDAOjdbc ordenDAO;
	
	private DatabaseConfig config;
	
	public VisitaDAOjdbc(DatabaseConfig config) throws AppException {
		if(config == null)
			throw new AppException("Error de configuración de base de datos");
		ordenDAO = new OrdenDeRetiroDAOjdbc(config);
		this.config = config;
	}
	
	@Override
	public void create(Visita visita) throws Exception {
		
		Connection conexion = null;
		
		try {

			conexion = config.conectarse();
			
			PreparedStatement crearVisita = conexion.prepareStatement("insert into visitas (fecha, observacion, idorden)" +
			" values (?, ?, ?)");
			
			crearVisita.setTimestamp(1, new java.sql.Timestamp(visita.getFechaHora().getTime()));
			crearVisita.setString(2, visita.getObservacion());
			crearVisita.setLong(3, visita.getOrden().getId());
			
			crearVisita.executeUpdate();
			
			crearVisita.close();
			
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
	public void update(Visita visita) throws Exception {
		
		Connection conexion = null;
		
		try {

			conexion = config.conectarse();
			
			PreparedStatement sentencia = conexion.prepareStatement("update visitas set fecha = ?, observacion = ? where idvisita = ?");
			
			sentencia.setTimestamp(1, new java.sql.Timestamp(visita.getFechaHora().getTime()));
			sentencia.setString(2, visita.getObservacion());
			sentencia.setLong(3, visita.getId());
			
			sentencia.executeUpdate();
			
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
		Connection conexion = null;
		
		try {

			conexion = config.conectarse();
			
			PreparedStatement borrarVisita = conexion.prepareStatement("update visitas set activo = 0 where idvisita = ?");
			borrarVisita.setLong(1, id);
			
			borrarVisita.executeUpdate();
			
			borrarVisita.close();
			
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
	public void remove(Visita visita) throws Exception {
		
		try {
			remove(visita.getId());
		}
		catch(JDBCException e) {
			throw e;
		}
		
	}

	@Override
	public Visita find(Long id) throws Exception {

		Connection conexion = null;
		
		try {

			conexion = config.conectarse();
			
			PreparedStatement sentencia = conexion.prepareStatement("select * from visitas where idvisita = ?");
			sentencia.setLong(1, id);
			
			ResultSet resultado = sentencia.executeQuery();
			
			Visita visita = null;
			
			if(resultado.next()) {
				
				OrdenDeRetiro orden = ordenDAO.find(resultado.getLong("idorden"));
				
				visita = new Visita(resultado.getString("observacion"), resultado.getTimestamp("fecha"), orden);
				visita.setId(resultado.getLong("idvisita"));
				visita.setActivo(resultado.getBoolean("activo"));
				
			}
			
			resultado.close();
			
			sentencia.close();
			
			conexion.close();
			
			return visita;
			
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
	public List<Visita> findAll() throws Exception {

		Connection conexion = null;
		
		try {

			conexion = config.conectarse();
			
			Statement sentencia = conexion.createStatement();
			
			ResultSet resultado = sentencia.executeQuery("select * from visitas");
			
			List<Visita> lista = new ArrayList<Visita>();
			
			while(resultado.next()) {
				
				OrdenDeRetiro orden = ordenDAO.find(resultado.getLong("idorden"));
				
				Visita visita = new Visita(resultado.getString("observacion"), resultado.getTimestamp("fecha"), orden);
				visita.setId(resultado.getLong("idvisita"));
				visita.setActivo(resultado.getBoolean("activo"));
				
				lista.add(visita);
				
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
