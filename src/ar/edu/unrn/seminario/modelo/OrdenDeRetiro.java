package ar.edu.unrn.seminario.modelo;
import java.util.*;
import ar.edu.unrn.seminario.excepciones.*;
import ar.edu.unrn.seminario.servicio.Helper;

public class OrdenDeRetiro {
	private Long idOrden;
	private PedidoDeRetiro pedido;
	private Personal encargadoRetirar;
	private Direccion lugar;
	private Date fechaHora;
	private String estado;
	private List<Visita> visitas;
	private Campaña campaña;
	private boolean activo;

	public OrdenDeRetiro(PedidoDeRetiro nuevoPedido, Personal encargado) throws DataEmptyException {
		if(nuevoPedido==null) {
			throw new DataEmptyException("Orden de retiro: Debe seleccionar un pedido");
		}
		if(encargado==null) {
			throw new DataEmptyException("Orden de retiro: Debe seleccionar un encargado");
		}
		pedido=nuevoPedido;
		lugar=nuevoPedido.getCiudadano().getDireccion();
		fechaHora=new Date();
		estado="SIN_RETIRAR";
		visitas=new ArrayList<Visita>();
		campaña=pedido.getCampaña();
		encargadoRetirar=encargado;
		activo = true;
	}
	
	public OrdenDeRetiro(Date fechaHora, String nuevoEstado, Direccion direccion, Campaña campaña, Personal encargado, PedidoDeRetiro pedido) throws DataEmptyException, InvalidStringException {
		
		if(fechaHora==null) {
			throw new DataEmptyException("Orden de retiro: Debe especificar una fechaHora");
		}
		if(nuevoEstado==null || !nuevoEstado.matches("SIN_RETIRAR") && !nuevoEstado.matches("RETIRANDO") && !nuevoEstado.matches("FINALIZADA")) {
			throw new InvalidStringException("Orden de retiro: Debe ingresar un estado válido");
		}
		if(direccion==null) {
			throw new DataEmptyException("Orden de retiro: Debe especificar una dirección");
		}
		if(campaña==null) {
			throw new DataEmptyException("Orden de retiro: Debe especificar una campaña");
		}
		if(encargado==null) {
			throw new DataEmptyException("Orden de retiro: Debe especificar un personal");
		}
		if(pedido==null) {
			throw new DataEmptyException("Orden de retiro: Debe seleccionar un pedido");
		}
		
		this.fechaHora = fechaHora;
		lugar = direccion;
		estado = nuevoEstado;
		visitas = new ArrayList<Visita>();
		this.campaña = campaña;
		encargadoRetirar = encargado;
		activo = true;
		this.pedido = pedido;
	}
	
	public void finalizarOrden() throws InvalidStateException {
		if(estado.contentEquals("RETIRANDO")) {
			 estado="FINALIZADA";
		}
		else throw new InvalidStateException("Orden de retiro: La orden seleccionada no posee visitas");
	}
	
	public void agregarVisita(Visita nuevaVisita) throws DataEmptyException {
		if(nuevaVisita == null) {
			throw new DataEmptyException("Orden de retiro: Debe asignar una visita válida");
		}
		if(estado.contentEquals("SIN_RETIRAR") && visitas.size()==0) {	//Cambia de estado con la primera visita
			estado="RETIRANDO";
		}
		
		if(!estaFinalizada()) {	//Si la orden esta finalizada no se pueden agregar mas visitas
			visitas.add(nuevaVisita);
		}
	}
	
	public boolean estaFinalizada() {
		boolean resultado=false;
		
		if(estado.contentEquals("FINALIZADA"))
			resultado=true;
		
		return resultado;
	}
	
	public boolean estaRetirando() {
		boolean resultado=false;
		
		if(estado.contentEquals("RETIRANDO"))
			resultado=true;
		
		return resultado;
	}
	
	public PedidoDeRetiro getPedido() {
		return pedido;
	}
	
	public Direccion getDireccion() {
		return lugar;
	}
	
	public Date getFechaHora() {
		return fechaHora;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public Campaña getCampaña() {
		return campaña;
	}
	
	public Long getId() {
		return this.idOrden;
	}
	
	public Personal getPersonal() {
		return encargadoRetirar;
	}
	
	public void setActivo(boolean nuevo) {
		this.activo = nuevo;
	}
	
	public boolean activo() {
		return this.activo;
	}
	
	public void setId(Long nuevoId) throws DataEmptyException {
		if(nuevoId==null) {
			throw new DataEmptyException("Debe ingresar un id válido");
		}
		this.idOrden = nuevoId;
	}
	
	public String toString() {
		StringBuffer data=new StringBuffer();
		data.append(encargadoRetirar);
		data.append(" " + lugar);
		data.append(" " + Helper.convertirFechaHora(fechaHora));
		data.append(" " + estado);
		
		return data.toString();
	}
}
