package ar.edu.unrn.seminario.modelo;
import java.util.*;
import ar.edu.unrn.seminario.excepciones.*;

public class Institucion {
	private Long idInstitucion;
	private String nombre;
	private String cuil;
	private String contacto;
	private Direccion ubicacion;
	private boolean activo;

	public Institucion(String nuevoNombre, String nuevoCuil, String nuevoContacto, Direccion nuevaDireccion) throws DataEmptyException, InvalidStringLengthException {
		if(nuevoNombre.isEmpty() || nuevoNombre==null) {
			throw new DataEmptyException("Institución: El nombre proporcionado no es valido");
		}
		if(nuevoNombre.length() > 45) {
			throw new InvalidStringLengthException("Institución: El nombre debe tener como máximo 45 caracteres");
		}
		if(nuevoCuil.isEmpty() || nuevoCuil==null) {
			throw new DataEmptyException("Institución: El CUIL propocionado no es valido");
		}
		if(nuevoCuil.length() != 13) {
			throw new InvalidStringLengthException("Institución: El CUIL debe tener 13 caracteres");
		}
		if(nuevaDireccion==null) {
			throw new DataEmptyException("Institución: Debe seleccionar una dirección");
		}
		if(nuevoContacto.isEmpty() || nuevoContacto==null) {
			throw new DataEmptyException("Institución: El contacto proporcionado no es valido");
		}
		if(nuevoNombre.length() > 45) {
			throw new InvalidStringLengthException("Institución: El contacto debe tener como máximo 45 caracteres");
		}
		
		nombre=nuevoNombre;
		cuil=nuevoCuil;
		contacto=nuevoContacto;
		ubicacion=nuevaDireccion;
		activo = true;
	}
	
	public Institucion(String nuevoNombre) throws DataEmptyException {
		if(nuevoNombre.isEmpty() || nuevoNombre==null) {
			throw new DataEmptyException("Institución: El nombre proporcionado no es valido");
		}
		this.nombre = nuevoNombre;
	}
	
	public Long getId() {
		return this.idInstitucion;
	}
	
	public void setId(Long nuevoId) throws DataEmptyException {
		if(nuevoId==null) {
			throw new DataEmptyException("Debe ingresar un id válido");
		}
		this.idInstitucion=nuevoId;
	}
	
	public boolean activo() {
		return this.activo;
	}
	
	public void setActivo(boolean nuevo) {
		this.activo = nuevo;
	}
	
	public String toString() {
		StringBuffer data=new StringBuffer();
		data.append(" " + nombre);
		data.append(" " + cuil);
		data.append(" " + contacto);
		data.append(" " + ubicacion);
		
		return data.toString(); 
	}

	public String getNombre() {
		return this.nombre;
	}
	
	public String getCuil() {
		return this.cuil;
	}
	
	public String getContacto() {
		return this.contacto;
	}
	
	public Direccion getDireccion() {
		return this.ubicacion;
	}
}
