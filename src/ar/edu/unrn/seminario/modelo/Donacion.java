package ar.edu.unrn.seminario.modelo;
import java.util.*;
import ar.edu.unrn.seminario.excepciones.*;
import ar.edu.unrn.seminario.servicio.Helper;

public class Donacion {
	private Long idDonacion;
	private Date fecha;
	private String descripcion;
	private Direccion origen;
	private Campaña campaña;
	private OrdenDeRetiro orden;
	private boolean activo;
	
	/*
	 * Para donaciones que provienen de un lugar habilitado
	 */
	public Donacion(String nuevaDescripcion, Direccion nuevoOrigen, Date nuevaFecha, Campaña nuevaCampaña) throws DataEmptyException, InvalidStringLengthException {
		if(nuevaDescripcion.isEmpty() || nuevaDescripcion==null) {
			throw new DataEmptyException("Donación: La descripcion proporcionada no es valida");
		}
		if(nuevaDescripcion.length() > 100) {
			throw new InvalidStringLengthException("Donación: El nombre debe tener como máximo 100 caracteres");
		}
		if(nuevoOrigen==null) {
			throw new DataEmptyException("Donación: El origen proporcionado no es valido");
		};
		if(nuevaFecha==null) {
			throw new DataEmptyException("Donación: Debe proporcionar una fecha");
		}
		if(nuevaCampaña==null) {
			throw new DataEmptyException("Donación: Debe proporcionar una campaña");
		}
		
		descripcion=nuevaDescripcion;
		origen=nuevoOrigen;
		fecha = nuevaFecha;
		campaña = nuevaCampaña;
		orden = null;
		activo = true;
	}
	
	/*
	 * Para donaciones a partir de ordenes de retiro finalizadas
	 */
	public Donacion(OrdenDeRetiro orden, String nuevaDescripcion) throws DataEmptyException {		
		if(orden==null) {
			throw new DataEmptyException("Donación: La orden proporcionada no es valida");
		}
		if(nuevaDescripcion.isEmpty() || nuevaDescripcion==null) {
			throw new DataEmptyException("Donación: La descripcion proporcionada no es valida");
		}
		fecha=new Date();
		origen=orden.getDireccion();
		descripcion=nuevaDescripcion;
		campaña = orden.getCampaña();
		this.orden = orden;
		activo = true;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
	
	public Long getId() {
		return this.idDonacion;
	}
	
	public void setId(Long id) throws DataEmptyException {
		if(id==null) {
			throw new DataEmptyException("El id ingresado no es válido");
		}
		this.idDonacion = id;
	}
	
	public Date getFecha() {
		return this.fecha;
	}
	
	public Direccion getDireccion() {
		return this.origen;
	}
	
	public Campaña getCampaña() {
		return this.campaña;
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
	
	public void setOrden(OrdenDeRetiro orden) throws DataEmptyException {
		if(orden==null) {
			throw new DataEmptyException("Donación: La orden proporcionada no es valida");
		}
		this.orden = orden;
	}
	
	public String toString() {
		StringBuffer data=new StringBuffer();
		data.append(Helper.convertir(fecha));
		data.append(" " + descripcion);
		data.append(" " + origen);
		
		return data.toString();
	}

}
