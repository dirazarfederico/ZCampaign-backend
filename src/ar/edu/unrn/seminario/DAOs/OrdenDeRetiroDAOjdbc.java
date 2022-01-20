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
import ar.edu.unrn.seminario.modelo.Campaña;
import ar.edu.unrn.seminario.modelo.Direccion;
import ar.edu.unrn.seminario.modelo.OrdenDeRetiro;
import ar.edu.unrn.seminario.modelo.PedidoDeRetiro;
import ar.edu.unrn.seminario.modelo.Personal;
import ar.edu.unrn.seminario.modelo.Visita;
import ar.edu.unrn.seminario.servicio.DatabaseConfig;

public class OrdenDeRetiroDAOjdbc implements IOrdenDeRetiroDAO {
	
	private CampañaDAOjdbc campañaDAO;
	private PersonalDAOjdbc personalDAO;
	private PedidoDAOjdbc pedidoDAO;
	private DatabaseConfig config;
	
	public OrdenDeRetiroDAOjdbc(DatabaseConfig config) throws AppException {
		if(config == null)
			throw new AppException("Error de configuración de base de datos");
		campañaDAO = new CampañaDAOjdbc(config);
		personalDAO = new PersonalDAOjdbc(config);
		pedidoDAO = new PedidoDAOjdbc(config);
		this.config = config;
	}

	@Override
	public void create(OrdenDeRetiro orden) throws Exception {
		
		Connection conexion = null;
		
		try {

			conexion = config.conectarse();
			
			PreparedStatement crearOrden = conexion.prepareStatement("insert into ordenes (idpedido, iddireccion, fecha, estado, idcampaña, idpersonal)" +
			" values (?, ?, ?, ?, ?, ?)");
			
			crearOrden.setLong(1, orden.getPedido().getId());
			crearOrden.setLong(2, orden.getDireccion().getId());
			crearOrden.setTimestamp(3, new java.sql.Timestamp(orden.getFechaHora().getTime()));
			crearOrden.setString(4, orden.getEstado());
			crearOrden.setLong(5, orden.getCampaña().getId());
			crearOrden.setLong(6, orden.getPersonal().getId());
			
			crearOrden.executeUpdate();
			
			crearOrden.close();
			
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
	public void update(OrdenDeRetiro orden) throws Exception {
		
		Connection conexion = null;
		
		try {
			
			conexion = config.conectarse();
			
			PreparedStatement actualizarOrden = conexion.prepareStatement("update ordenes set fecha = ?, estado = ?, idpersonal = ? where idorden = ?");
			
			actualizarOrden.setTimestamp(1, new java.sql.Timestamp(orden.getFechaHora().getTime()));
			actualizarOrden.setString(2, orden.getEstado());
			actualizarOrden.setLong(3, orden.getPersonal().getId());
			actualizarOrden.setLong(4, orden.getId());
			
			actualizarOrden.executeUpdate();
			
			actualizarOrden.close();
			
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
			
			PreparedStatement borrarOrden = conexion.prepareStatement("update ordenes set activo = 0 where idorden = ?");
			
			borrarOrden.setLong(1, id);
			
			borrarOrden.executeUpdate();
			
			borrarOrden.close();
			
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
	public void remove(OrdenDeRetiro orden) throws Exception {
		
		try {
			remove(orden.getId());
		}
		catch (JDBCException e) {
			throw e;
		}
		
	}

	@Override
	public OrdenDeRetiro find(Long id) throws Exception {
		
		Connection conexion = null;
		
		try {

			conexion = config.conectarse();
			
			PreparedStatement sentencia = conexion.prepareStatement("select o.*, d.calle, d.numero, " +
			"d.latitud, d.longitud, d.ciudad from ordenes o join direcciones d on (o.iddireccion=d.iddireccion) where idorden = ?");
			
			sentencia.setLong(1, id);
			
			ResultSet resultado = sentencia.executeQuery();
			
			OrdenDeRetiro orden = null;
			
			if(resultado.next()) {
				
				Direccion direccion = new Direccion(resultado.getString("calle"), resultado.getInt("numero"), resultado.getString("latitud"), resultado.getString("longitud"), resultado.getString("ciudad"));
				direccion.setId(resultado.getLong("iddireccion"));
				
				Campaña campaña = campañaDAO.find(resultado.getLong("idcampaña"));
				
				Personal personal = personalDAO.find(resultado.getLong("idpersonal"));
				
				PedidoDeRetiro pedido = pedidoDAO.find(resultado.getLong("idpedido"));
				
				orden = new OrdenDeRetiro(resultado.getTimestamp("fecha"), resultado.getString("estado"), direccion, campaña, personal, pedido);
				orden.setId(resultado.getLong("idorden"));
				orden.setActivo(resultado.getBoolean("o.activo"));
				
				PreparedStatement visitas = conexion.prepareStatement("select * from visitas where idorden = ?");
				
				visitas.setLong(1, orden.getId());
				
				ResultSet resultadoVisitas = visitas.executeQuery();
				
				while(resultadoVisitas.next()) {
					Visita nuevaVisita = new Visita(resultadoVisitas.getString("observacion"), resultadoVisitas.getTimestamp("fecha"), orden);
					nuevaVisita.setId(resultadoVisitas.getLong("idvisita"));
					
					orden.agregarVisita(nuevaVisita);
				}
				
				resultadoVisitas.close();
				
				visitas.close();
				
			}
			
			resultado.close();
			
			sentencia.close();
			
			conexion.close();
			
			return orden;
			
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
	public List<OrdenDeRetiro> findAll() throws Exception {

		Connection conexion = null;
		
		try {

			conexion = config.conectarse();
			
			Statement sentencia = conexion.createStatement();
			
			ResultSet resultado = sentencia.executeQuery("select o.*, d.calle, d.numero, " +
					"d.latitud, d.longitud, d.ciudad from ordenes o join direcciones d on (o.iddireccion=d.iddireccion)");
			
			OrdenDeRetiro orden = null;
			
			Direccion direccion = null;
			
			Campaña campaña = null;
			
			Personal personal = null;
			
			PedidoDeRetiro pedido = null;
			
			List<OrdenDeRetiro> lista = new ArrayList<OrdenDeRetiro>();
			
			while (resultado.next()) {
				
				direccion = new Direccion(resultado.getString("calle"), resultado.getInt("numero"), resultado.getString("latitud"), resultado.getString("longitud"), resultado.getString("ciudad"));
				direccion.setId(resultado.getLong("iddireccion"));
				
				campaña = campañaDAO.find(resultado.getLong("idcampaña"));
				
				personal = personalDAO.find(resultado.getLong("idpersonal"));
				
				pedido = pedidoDAO.find(resultado.getLong("idpedido"));
				
				orden = new OrdenDeRetiro(resultado.getTimestamp("fecha"), resultado.getString("estado"), direccion, campaña, personal, pedido);
				orden.setId(resultado.getLong("idorden"));
				orden.setActivo(resultado.getBoolean("o.activo"));
				
				lista.add(orden);
				
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
