package ar.edu.unrn.seminario.DAOs;

import java.sql.*;

import ar.edu.unrn.seminario.modelo.*;
import ar.edu.unrn.seminario.servicio.DatabaseConfig;
import ar.edu.unrn.seminario.excepciones.*;
import java.util.*;

public class PedidoDAOjdbc implements IPedidoDAO {
	
	private IPersonaDAO personaDAO;
	private ICampañaDAO campañaDAO;
	
	private DatabaseConfig config;
	
	public PedidoDAOjdbc(DatabaseConfig config) throws AppException {
		if(config == null)
			throw new AppException("Error de configuración de base de datos");
		personaDAO = new PersonaDAOjdbc(config);
		campañaDAO = new CampañaDAOjdbc(config);
		this.config = config;
	}

	public void create(PedidoDeRetiro pedido) throws JDBCException, DataEmptyException {
		
		Connection conexion = null;
		
		try {

			conexion = config.conectarse();
			
			PreparedStatement sentencia = conexion.prepareStatement("insert into pedidos (emisor, fecha, descripcion, carga_pesada, observacion, idcampaña) "
					+ "values (?, ?, ?, ?, ?, ?)");
			
			sentencia.setLong(1, pedido.getCiudadano().getId());
			sentencia.setDate(2, new java.sql.Date(pedido.getFecha().getTime()));
			sentencia.setString(3, pedido.getDescripcion());
			sentencia.setBoolean(4, pedido.requiereCargaPesada());
			sentencia.setString(5, pedido.getObservacion());
			sentencia.setLong(6, pedido.getCampaña().getId());
			
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
				conexion.rollback();
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
	public void update(PedidoDeRetiro pedido) throws JDBCException, DataEmptyException {
		
		Connection conexion = null;
		
		try {

			conexion = config.conectarse();
			
			PreparedStatement sentencia = conexion.prepareStatement("update pedidos set emisor=?, fecha=?, descripcion=?, carga_pesada=?, observacion=?, atendido=? where idpedido=?");
			
			sentencia.setLong(1, pedido.getCiudadano().getId());
			sentencia.setDate(2, new java.sql.Date(pedido.getFecha().getTime()));
			sentencia.setString(3, pedido.getDescripcion());
			sentencia.setBoolean(4, pedido.requiereCargaPesada());
			sentencia.setString(5, pedido.getObservacion());
			sentencia.setBoolean(6, pedido.atendido());
			sentencia.setLong(7, pedido.getId());
			
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
				conexion.rollback();
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
			
			PreparedStatement sentencia = conexion.prepareStatement("update pedidos set activo = 0 where idpedido=?");
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
	public void remove(PedidoDeRetiro pedido) throws JDBCException, DataEmptyException {
		try {
			remove(pedido.getId());
		}
		catch (JDBCException e) {
			throw e;
		}
		
	}

	@Override
	public PedidoDeRetiro find(Long id) throws Exception {
		
		Connection conexion = null;
		
		try {

			conexion = config.conectarse();
			
			PreparedStatement sentencia = conexion.prepareStatement("select p.*, pers.idpersona, d.*, cs.idcampaña, c.idciudadano" + 
					" from pedidos p join campañas cs on (cs.idcampaña=p.idcampaña) join ciudadanos c on (c.idciudadano=p.emisor)" + 
					" join direcciones d on (c.iddireccion=d.iddireccion) join personas pers on (c.idpersona=pers.idpersona) where p.idpedido=?");
			
			sentencia.setLong(1, id);
			
			ResultSet resultado = sentencia.executeQuery();
			
			PedidoDeRetiro pedido = null;
			
			if(resultado.next()) {
				
				Persona nuevosDatos = personaDAO.find(resultado.getLong("pers.idpersona"));
				
				Direccion nuevaUbicacion = new Direccion(resultado.getString("calle"), resultado.getInt("numero"), resultado.getString("latitud"), resultado.getString("longitud"), resultado.getString("ciudad"));
				nuevaUbicacion.setId(resultado.getLong("iddireccion"));
				
				Ciudadano ciudadano = new Ciudadano(nuevaUbicacion, nuevosDatos);
				ciudadano.setId(resultado.getLong("c.idciudadano"));
				
				Campaña campaña = campañaDAO.find(resultado.getLong("cs.idcampaña"));
				
				pedido = new PedidoDeRetiro(ciudadano, resultado.getString("descripcion"), resultado.getBoolean("carga_pesada"), resultado.getString("observacion"), campaña, resultado.getDate("fecha"), resultado.getBoolean("p.atendido"));
				pedido.setId(resultado.getLong("idpedido"));
				pedido.setActivo(resultado.getBoolean("p.activo"));
			}
			
			resultado.close();
			
			sentencia.close();
			
			conexion.close();
			
			return pedido;
			
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
	public List<PedidoDeRetiro> findAll() throws Exception {
		
		Connection conexion = null;
		
		try {

			conexion = config.conectarse();
			
			Statement sentencia = conexion.createStatement();
			
			ResultSet resultado = sentencia.executeQuery("select p.*, pers.idpersona, d.*, cs.idcampaña, c.idciudadano" + 
					" from pedidos p join campañas cs on (cs.idcampaña=p.idcampaña) join ciudadanos c on (c.idciudadano=p.emisor)" + 
					" join direcciones d on (c.iddireccion=d.iddireccion) join personas pers on (c.idpersona=pers.idpersona)");
					
			ArrayList<PedidoDeRetiro> lista = new ArrayList<PedidoDeRetiro>();
			
			Campaña campaña= null;
			
			Ciudadano ciudadano = null;
			
			Direccion direccion = null;
			
			Persona persona = null;
			
			PedidoDeRetiro pedido = null;
			
			while (resultado.next()) {
				
				campaña = campañaDAO.find(resultado.getLong("cs.idcampaña"));
				
				direccion = new Direccion(resultado.getString("calle"), resultado.getInt("numero"), resultado.getString("latitud"), resultado.getString("longitud"), resultado.getString("ciudad"));
				direccion.setId(resultado.getLong("iddireccion"));
				
				persona = personaDAO.find(resultado.getLong("pers.idpersona"));
				
				ciudadano = new Ciudadano(direccion, persona);
				ciudadano.setId(resultado.getLong("c.idciudadano"));
				
				pedido = new PedidoDeRetiro(ciudadano, resultado.getString("descripcion"), resultado.getBoolean("carga_pesada"), resultado.getString("observacion"), campaña, resultado.getDate("fecha"), resultado.getBoolean("p.atendido"));
				pedido.setId(resultado.getLong("idpedido"));
				pedido.setActivo(resultado.getBoolean("p.activo"));
				
				lista.add(pedido);
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
}
