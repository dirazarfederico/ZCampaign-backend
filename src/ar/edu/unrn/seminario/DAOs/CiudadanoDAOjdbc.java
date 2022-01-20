package ar.edu.unrn.seminario.DAOs;

import java.sql.*;

import ar.edu.unrn.seminario.excepciones.*;
import ar.edu.unrn.seminario.modelo.*;
import ar.edu.unrn.seminario.servicio.DatabaseConfig;

import java.util.*;

public class CiudadanoDAOjdbc implements ICiudadanoDAO {
	
	private DatabaseConfig config;
	
	public CiudadanoDAOjdbc(DatabaseConfig config) throws AppException {
		if(config == null)
			throw new AppException("Error de configuración de base de datos");
		this.config = config;
	}
	
	@Override
	public void create(Ciudadano ciudadano) throws JDBCException, DataEmptyException {
		
		Connection conexion=null;
		
		try {
			
			conexion = config.conectarse();
			
			conexion.setAutoCommit(false);
			
			PreparedStatement crearDireccion = conexion.prepareStatement("insert into direcciones (calle, numero, latitud, longitud, ciudad)"
					+ "values (?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
			
			crearDireccion.setString(1, ciudadano.getDireccion().getCalle());
			crearDireccion.setInt(2, ciudadano.getDireccion().getNumero());
			crearDireccion.setString(3, ciudadano.getDireccion().getLatitud());
			crearDireccion.setString(4, ciudadano.getDireccion().getLongitud());
			crearDireccion.setString(5, ciudadano.getDireccion().getCiudad());
			
			crearDireccion.executeUpdate();
			
			ResultSet claveDireccion = crearDireccion.getGeneratedKeys();
			
			PreparedStatement crearCiudadano = conexion.prepareStatement("insert into ciudadanos (idpersona, iddireccion)"
					+ "values (?, ?)");
			
			claveDireccion.next();
			
			Long idDireccion=claveDireccion.getLong(1);
			Long idPersona=ciudadano.getPersona().getId();
	
			crearCiudadano.setLong(1, idPersona);
			
			crearCiudadano.setLong(2, idDireccion);
	
			crearCiudadano.execute();
			
			conexion.commit();
			
			claveDireccion.close();
			
			crearDireccion.close();
			
			crearCiudadano.close();
			
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

	@Override
	public void update(Ciudadano ciudadano) throws JDBCException, DataEmptyException {
		
		Connection conexion=null;
		
		try {
			
			conexion = config.conectarse();
			
			conexion.setAutoCommit(false);
			
			PreparedStatement actDireccion = conexion.prepareStatement("update direcciones set calle=?, numero=?, latitud=?, longitud=?, ciudad=? "
					+ "where iddireccion=?", PreparedStatement.RETURN_GENERATED_KEYS);
			
			actDireccion.setString(1, ciudadano.getDireccion().getCalle());
			actDireccion.setInt(2, ciudadano.getDireccion().getNumero());
			actDireccion.setString(3, ciudadano.getDireccion().getLatitud());
			actDireccion.setString(4, ciudadano.getDireccion().getLongitud());
			actDireccion.setString(5, ciudadano.getDireccion().getCiudad());
			actDireccion.setLong(6, ciudadano.getDireccion().getId());
			
			actDireccion.executeUpdate();
			
			PreparedStatement actCiudadano = conexion.prepareStatement("update ciudadanos set iddireccion=?, idpersona=? where idciudadano=?");
			
			actCiudadano.setLong(1, ciudadano.getDireccion().getId());
			
			actCiudadano.setLong(2, ciudadano.getPersona().getId());
			
			actCiudadano.setLong(3, ciudadano.getId());
			
			actCiudadano.executeUpdate();
			
			conexion.commit();
			
			actCiudadano.close();
			
			actDireccion.close();
			
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

	@Override
	public void remove(Long id) throws JDBCException, DataEmptyException {
		
		Connection conexion=null;
		
		try {
			
			conexion = config.conectarse();
			
			PreparedStatement sentencia = conexion.prepareStatement("update ciudadanos set activo = 0 where idciudadano=?");
			sentencia.setLong(1, id);
			
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
	public void remove(Ciudadano ciudadano) throws JDBCException, DataEmptyException {
		try {
			remove(ciudadano.getId());
		}
		catch (JDBCException e) {
			throw e;
		}
	}

	@Override
	public Ciudadano find(Long id) throws JDBCException, DataEmptyException, InvalidStringLengthException, InvalidDateRangeException, DateOutOfBoundariesException {
		
		Connection conexion = null;
		
		try {
			
			conexion = config.conectarse();
			
			PreparedStatement sentencia = conexion.prepareStatement("select c.idciudadano, c.activo, d.*, p.* from ciudadanos c join direcciones d on(c.iddireccion=d.iddireccion) "
					+ "join personas p on(c.idpersona=p.idpersona) where c.idciudadano = ?");
			
			sentencia.setLong(1, id);
			
			ResultSet resultado = sentencia.executeQuery();
			
			Ciudadano ciudadano=null;
			
			if(resultado.next()) {
				Direccion direccion = new Direccion(resultado.getString("calle"), resultado.getInt("numero"), resultado.getString("latitud"), resultado.getString("longitud"), resultado.getString("ciudad"));
				direccion.setId(resultado.getLong("iddireccion"));
				
				Persona persona = new Persona(resultado.getString("nombre"), resultado.getString("apellido"), resultado.getInt("dni"));
				persona.setId(resultado.getLong("idpersona"));
				
				ciudadano = new Ciudadano(direccion, persona);
				ciudadano.setId(resultado.getLong("idciudadano"));
				ciudadano.setActivo(resultado.getBoolean("c.activo"));
			}
			
			resultado.close();
			
			sentencia.close();
			
			conexion.close();
			
			return ciudadano;
			
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
	public List<Ciudadano> findAll() throws JDBCException, DataEmptyException, InvalidStringLengthException, InvalidDateRangeException, DateOutOfBoundariesException {
		
		Connection conexion = null;
		
		try {
			
			conexion = config.conectarse();
			
			Statement sentencia = conexion.createStatement();
			
			ResultSet resultado = sentencia.executeQuery("select c.idciudadano, c.activo, d.*, p.* from ciudadanos c join direcciones d on (c.iddireccion=d.iddireccion) "
					+ "join personas p on (c.idpersona=p.idpersona)");
			
			ArrayList<Ciudadano> lista = new ArrayList<Ciudadano>();
			
			while (resultado.next()) {
				
				Direccion direccion = new Direccion(resultado.getString("calle"), resultado.getInt("numero"), resultado.getString("latitud"), resultado.getString("longitud"), resultado.getString("ciudad"));
				direccion.setId(resultado.getLong("iddireccion"));
				
				Persona persona = new Persona(resultado.getString("nombre"), resultado.getString("apellido"), resultado.getInt("dni"));
				persona.setId(resultado.getLong("idpersona"));
				
				Ciudadano ciudadano = new Ciudadano(direccion, persona);
				ciudadano.setId(resultado.getLong("idciudadano"));
				ciudadano.setActivo(resultado.getBoolean("c.activo"));
				
				lista.add(ciudadano);

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
