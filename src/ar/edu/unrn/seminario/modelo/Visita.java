package ar.edu.unrn.seminario.modelo;
import java.util.*;
import ar.edu.unrn.seminario.excepciones.*;
import ar.edu.unrn.seminario.servicio.Helper;

public class Visita {
	private Long idVisita;
	private Date fechaHora;
	private String observacion;
	private OrdenDeRetiro orden;
	private boolean activo;

	public Visita(String nuevaObservacion, Date nuevaFechaHora, OrdenDeRetiro nuevaOrden) throws DataEmptyException, DateOutOfBoundariesException, InvalidStringLengthException {
		if(nuevaOrden==null) {
			throw new DataEmptyException("Visita: Debe ingresar una orden");
		}
		if(nuevaObservacion.isEmpty() || nuevaObservacion==null) {
			throw new DataEmptyException("Visita: La observacion proporcionada no es valida");
		}
		if(nuevaObservacion.length() > 100) {
			throw new InvalidStringLengthException("Visita: La observacion debe tener como máximo 100 caracteres");
		}
		if(nuevaFechaHora==null) {
			throw new DataEmptyException("Visita: Debe ingresar una fecha");
		}
		if(nuevaFechaHora.after(new Date())) {
			throw new DateOutOfBoundariesException("Visita: La fecha proporcionada no puede ser posterior a la fecha actual");
		}
		if(nuevaFechaHora.before(nuevaOrden.getFechaHora())) {
			throw new DateOutOfBoundariesException("Visita: La fecha proporcionada no puede ser anterior a la fecha de creación de la orden. (" + Helper.convertirFechaHora(nuevaOrden.getFechaHora()) + ")");
		}
		observacion=nuevaObservacion;
		fechaHora=nuevaFechaHora;
		orden=nuevaOrden;
		activo = true;
	}
	
	public Long getId() {
		return this.idVisita;
	}
	
	public void setId(Long nuevoId) throws DataEmptyException {
		if(nuevoId==null) {
			throw new DataEmptyException("Debe ingresar un id válido");
		}
		this.idVisita = nuevoId;
	}
	
	public Date getFechaHora() {
		return this.fechaHora;
	}
	
	public String getObservacion() {
		return this.observacion;
	}
	
	public OrdenDeRetiro getOrden() {
		return this.orden;
	}
	
	public boolean activo() {
		return this.activo;
	}
	
	public void setActivo(boolean nuevo) {
		this.activo = nuevo;
	}
	
	public String toString() {
		StringBuffer data=new StringBuffer();
		data.append(Helper.convertirFechaHora(fechaHora));
		data.append(" " + observacion);
		
		return data.toString();
	}
}
