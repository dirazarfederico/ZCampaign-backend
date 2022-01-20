package ar.edu.unrn.seminario.api;
import ar.edu.unrn.seminario.excepciones.*;
import ar.edu.unrn.seminario.modelo.*;
import java.util.*;

public interface IFacade {
	
	//Crea una campaña
	void guardarCampaña(String nombre, Date fechaInicio, Date fechaFin, String descripcion, String motivo, Long idInstitucion) throws AppException;
	
	//Crea una institución
	void guardarInstitucion(String nombre, String cuil, String contacto, String calle, int numero, String latitud, String longitud, String ciudad) throws AppException;
	
	//Crea un lugar habilitado
	void guardarLugarHabilitado(String nombre, String contacto, String calle, int numero, String latitud, String longitud, String ciudad, Long idCampaña) throws AppException;
	
	//Crea una persona
	Persona guardarPersona(String nombre, String apellido, int dni) throws AppException;
	
	//Crea un ciudadano
	void guardarCiudadano(Long idPersona, String calle, int numero, String latitud, String longitud, String ciudad) throws AppException;
	
	//Crea un personal
	void guardarPersonal(Long idInstitucion, Long idPersona, String mail, String foto) throws AppException;
	
	//Crea un pedido
	void registrarPedido(Long idEmisor, String descripcion, boolean cargaPesada, String nuevaObservacion, Long idCampaña) throws AppException;
	
	//Crea una orden de retiro
	void generarOrdenDeRetiro(Long idPedido, Long idPersonal) throws AppException;
	
	//Agrega una visita a la orden especificada
	void agregarVisita(String observacion, Date fecha, Long idOrden) throws AppException;
	
	//Finaliza una orden y genera una donación
	void finalizarOrden(Long idOrden, String descripcion) throws AppException;
		
	//Genera una donación a partir de una dirección
	void generarDonacionLugarHabilitado(Long idLugarHabilitado, String descripcion, Date fecha, Long idCampaña) throws AppException;
		
	//Modifica una campaña
	void actualizarCampaña(Campaña campaña) throws AppException;
	
	//Modifica una institución
	void actualizarInstitucion(Institucion institucion) throws AppException;
	
	//Modifica una persona
	void actualizarPersona(Persona persona) throws AppException;
	
	//Modifica un Ciudadano
	void actualizarCiudadano(Ciudadano ciudadano) throws AppException;
	
	//Modifica un pedido
	void actualizarPedido(PedidoDeRetiro pedido) throws AppException;
	
	//Modifica un Lugar Habilitado
	void actualizarLugarHabilitado(LugarHabilitado lugarHabilitado) throws AppException;
	
	//Modifica un Personal
	void actualizarPersonal(Personal personal) throws AppException;
	
	//Modifica una Orden de retiro
	void actualizarOrden(OrdenDeRetiro orden) throws AppException;
	
	//Modifica una Visita
	void actualizarVisita(Visita visita) throws AppException;
	
	//Modifica una Donacion
	void actualizarDonacion(Donacion donacion) throws AppException;
		
	//Lista las instituciones
	List<Institucion> listarInstituciones() throws AppException;
	
	//Lista las campañas
	List<Campaña> listarCampañas() throws AppException;
	
	//Lista las personas que son ciudadanos
	List<Ciudadano> listarCiudadanos() throws AppException;
	
	//Lista los pedidos
	List<PedidoDeRetiro> listarPedidos() throws AppException;
	
	//Lista los Lugares Habilitados
	List<LugarHabilitado> listarLugaresHabilitados() throws AppException;
	
	//Lista las personas que son personal
	List<Personal> listarPersonales() throws AppException;
	
	//Lista las personas
	List<Persona> listarPersonas() throws AppException;
	
	//Lista las ordenes de retiro
	List<OrdenDeRetiro> listarOrdenes() throws AppException;
	
	//Lista las visitas
	List<Visita> listarVisitas() throws AppException;
	
	//Lista las donaciones
	List<Donacion> listarDonaciones() throws AppException;
	
	//Devuelve una institucion
	Institucion buscarInstitucion(Long id) throws AppException;
	
	//Devuelve un Ciudadano
	Ciudadano buscarCiudadano(Long id) throws AppException;
	
	//Devuelve una Campaña
	Campaña buscarCampaña(Long id) throws AppException;
		
	//Devuelve un PedidoDeRetiro
	PedidoDeRetiro buscarPedido(Long id) throws AppException;
	
	//Devuelve un LugarHabilitado
	LugarHabilitado buscarLugarHabilitado(Long id) throws AppException;
	
	//Devuelve un Personal
	Personal buscarPersonal(Long id) throws AppException;
	
	//Devuelve una Persona
	Persona buscarPersona(Long id) throws AppException;
	
	//Devuelve una OrdenDeRetiro
	OrdenDeRetiro buscarOrden(Long id) throws AppException;
	
	//Devuelve una Visita
	Visita buscarVisita(Long id) throws AppException;
	
	//Devuelve una Donacion
	Donacion buscarDonacion(Long id) throws AppException;
	
	//Borra una Campaña
	void borrarCampaña(Campaña campaña) throws AppException;
	
	//Borra una Institucion
	void borrarInstitucion(Institucion institucion) throws AppException;
	
	//Borra un Ciudadano
	void borrarCiudadano(Ciudadano ciudadano) throws AppException;
		
	//Borra un PedidoDeRetiro
	void borrarPedido(PedidoDeRetiro pedido) throws AppException;
	
	//Borra un LugarHabilitado
	void borrarLugarHabilitado(LugarHabilitado lugarHabilitado) throws AppException;
	
	//Borra un Personal
	void borrarPersonal(Personal personal) throws AppException;
	
	//Borra una Persona
	void borrarPersona(Persona persona) throws AppException;
	
	//Borra una OrdenDeRetiro
	void borrarOrden(OrdenDeRetiro orden) throws AppException;
	
	//Borra una Visita
	void borrarVisita(Visita visita) throws AppException;
	
	//Borra una donacion
	void borrarDonacion(Donacion donacion) throws AppException;
}
