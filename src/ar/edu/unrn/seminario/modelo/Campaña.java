package ar.edu.unrn.seminario.modelo;

import java.util.*;
import ar.edu.unrn.seminario.excepciones.*;
import ar.edu.unrn.seminario.servicio.Helper;

public class Campa�a {
	private Long idCampa�a;
	private Date fechaInicio;
	private Date fechaFin;
	private String descripcion;
	private String motivo;
	private String nombre;
	private Institucion beneficiado;
	private boolean activo;

	/*
	 * Crea una campa�a desde la base de datos, sin instituci�n. Es auxiliar
	 * */
	public Campa�a(Date nuevaFechaInicio, Date nuevaFechaFin, String nuevaDescripcion, String nuevoMotivo,
			String nuevoNombre) throws InvalidDateRangeException, DataEmptyException, InvalidStringLengthException {
		
		if (nuevaFechaInicio == null) {
			throw new DataEmptyException("Campa�a: La fecha de inicio proporcionada no es v�lida");
		}
		if (nuevaFechaFin == null) {
			throw new DataEmptyException("Campa�a: La fecha de finalizacion proporcionada no es v�lida");
		}
		if (nuevaFechaInicio.after(nuevaFechaFin)) {
			throw new InvalidDateRangeException("Campa�a: La fecha de inicio de la campa�a no puede ser posterior a la fecha de finalizaci�n.");
		}
		if (nuevoNombre.isEmpty() || nuevoNombre.equals(null)) {
			throw new DataEmptyException("Campa�a: El nombre proporcionado no es v�lido");
		}
		if(nuevoNombre.length() > 45 || nuevoNombre.length() < 10) {
			throw new InvalidStringLengthException("Campa�a: El nombre debe contener entre 10 y 45 caracteres");
		}
		if(nuevaDescripcion.length() > 100) {
			throw new InvalidStringLengthException("Campa�a: La descripci�n debe tener como m�ximo 100 caracteres");
		}
		if(nuevoMotivo.length() > 100) {
			throw new InvalidStringLengthException("Campa�a: El motivo debe tener como m�ximo 100 caracteres");
		}

		fechaInicio = nuevaFechaInicio;
		fechaFin = nuevaFechaFin;
		descripcion = nuevaDescripcion;
		motivo = nuevoMotivo;
		nombre = nuevoNombre;
		activo = true;
	}

	/*
	 * Crea una campa�a con instituci�n
	 */
	public Campa�a(Date nuevaFechaInicio, Date nuevaFechaFin, String nuevaDescripcion, String nuevoMotivo, String nuevoNombre,
			Institucion nuevoBeneficiado) throws InvalidDateRangeException, DataEmptyException, InvalidStringLengthException, DateOutOfBoundariesException {
		
		this(nuevaFechaInicio, nuevaFechaFin, nuevaDescripcion, nuevoMotivo, nuevoNombre);
		
		if (nuevoBeneficiado == null) {
			throw new DataEmptyException("Campa�a: Debe seleccionar una institucion");
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
		return this.idCampa�a;
	}
	
	public void setId(Long id) throws DataEmptyException {
		if(id==null) {
			throw new DataEmptyException("Debe ingresar un id v�lido");
		}
		this.idCampa�a = id;
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
