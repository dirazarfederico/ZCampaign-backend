package ar.edu.unrn.seminario.modelo;

import java.util.*;
import ar.edu.unrn.seminario.excepciones.*;
import ar.edu.unrn.seminario.servicio.Helper;

public class Campaña {
	private Long idCampaña;
	private Date fechaInicio;
	private Date fechaFin;
	private String descripcion;
	private String motivo;
	private String nombre;
	private Institucion beneficiado;
	private boolean activo;

	/*
	 * Crea una campaña desde la base de datos, sin institución. Es auxiliar
	 * */
	public Campaña(Date nuevaFechaInicio, Date nuevaFechaFin, String nuevaDescripcion, String nuevoMotivo,
			String nuevoNombre) throws InvalidDateRangeException, DataEmptyException, InvalidStringLengthException {
		
		if (nuevaFechaInicio == null) {
			throw new DataEmptyException("Campaña: La fecha de inicio proporcionada no es válida");
		}
		if (nuevaFechaFin == null) {
			throw new DataEmptyException("Campaña: La fecha de finalizacion proporcionada no es válida");
		}
		if (nuevaFechaInicio.after(nuevaFechaFin)) {
			throw new InvalidDateRangeException("Campaña: La fecha de inicio de la campaña no puede ser posterior a la fecha de finalización.");
		}
		if (nuevoNombre.isEmpty() || nuevoNombre.equals(null)) {
			throw new DataEmptyException("Campaña: El nombre proporcionado no es válido");
		}
		if(nuevoNombre.length() > 45 || nuevoNombre.length() < 10) {
			throw new InvalidStringLengthException("Campaña: El nombre debe contener entre 10 y 45 caracteres");
		}
		if(nuevaDescripcion.length() > 100) {
			throw new InvalidStringLengthException("Campaña: La descripción debe tener como máximo 100 caracteres");
		}
		if(nuevoMotivo.length() > 100) {
			throw new InvalidStringLengthException("Campaña: El motivo debe tener como máximo 100 caracteres");
		}

		fechaInicio = nuevaFechaInicio;
		fechaFin = nuevaFechaFin;
		descripcion = nuevaDescripcion;
		motivo = nuevoMotivo;
		nombre = nuevoNombre;
		activo = true;
	}

	/*
	 * Crea una campaña con institución
	 */
	public Campaña(Date nuevaFechaInicio, Date nuevaFechaFin, String nuevaDescripcion, String nuevoMotivo, String nuevoNombre,
			Institucion nuevoBeneficiado) throws InvalidDateRangeException, DataEmptyException, InvalidStringLengthException, DateOutOfBoundariesException {
		
		this(nuevaFechaInicio, nuevaFechaFin, nuevaDescripcion, nuevoMotivo, nuevoNombre);
		
		if (nuevoBeneficiado == null) {
			throw new DataEmptyException("Campaña: Debe seleccionar una institucion");
		}

		beneficiado = nuevoBeneficiado;
		activo = true;
	}

	public String toString() {
		StringBuffer data = new StringBuffer();
		data.append(Helper.convertir(fechaInicio));
		data.append(" " + Helper.convertir(fechaFin));
		data.append(" " + descripcion);
		data.append(" " + motivo);
		data.append(" " + nombre);
		data.append(" " + beneficiado);

		return data.toString();
	}

	public Long getId() {
		return this.idCampaña;
	}
	
	public void setId(Long id) throws DataEmptyException {
		if(id==null) {
			throw new DataEmptyException("Debe ingresar un id válido");
		}
		this.idCampaña = id;
	}
	
	public boolean activo() {
		return this.activo;
	}
	
	public void setActivo(boolean nuevo) {
		this.activo = nuevo;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public Date getFechaFin() {
		return this.fechaFin;
	}

	public String getNombre() {
		return this.nombre;
	}

	public String getMotivo() {
		return this.motivo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public Institucion getInstitucion() {
		return this.beneficiado;
	}
	
	public boolean estaVigente() {
		
		Date hoy = new Date();
		
		if(hoy.after(fechaInicio) && hoy.before(fechaFin)) {
			return true;
		}
		
		return false;
	}

}
