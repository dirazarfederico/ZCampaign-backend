package ar.edu.unrn.seminario.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.excepciones.AppException;
import ar.edu.unrn.seminario.excepciones.JDBCException;
import ar.edu.unrn.seminario.modelo.Campaña;
import ar.edu.unrn.seminario.modelo.Direccion;
import ar.edu.unrn.seminario.modelo.Donacion;
import ar.edu.unrn.seminario.modelo.OrdenDeRetiro;
import ar.edu.unrn.seminario.servicio.DatabaseConfig;

public class DonacionDAOjdbc implements IDonacionDAO {

	private ICampañaDAO campañaDAO;
	private IOrdenDeRetiroDAO ordenDAO;
	private DatabaseConfig config;
	
	public DonacionDAOjdbc(DatabaseConfig config) throws AppException {
		if(config == null)
			throw new AppException("Error de configuración de base de datos");
		this.config = config;
		campañaDAO = new CampañaDAOjdbc(config);
		ordenDAO = new OrdenDeRetiroDAOjdbc(config);
	}

	@Override
	public void create(Donacion donacion) throws Exception {
		Connection conexion = null;
		
		try {
		
			conexion = config.conectarse();
			
			conexion.setAutoCommit(false);
			
			PreparedStatement crearDonacion = conexion.prepareStatement("insert into donaciones (descripcion, fecha, iddireccion, idcampaña)" +
			" values (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
			
			crearDonacion.setString(1, donacion.getDescripcion());
			crearDonacion.setDate(2, new java.sql.Date(donacion.getFecha().getTime()));
			crearDonacion.setLong(3, donacion.getDireccion().getId());
			crearDonacion.setLong(4, donacion.getCampaña().getId());
			
			crearDonacion.executeUpdate();
			
			ResultSet claveDonacion = null;
			
			PreparedStatement donacionOrden = null;
			
			if(donacion.getOrden() != null) {
				
				claveDonacion = crearDonacion.getGeneratedKeys();
				
				claveDonacion.next();
				
				Long iddonacion = claveDonacion.getLong(1);
				
				donacionOrden = conexion.prepareStatement("insert into donacion_orden (iddonacion, idorden) values(?, ?)");
				
				donacionOrden.setLong(1, iddonacion);
				donacionOrden.setLong(2, donacion.getOrden().getId());
				
				donacionOrden.executeUpdate();
				
			}
			
			conexion.commit();
			
			claveDonacion.close();
			
			donacionOrden.close();
			
			crearDonacion.close();
			
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
	public void update(Donacion donacion) throws Exception {
		Connection conexion = null;
		
		try {
		
			conexion = config.conectarse();
			
			PreparedStatement actualizarDonacion = conexion.prepareStatement("update donaciones set descripcion = ?, fecha = ? where iddonacion = ?");
			
			actualizarDonacion.setString(1, donacion.getDescripcion());
			actualizarDonacion.setDate(2, new java.sql.Date(donacion.getFecha().getTime()));
			actualizarDonacion.setLong(3, donacion.getId());
			
			actualizarDonacion.executeUpdate();
			
			actualizarDonacion.close();
			
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
			throw new JDBCException("Modificación: Error de base de datos");
		  }
		  finally {
			try {
				if(conexion!=null) {
					conexion.close();
				}
			}
			catch(SQLException sqlEx2) {
				throw new JDBCException("Modificación: Error de base de datos");
			}
		  }
	}

	@Override
	public void remove(Long id) throws Exception {
		Connection conexion = null;
		
		try {
		
			conexion = config.conectarse();
			
			PreparedStatement borrarDonacion = conexion.prepareStatement("update donaciones set activo = 0 where iddonacion = ?");
			
			borrarDonacion.setLong(1, id);
			
			borrarDonacion.executeUpdate();
			
			borrarDonacion.close();
			
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
	public void remove(Donacion donacion) throws Exception {
		try {
			remove(donacion.getId());
		}
		catch (JDBCException e) {
			throw e;
		}
		
	}

	@Override
	public Donacion find(Long id) throws Exception {
		Connection conexion = null;
		
		try {
		
			conexion = config.conectarse();
			
			PreparedStatement sentencia = conexion.prepareStatement("select d.*, dir.iddireccion, dir.calle, dir.numero, dir.latitud, dir.longitud, dir.ciudad, don.idorden" +
										" from donaciones d join direcciones dir on(d.iddireccion=dir.iddireccion)" +
										" left join donacion_orden don on(don.iddonacion=d.iddonacion) left join ordenes o on(o.idorden=don.idorden) join campañas c on(d.idcampaña=c.idcampaña) where d.iddonacion = ?");
			
			sentencia.setLong(1, id);
			
			ResultSet resultado = sentencia.executeQuery();
			
			Donacion donacion = null;
			
			if(resultado.next()) {
				
				Campaña campaña = campañaDAO.find(resultado.getLong("idcampaña"));
				
				OrdenDeRetiro orden;
				
				Direccion direccion = new Direccion(resultado.getString("calle"), resultado.getInt("numero"), resultado.getString("latitud"), resultado.getString("longitud"), resultado.getString("ciudad"));
				direccion.setId(resultado.getLong("dir.iddireccion"));
				
				donacion = new Donacion(resultado.getString("descripcion"), direccion, resultado.getDate("fecha"), campaña);
				donacion.setId(resultado.getLong("d.iddonacion"));
				donacion.setActivo(resultado.getBoolean("d.activo"));
				
				Long idOrden = resultado.getLong("idorden");
				
				//Donacion provino de Orden de retiro
				if(!resultado.wasNull()) {
					orden = ordenDAO.find(idOrden);
					
					donacion.setOrden(orden);
				}
				
			}
			
			resultado.close();
			
			sentencia.close();
			
			conexion.close();
			
			return donacion;
			
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
	public List<Donacion> findAll() throws Exception {
		Connection conexion = null;
		
		try {
		
			conexion = config.conectarse();
			
			PreparedStatement sentencia = conexion.prepareStatement("select d.*, dir.iddireccion, dir.calle, dir.numero, dir.latitud, dir.longitud, dir.ciudad, don.idorden " + 
					"from donaciones d join direcciones dir on(d.iddireccion=dir.iddireccion) " + 
					"left join donacion_orden don on(don.iddonacion=d.iddonacion) left join ordenes o on(o.idorden=don.idorden) join campañas c on(d.idcampaña=c.idcampaña)");
			
			ResultSet resultado = sentencia.executeQuery();
			
			ArrayList<Donacion> lista = new ArrayList<Donacion>();
			
			Donacion donacion = null;
			
			while(resultado.next()) {
				
				Campaña campaña = campañaDAO.find(resultado.getLong("idcampaña"));
				
				OrdenDeRetiro orden;
				
				Direccion direccion = new Direccion(resultado.getString("calle"), resultado.getInt("numero"), resultado.getString("latitud"), resultado.getString("longitud"), resultado.getString("ciudad"));
				direccion.setId(resultado.getLong("dir.iddireccion"));
				
				donacion = new Donacion(resultado.getString("descripcion"), direccion, resultado.getDate("fecha"), campaña);
				donacion.setId(resultado.getLong("d.iddonacion"));
				donacion.setActivo(resultado.getBoolean("d.activo"));
				
				Long idOrden = resultado.getLong("idorden");
				
				//Donacion provino de Orden de retiro
				if(!resultado.wasNull()) {
					orden = ordenDAO.find(idOrden);
					
					donacion.setOrden(orden);
				}
				
				lista.add(donacion);
				
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
