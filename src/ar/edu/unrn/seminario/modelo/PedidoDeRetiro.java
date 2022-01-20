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
	private Campa�a campa�a;
	private boolean atendido;
	private boolean activo;

	public PedidoDeRetiro(Ciudadano nuevoEmisor, String nuevaDescripcion, boolean cargaPesada, String nuevaObservacion, Campa�a nuevaCampa�a) throws DataEmptyException, InvalidStringLengthException {
		if(nuevoEmisor==null) {
			throw new DataEmptyException("Pedido de retiro: Seleccione un ciudadano");
		}
		if(nuevaDescripcion.isEmpty() || nuevaDescripcion==null) {
			throw new DataEmptyException("Pedido de retiro: Ingrese una descripcion v�lida");
		}
		if(nuevaDescripcion.length() > 100) {
			throw new InvalidStringLengthException("Pedido de retiro: La descripcion debe tener como m�ximo 100 caracteres");
		}
		if(nuevaObservacion.isEmpty() || nuevaObservacion==null) {
			throw new DataEmptyException("Pedido de retiro:  Ingrese una observaci�n");
		}
		if(nuevaObservacion.length() > 100) {
			throw new InvalidStringLengthException("Pedido de retiro: La observacion debe tener como m�ximo 100 caracteres");
		}
		if(nuevaCampa�a==null) {
			throw new DataEmptyException("Pedido de retiro: El pedido debe pertenecer a una campa�a");
		}
		
		emisor=nuevoEmisor;
		fechaEmision=new Date();
		descripcion=nuevaDescripcion;
		requiereCargaPesada=cargaPesada;
		observacion=nuevaObservacion;
		this.campa�a = nuevaCampa�a;
		atendido = false;
		activo = true;
	}
	
	public PedidoDeRetiro(Ciudadano nuevoEmisor, String nuevaDescripcion, boolean cargaPesada, String nuevaObservacion, Campa�a nuevaCampa�a, Date fecha, boolean atendido) throws DataEmptyException, DateOutOfBoundariesException {
		if(nuevoEmisor==null) {
			throw new DataEmptyException("Pedido de retiro: Seleccione un ciudadano");
		}
		if(nuevaDescripcion.isEmpty() || nuevaDescripcion==null) {
			throw new DataEmptyException("Pedido de retiro: Ingrese una descripcion v�lida");
		}
		if(nuevaDescripcion.isEmpty() || nuevaObservacion==null) {
			throw new DataEmptyException("Pedido de retiro:  Ingrese una observaci�n");
		}
		if(nuevaCampa�a==null) {
			throw new DataEmptyException("Pedido de retiro: El pedido debe pertenecer a una campa�a");
		}
		if(fecha==null) {
			throw new DataEmptyException("Pedido de retiro: Ingrese una fecha v�lida");
		}
		if(fecha.after(new Date())) {
			throw new DateOutOfBoundariesException("Pedido de retiro: La fecha no puede ser posterior a la actual");
		}
		
		emisor=nuevoEmisor;
		fechaEmision=fecha;
		descripcion=nuevaDescripcion;
		requiereCargaPesada=cargaPesada;
		observacion=nuevaObservacion;
		this.campa�a = nuevaCampa�a;
		this.atendido = atendido;
		activo = true;
	}
	
	public void setId(Long nuevoId) throws DataEmptyException {
		if(nuevoId==null) {
			throw new DataEmptyException("Debe ingresar un id v�lido");
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
	
	public Campa�a getCampa�a() {
		return this.campa�a;
	}
	
	public Long getId() {
		return this.idPedido;
	}
	
	public String toString() {
		String requiere = (requiereCargaPesada) ? "S�" : "No";
		
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
