package ar.edu.unrn.seminario.api;

import java.util.*;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import ar.edu.unrn.seminario.modelo.*;
import ar.edu.unrn.seminario.servicio.DatabaseConfig;
import ar.edu.unrn.seminario.DAOs.*;
import ar.edu.unrn.seminario.excepciones.*;

public class ApiFacade implements IFacade {
	
	private static ApiFacade instancia=null;
	private DatabaseConfig config;
	private ICampañaDAO campañaDAO;
	private IInstitucionDAO institucionDAO;
	private ICiudadanoDAO ciudadanoDAO;
	private IPedidoDAO pedidoDAO;
	private ILugarHabilitadoDAO lugarHabilitadoDAO;
	private IPersonalDAO personalDAO;
	private IPersonaDAO personaDAO;
	private IOrdenDeRetiroDAO ordenesDAO;
	private IVisitaDAO visitaDAO;
	private IDonacionDAO donacionDAO;
	
	public static ApiFacade getInstance() throws AppException {
		
		if(instancia==null) {
			instancia = new ApiFacade();
		}
		
		return instancia;
	}
	
	private ApiFacade() throws AppException {
		
		config = DatabaseConfig.getInstance(System.getProperty("user.dir") + "/config/db.properties");
		
		campañaDAO = new CampañaDAOjdbc(config);
		institucionDAO = new InstitucionDAOjdbc(config);
		ciudadanoDAO = new CiudadanoDAOjdbc(config);
		pedidoDAO = new PedidoDAOjdbc(config);
		lugarHabilitadoDAO = new LugarHabilitadoDAOjdbc(config);
		personalDAO = new PersonalDAOjdbc(config);
		personaDAO = new PersonaDAOjdbc(config);
		ordenesDAO = new OrdenDeRetiroDAOjdbc(config);
		visitaDAO = new VisitaDAOjdbc(config);
		donacionDAO = new DonacionDAOjdbc(config);
	}
	
	@Override
	public void guardarCampaña(String nombre, Date fechaInicio, Date fechaFin, String descripcion, String motivo, Long idinstitucion) throws AppException { 
	
		try {
			
			Institucion institucion = institucionDAO.find(idinstitucion);
			
			Campaña campaña = new Campaña(fechaInicio, fechaFin, descripcion, motivo, nombre, institucion);
			
			campañaDAO.create(campaña);
		}
		catch(Exception e) {
			throw new AppException(e.getMessage());
		}
		
	}

	@Override
	public void guardarInstitucion(String nombre, String cuil, String contacto, String calle, int numero, String latitud, String longitud, String ciudad) throws AppException {
		
		try {
			
			Direccion direccion = new Direccion(calle, numero, latitud, longitud, ciudad);
			
			Institucion institucion = new Institucion(nombre, cuil, contacto, direccion);
			
			institucionDAO.create(institucion);
			
		}
		catch(Exception e) {
			throw new AppException(e.getMessage());
		}
		
	}
	
	@Override
	public void guardarLugarHabilitado(String nombre, String contacto, String calle, int numero, String latitud, String longitud, String ciudad, Long idCampaña) throws AppException {
		
		try {
			
			Campaña campaña = campañaDAO.find(idCampaña);
			
			Direccion direccion = new Direccion(calle, numero, latitud, longitud, ciudad);
			
			LugarHabilitado lugarHabilitado = new LugarHabilitado(nombre, contacto, direccion, campaña);
			
			lugarHabilitadoDAO.create(lugarHabilitado);
			
		} catch (Exception e) {
			throw new AppException(e.getMessage());
		}
		
	}
	
	@Override
	public Persona guardarPersona(String nombre, String apellido, int dni) throws AppException {
		try {
			
			Persona persona = new Persona(nombre, apellido, dni);
			
			personaDAO.create(persona);
			
			return persona;
			
		}
		catch(Exception e) {
			throw new AppException(e.getMessage());
		}
	}

	@Override
	public void guardarCiudadano(Long idPersona, String calle, int numero, String latitud, String longitud, String ciudad) throws AppException {
		try {
			
			Direccion direccion = new Direccion(calle, numero, latitud, longitud, ciudad);
			
			Persona persona = personaDAO.find(idPersona);
			
			Ciudadano ciudadano = new Ciudadano(direccion, persona);
			
			ciudadanoDAO.create(ciudadano);
			
		}
		catch(Exception e) {
			throw new AppException(e.getMessage());
		}
		
	}

	@Override
	public void guardarPersonal(Long idInstitucion, Long idPersona, String mail, String foto) throws AppException {
		try {
			
			Persona persona = personaDAO.find(idPersona);
			
			Institucion institucion = institucionDAO.find(idInstitucion);
			
			Personal personal = new Personal(foto, mail, persona, institucion);
			
			personalDAO.create(personal);
			
		} catch (Exception e) {
			throw new AppException(e.getMessage());
		}
	}
	
	@Override
	public void registrarPedido(Long idEmisor, String descripcion, boolean cargaPesada, String observacion, Long idCampaña) throws AppException {
		
		try {
			
			Ciudadano emisor = ciudadanoDAO.find(idEmisor);
			
			Campaña campaña = campañaDAO.find(idCampaña);
			
			PedidoDeRetiro pedido = new PedidoDeRetiro(emisor, descripcion, cargaPesada, observacion, campaña);
			
			pedidoDAO.create(pedido);
			
		} catch (Exception e) {
			throw new AppException(e.getMessage());
		}
		
	}

	@Override
	public void generarOrdenDeRetiro(Long idPedido, Long idPersonal) throws AppException {
		
		try {
			
			PedidoDeRetiro pedido = pedidoDAO.find(idPedido);
			
			Personal encargado = personalDAO.find(idPersonal);
			
			OrdenDeRetiro orden = new OrdenDeRetiro(pedido, encargado);
			
			ordenesDAO.create(orden);
			
			pedido.setAtendido(true);
			
			pedidoDAO.update(pedido);
			
		}
		catch(Exception e) {
			throw new AppException(e.getMessage());
		}
	}

	@Override
	public void agregarVisita(String observacion, Date fecha, Long idOrden) throws AppException {
		
		try {
			
			OrdenDeRetiro orden = ordenesDAO.find(idOrden);
			
			Visita visita = new Visita(observacion, fecha, orden);
			
			orden.agregarVisita(visita);
			
			visitaDAO.create(visita);
			
			ordenesDAO.update(orden);
			
		}
		catch(Exception e) {
			throw new AppException(e.getMessage());
		}
		
	}
	
	@Override
	public void finalizarOrden(Long idOrden, String descripcion) throws AppException {
		try {
			
			OrdenDeRetiro orden = ordenesDAO.find(idOrden);
			
			orden.finalizarOrden();
			
			Donacion donacion = new Donacion(orden, descripcion);
			
			donacionDAO.create(donacion);
			
			ordenesDAO.update(orden);
			
		}
		catch(Exception e) {
			throw new AppException(e.getMessage());
		}
	}
	
	@Override
	public void generarDonacionLugarHabilitado(Long idLugarHabilitado, String descripcion, Date fecha, Long idCampaña) throws AppException {
		
		try {
			
			LugarHabilitado lugar = lugarHabilitadoDAO.find(idLugarHabilitado);
			Campaña campaña = campañaDAO.find(idCampaña);
			
			Donacion donacion = new Donacion(descripcion, lugar.getDireccion(), fecha, campaña);
			
			donacionDAO.create(donacion);
			
		}
		catch(Exception e) {
			throw new AppException(e.getMessage());
		}
		
	}

	@Override
	public void actualizarCampaña(Campaña campaña) throws AppException {
		try {
			campañaDAO.update(campaña);
		}
		catch(Exception e) {
			throw new AppException(e.getMessage());
		}
		
	}

	@Override
	public void actualizarInstitucion(Institucion institucion) throws AppException {
		
		try {
			institucionDAO.update(institucion);
		}
		catch(Exception e) {
			throw new AppException(e.getMessage());
		}
		
	}
	
	@Override
	public void actualizarPersona(Persona persona) throws AppException {
		try {
			personaDAO.update(persona);
		}
		catch(Exception e) {
			throw new AppException(e.getMessage());
		}
	}

	@Override
	public void actualizarCiudadano(Ciudadano ciudadano) throws AppException {
		
		try {
			
			personaDAO.update(ciudadano.getPersona());
			
			ciudadanoDAO.update(ciudadano);
		}
		catch(Exception e) {
			throw new AppException(e.getMessage());
		}
		
	}

	@Override
	public void actualizarPedido(PedidoDeRetiro pedido) throws AppException {
		
		try {
			pedidoDAO.update(pedido);
		}
		catch(Exception e) {
			throw new AppException(e.getMessage());
		}
		
	}
	
	public void actualizarLugarHabilitado(LugarHabilitado lugarHabilitado) throws AppException {
		
		try {
			
			lugarHabilitadoDAO.update(lugarHabilitado);
			
		}
		catch(Exception e) {
			throw new AppException(e.getMessage());
		}
		
	}
	
	public void actualizarPersonal(Personal personal) throws AppException {
		
		try {
			
			personaDAO.update(personal.getPersona());
			
			personalDAO.update(personal);
			
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new AppException(e.getMessage());
		}
	}
	
	@Override
	public void actualizarOrden(OrdenDeRetiro orden) throws AppException {
		try {
			
			ordenesDAO.update(orden);
			
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new AppException(e.getMessage());
		}
	}
	
	public void actualizarVisita(Visita visita) throws AppException {
		try {
			visitaDAO.update(visita);
		}
		catch(Exception e) {
			throw new AppException(e.getMessage());
		}
	}
	
	@Override
	public void actualizarDonacion(Donacion donacion) throws AppException {
		try {
			donacionDAO.update(donacion);
		}
		catch(Exception e) {
			throw new AppException(e.getMessage());
		}
	}

	@Override
	public List<Institucion> listarInstituciones() throws AppException {
		
		try {
			
			return institucionDAO.findAll();
		
		}
		catch(Exception e) {
			throw new AppException(e.getMessage());
		}
	}

	@Override
	public List<Campaña> listarCampañas() throws AppException {
		
		try {
			
			return campañaDAO.findAll();
			
		}
		catch(Exception e) {
			throw new AppException(e.getMessage());
		}
		
	}

	@Override
	public List<Ciudadano> listarCiudadanos() throws AppException {

		try {
			
			return ciudadanoDAO.findAll();
			
		}
		catch(Exception e) {
			throw new AppException(e.getMessage());
		}
		
	}

	@Override
	public List<PedidoDeRetiro> listarPedidos() throws AppException {

		try {
			
			return pedidoDAO.findAll();
			
		}
		catch(Exception e) {
			throw new AppException(e.getMessage());
		}
		
	}
	
	@Override
	public List<LugarHabilitado> listarLugaresHabilitados() throws AppException {

		try {
			
			return lugarHabilitadoDAO.findAll();
			
		}
		catch(Exception e) {
			throw new AppException(e.getMessage());
		}
		
	}
	
	@Override
	public List<Personal> listarPersonales() throws AppException {
		
		try {
			
			return personalDAO.findAll();
			
		}
		catch(Exception e) {
			throw new AppException(e.getMessage());
		}
		
	}
	
	public List<Persona> listarPersonas() throws AppException {
		
		try {
			
			return personaDAO.findAll();
			
		}
		catch(Exception e) {
			throw new AppException(e.getMessage());
		}
	}
	
	public List<OrdenDeRetiro> listarOrdenes() throws AppException {
		
		try {
			
			return ordenesDAO.findAll();

		}
		catch(Exception e) {
			throw new AppException(e.getMessage());
		}
		
	}
	
	@Override
	public List<Visita> listarVisitas() throws AppException {
		try {
			return visitaDAO.findAll();
		}
		catch(Exception e) {
			throw new AppException(e.getMessage());
		}
	}
	
	@Override
	public List<Donacion> listarDonaciones() throws AppException {
		
		try {
			return donacionDAO.findAll();
		}
		catch(Exception e) {
			throw new AppException(e.getMessage());
		}
	}

	@Override
	public Institucion buscarInstitucion(Long id) throws AppException {

		try {
			
			Institucion resultado = institucionDAO.find(id);
			
			return resultado;
		}
		catch (Exception e) {
			throw new AppException(e.getMessage());
		}
	}

	@Override
	public Ciudadano buscarCiudadano(Long id) throws AppException {
	
		try {
			
			Ciudadano resultado = ciudadanoDAO.find(id);
			
			return resultado;
		}
		catch (Exception e) {
			throw new AppException(e.getMessage());
		}
	}

	@Override
	public Campaña buscarCampaña(Long id) throws AppException {
		
		try {
			
			Campaña resultado = campañaDAO.find(id);
			
			return resultado;
		}
		catch (Exception e) {
			throw new AppException(e.getMessage());
		}
	}

	@Override
	public PedidoDeRetiro buscarPedido(Long id) throws AppException {
		
		try {
			
			PedidoDeRetiro resultado = pedidoDAO.find(id);
			
			return resultado;
		}
		catch (Exception e) {
			throw new AppException(e.getMessage());
		}

	}

	@Override
	public LugarHabilitado buscarLugarHabilitado(Long id) throws AppException {
		
		try {
			
			LugarHabilitado resultado = lugarHabilitadoDAO.find(id);
			
			return resultado;
		}
		catch (Exception e) {
			throw new AppException(e.getMessage());
		}
		
	}

	@Override
	public Personal buscarPersonal(Long id) throws AppException {
		
		try {
			
			Personal resultado = personalDAO.find(id);
			
			return resultado;
			
		}
		catch(Exception e) {
			throw new AppException(e.getMessage());
		}
		
	}

	@Override
	public Persona buscarPersona(Long id) throws AppException {
		
		try {
			
			Persona resultado = personaDAO.find(id);
			
			return resultado;
			
		}
		catch(Exception e) {
			throw new AppException(e.getMessage());
		}
		
	}
	
	@Override
	public OrdenDeRetiro buscarOrden(Long id) throws AppException {
		
		try {
			
			OrdenDeRetiro resultado = ordenesDAO.find(id);
			
			return resultado;
			
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new AppException(e.getMessage());
		}
		
	}
	
	@Override
	public Visita buscarVisita(Long id) throws AppException {
		
		
		try {
			
			Visita resultado = visitaDAO.find(id);
			
			return resultado;
			
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new AppException(e.getMessage());
		}
	}
	
	public Donacion buscarDonacion(Long id) throws AppException {
		
		
		try {
			
			Donacion resultado = donacionDAO.find(id);
			
			return resultado;
			
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new AppException(e.getMessage());
		}
	}
	
	@Override
	public void borrarCampaña(Campaña campaña) throws AppException {
		
		try {
			
			campañaDAO.remove(campaña);
			
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new AppException(e.getMessage());
		}
		
	}

	@Override
	public void borrarInstitucion(Institucion institucion) throws AppException {

		try {
			
			institucionDAO.remove(institucion);
			
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new AppException(e.getMessage());
		}

	}

	@Override
	public void borrarCiudadano(Ciudadano ciudadano) throws AppException {

		try {
			
			ciudadanoDAO.remove(ciudadano);
			
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new AppException(e.getMessage());
		}
		
	}

	@Override
	public void borrarPedido(PedidoDeRetiro pedido) throws AppException {

		try {
			
			pedidoDAO.remove(pedido);
			
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new AppException(e.getMessage());
		}
		
	}

	@Override
	public void borrarLugarHabilitado(LugarHabilitado lugarHabilitado) throws AppException {

		try {
			
			lugarHabilitadoDAO.remove(lugarHabilitado);
			
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new AppException(e.getMessage());
		}
		
	}

	@Override
	public void borrarPersonal(Personal personal) throws AppException {

		try {
			
			personalDAO.remove(personal);
			
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new AppException(e.getMessage());
		}
		
	}

	@Override
	public void borrarPersona(Persona persona) throws AppException {

		try {
			
			personaDAO.remove(persona);
			
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new AppException(e.getMessage());
		}

	}

	@Override
	public void borrarOrden(OrdenDeRetiro orden) throws AppException {

		try {
			
			ordenesDAO.remove(orden);
			
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new AppException(e.getMessage());
		}
		
	}

	@Override
	public void borrarVisita(Visita visita) throws AppException {

		try {
			
			visitaDAO.remove(visita);
			
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new AppException(e.getMessage());
		}
		
	}
	
	@Override
	public void borrarDonacion(Donacion donacion) throws AppException {

		try {
			
			donacionDAO.remove(donacion);
			
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new AppException(e.getMessage());
		}
		
	}
}
