package ar.edu.unrn.seminario.DAOs;

import java.util.*;

import java.sql.*;

import ar.edu.unrn.seminario.excepciones.AppException;
import ar.edu.unrn.seminario.excepciones.DataEmptyException;
import ar.edu.unrn.seminario.excepciones.DateOutOfBoundariesException;
import ar.edu.unrn.seminario.excepciones.InvalidDateRangeException;
import ar.edu.unrn.seminario.excepciones.InvalidStringLengthException;
import ar.edu.unrn.seminario.excepciones.JDBCException;
import ar.edu.unrn.seminario.modelo.Campaña;
import ar.edu.unrn.seminario.modelo.Direccion;
import ar.edu.unrn.seminario.modelo.LugarHabilitado;
import ar.edu.unrn.seminario.servicio.DatabaseConfig;

public class LugarHabilitadoDAOjdbc implements ILugarHabilitadoDAO {

	private DatabaseConfig config;
	
	public LugarHabilitadoDAOjdbc(DatabaseConfig config) throws AppException {
		if(config == null)
			throw new AppException("Error de configuración de base de datos");
		this.config = config;
	}
	
	@Override
	public void create(LugarHabilitado lugarHabilitado) throws Exception {

		Connection conexion = null;
		
		try {

			conexion = config.conectarse();
			
			conexion.setAutoCommit(false);

			PreparedStatement crearDireccion = conexion.prepareStatement("insert into direcciones (calle, numero, latitud, longitud, ciudad) "
					+ "values (?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
			
			crearDireccion.setString(1, lugarHabilitado.getDireccion().getCalle());
			crearDireccion.setLong(2, lugarHabilitado.getDireccion().getNumero());
			crearDireccion.setString(3, lugarHabilitado.getDireccion().getLatitud());
			crearDireccion.setString(4, lugarHabilitado.getDireccion().getLongitud());
			crearDireccion.setString(5, lugarHabilitado.getDireccion().getCiudad());
			
			crearDireccion.executeUpdate();
			
			ResultSet claveDireccion = crearDireccion.getGeneratedKeys();
			
			claveDireccion.next();
			
			PreparedStatement sentencia = conexion.prepareStatement("insert into lugareshabilitados (nombre, contacto, iddireccion, idcampaña) "
					+ "values (?, ?, ?, ?)");
			
			sentencia.setString(1, lugarHabilitado.getNombre());
			sentencia.setString(2, lugarHabilitado.getContacto());
			sentencia.setLong(3, claveDireccion.getLong(1));
			sentencia.setLong(4, lugarHabilitado.getCampaña().getId());
			
			sentencia.executeUpdate();
			
			conexion.commit();
			
			claveDireccion.close();
			
			crearDireccion.close();
			
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

	@Override
	public void update(LugarHabilitado lugarHabilitado) throws Exception {
		
		Connection conexion = null;
		
		try {

			conexion = config.conectarse();
			
			conexion.setAutoCommit(false);
			
			PreparedStatement actualizarDireccion = conexion.prepareStatement("update direcciones set calle=?, numero=?, latitud=?, longitud=?, ciudad=? where iddireccion=?");
			
			actualizarDireccion.setString(1, lugarHabilitado.getDireccion().getCalle());
			actualizarDireccion.setInt(2, lugarHabilitado.getDireccion().getNumero());
			actualizarDireccion.setString(3, lugarHabilitado.getDireccion().getLatitud());
			actualizarDireccion.setString(4, lugarHabilitado.getDireccion().getLongitud());
			actualizarDireccion.setString(5, lugarHabilitado.getDireccion().getCiudad());
			actualizarDireccion.setLong(6, lugarHabilitado.getDireccion().getId());
			
			PreparedStatement sentencia = conexion.prepareStatement("update lugareshabilitados set nombre=?, contacto=? where idlugarhabilitado=?");
			
			sentencia.setString(1, lugarHabilitado.getNombre());
			sentencia.setString(2, lugarHabilitado.getContacto());
			sentencia.setLong(3, lugarHabilitado.getId());
			
			actualizarDireccion.executeUpdate();
			sentencia.executeUpdate();
			
			conexion.commit();
			
			actualizarDireccion.close();
			
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
			
			PreparedStatement sentencia = conexion.prepareStatement("update lugareshabilitados set activo = 0 where idlugarhabilitado=?");
			
			sentencia.setLong(1, id);
			
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
	public void remove(LugarHabilitado lugarHabilitado) throws Exception {

		try {
			remove(lugarHabilitado.getId());
		}
		catch(JDBCException e) {
			throw e;
		}
	}

	@Override
	public LugarHabilitado find(Long id) throws JDBCException, DataEmptyException, InvalidStringLengthException, InvalidDateRangeException, DateOutOfBoundariesException {

		Connection conexion = null;
		
		try {

			conexion = config.conectarse();
			
			PreparedStatement sentencia = conexion.prepareStatement("select lh.idlugarhabilitado, lh.nombre as lnombre, lh.contacto as lcontacto, lh.activo, cs.*, d.* from lugareshabilitados lh join campañas cs on (lh.idcampaña=cs.idcampaña) " + 
					"join direcciones d on (lh.iddireccion=d.iddireccion) where lh.idlugarhabilitado=?");
			
			sentencia.setLong(1, id);
			
			ResultSet resultado = sentencia.executeQuery();
			
			Direccion direccion;
			
			Campaña campaña;
			
			LugarHabilitado lugarHabilitado = null;
			
			if(resultado.next()) {
				
				direccion = new Direccion(resultado.getString("calle"), resultado.getInt("numero"), resultado.getString("latitud"), resultado.getString("longitud"), resultado.getString("ciudad"));
				
				direccion.setId(resultado.getLong("iddireccion"));
				
				campaña = new Campaña(resultado.getDate("fecha_inicio"), resultado.getDate("fecha_fin"), resultado.getString("descripcion"), resultado.getString("motivo"), resultado.getString("nombre"));
				
				campaña.setId(resultado.getLong("idcampaña"));
				
				lugarHabilitado = new LugarHabilitado(resultado.getString("lnombre"), resultado.getString("lcontacto"), direccion, campaña);
				lugarHabilitado.setId(resultado.getLong("idlugarhabilitado"));
				lugarHabilitado.setActivo(resultado.getBoolean("lh.activo"));
				
			}
			
			resultado.close();
			
			sentencia.close();
			
			conexion.close();
			
			return lugarHabilitado;
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
			
			throw new JDBCException("Búsqueda: Error de base de datos");
		  }
		catch(DataEmptyException e) {
			throw e;
		}
		catch(InvalidStringLengthException e) {
			throw e;
		}
		catch(InvalidDateRangeException e) {
			throw e;
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

	/**
	 * Devuelve una lista con todos los lugares habilitados.
	 * 
	 */
	@Override
	public List<LugarHabilitado> findAll() throws JDBCException, DataEmptyException, InvalidStringLengthException, InvalidDateRangeException, DateOutOfBoundariesException {
		
		Connection conexion = null;
		
		try {

			conexion = config.conectarse();
			
			Statement sentencia = conexion.createStatement();
			
			ResultSet resultado = sentencia.executeQuery("select lh.idlugarhabilitado, lh.nombre as lnombre, lh.contacto as lcontacto, lh.activo, cs.*, d.* from lugareshabilitados lh join campañas cs on (lh.idcampaña=cs.idcampaña) " + 
					"join direcciones d on (lh.iddireccion=d.iddireccion)");
			
			Direccion direccion;
			
			Campaña campaña;
			
			LugarHabilitado lugarHabilitado = null;
			
			ArrayList<LugarHabilitado> lista = new ArrayList<LugarHabilitado>();
			
			while(resultado.next()) {
				
				direccion = new Direccion(resultado.getString("calle"), resultado.getInt("numero"), resultado.getString("latitud"), resultado.getString("longitud"), resultado.getString("ciudad"));
				
				direccion.setId(resultado.getLong("iddireccion"));
				
				campaña = new Campaña(resultado.getDate("fecha_inicio"), resultado.getDate("fecha_fin"), resultado.getString("descripcion"), resultado.getString("motivo"), resultado.getString("nombre"));
				
				campaña.setId(resultado.getLong("idcampaña"));
				
				lugarHabilitado = new LugarHabilitado(resultado.getString("lnombre"), resultado.getString("lcontacto"), direccion, campaña);
				lugarHabilitado.setId(resultado.getLong("idlugarhabilitado"));
				lugarHabilitado.setActivo(resultado.getBoolean("lh.activo"));
				
				lista.add(lugarHabilitado);
				
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
