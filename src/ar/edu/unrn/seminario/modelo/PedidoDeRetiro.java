package ar.edu.unrn.seminario.modelo;
import java.util.*;
import ar.edu.unrn.seminario.excepciones.*;
import ar.edu.unrn.seminario.servicio.Helper;

public class PedidoDeRetiro {
	private Long idPedido;
	private Ciudadano emisor;
	private Date fechaEmision;
	private String descripcion;
	private boolean requiereCargaPesada;
	private String observacion;
	private Campaña campaña;
	private boolean atendido;
	private boolean activo;

	public PedidoDeRetiro(Ciudadano nuevoEmisor, String nuevaDescripcion, boolean cargaPesada, String nuevaObservacion, Campaña nuevaCampaña) throws DataEmptyException, InvalidStringLengthException {
		if(nuevoEmisor==null) {
			throw new DataEmptyException("Pedido de retiro: Seleccione un ciudadano");
		}
		if(nuevaDescripcion.isEmpty() || nuevaDescripcion==null) {
			throw new DataEmptyException("Pedido de retiro: Ingrese una descripcion válida");
		}
		if(nuevaDescripcion.length() > 100) {
			throw new InvalidStringLengthException("Pedido de retiro: La descripcion debe tener como máximo 100 caracteres");
		}
		if(nuevaObservacion.isEmpty() || nuevaObservacion==null) {
			throw new DataEmptyException("Pedido de retiro:  Ingrese una observación");
		}
		if(nuevaObservacion.length() > 100) {
			throw new InvalidStringLengthException("Pedido de retiro: La observacion debe tener como máximo 100 caracteres");
		}
		if(nuevaCampaña==null) {
			throw new DataEmptyException("Pedido de retiro: El pedido debe pertenecer a una campaña");
		}
		
		emisor=nuevoEmisor;
		fechaEmision=new Date();
		descripcion=nuevaDescripcion;
		requiereCargaPesada=cargaPesada;
		observacion=nuevaObservacion;
		this.campaña = nuevaCampaña;
		atendido = false;
		activo = true;
	}
	
	public PedidoDeRetiro(Ciudadano nuevoEmisor, String nuevaDescripcion, boolean cargaPesada, String nuevaObservacion, Campaña nuevaCampaña, Date fecha, boolean atendido) throws DataEmptyException, DateOutOfBoundariesException {
		if(nuevoEmisor==null) {
			throw new DataEmptyException("Pedido de retiro: Seleccione un ciudadano");
		}
		if(nuevaDescripcion.isEmpty() || nuevaDescripcion==null) {
			throw new DataEmptyException("Pedido de retiro: Ingrese una descripcion válida");
		}
		if(nuevaDescripcion.isEmpty() || nuevaObservacion==null) {
			throw new DataEmptyException("Pedido de retiro:  Ingrese una observación");
		}
		if(nuevaCampaña==null) {
			throw new DataEmptyException("Pedido de retiro: El pedido debe pertenecer a una campaña");
		}
		if(fecha==null) {
			throw new DataEmptyException("Pedido de retiro: Ingrese una fecha válida");
		}
		if(fecha.after(new Date())) {
			throw new DateOutOfBoundariesException("Pedido de retiro: La fecha no puede ser posterior a la actual");
		}
		
		emisor=nuevoEmisor;
		fechaEmision=fecha;
		descripcion=nuevaDescripcion;
		requiereCargaPesada=cargaPesada;
		observacion=nuevaObservacion;
		this.campaña = nuevaCampaña;
		this.atendido = atendido;
		activo = true;
	}
	
	public void setId(Long nuevoId) throws DataEmptyException {
		if(nuevoId==null) {
			throw new DataEmptyException("Debe ingresar un id válido");
		}
		this.idPedido=nuevoId;
	}
	
	public Ciudadano getCiudadano() {
		return this.emisor;
	}
	
	public Date getFecha() {
		return this.fechaEmision;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
	
	public boolean requiereCargaPesada() {
		return this.requiereCargaPesada;
	}
	
	public boolean activo() {
		return this.activo;
	}
	
	public void setActivo(boolean nuevo) {
		this.activo = nuevo;
	}
	
	public boolean atendido() {
		return this.atendido;
	}
	
	public void setAtendido(boolean nuevo) {
		this.atendido = nuevo;
	}
		
	public String getObservacion() {
		return this.observacion;
	}
	
	public Campaña getCampaña() {
		return this.campaña;
	}
	
	public Long getId() {
		return this.idPedido;
	}
	
	public String toString() {
		String requiere = (requiereCargaPesada) ? "Sí" : "No";
		
		String atendido = (this.atendido) ? "atendido" : "no atendido";
		
		StringBuffer data=new StringBuffer();
		data.append(emisor);
		data.append(" " + Helper.convertir(fechaEmision));
		data.append(" " + descripcion);
		data.append(" " + requiere);
		data.append(" requiere carga pesada");
		data.append(" " + atendido);
		data.append(" " + observacion);
		
		return data.toString();
	}
}
