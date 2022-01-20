package ar.edu.unrn.seminario.modelo;

import ar.edu.unrn.seminario.excepciones.*;
import java.util.*;

public class Ciudadano {
	private Long idCiudadano;
	private Direccion ubicacion;
	private Persona datos;
	private boolean activo;

	public Ciudadano(Direccion nuevaUbicacion, Persona nuevosDatos) throws DataEmptyException{
		if(nuevaUbicacion==null) {
			throw new DataEmptyException("Ciudadano: Debe seleccionar una dirección");
		}
		if(nuevosDatos==null) {
			throw new DataEmptyException("Ciudadano: Debe ingresar los datos requeridos");
		}
		ubicacion=nuevaUbicacion;
		datos=nuevosDatos;
		activo = true;
	}
	
	public Long getId() {
		return idCiudadano;
	}
	
	public void setId(Long nuevoId) throws DataEmptyException {
		if(nuevoId==null) {
			throw new DataEmptyException("Debe ingresar un id válido");
		}
		idCiudadano=nuevoId;
	}
	
	public Direccion getDireccion() {
		return ubicacion;
	}
	
	public Persona getPersona() {
		return datos;
	}
	
	public boolean activo() {
		return this.activo;
	}
	
	public void setActivo(boolean nuevo) {
		this.activo = nuevo;
	}
		
	public String toString() {
		StringBuffer data=new StringBuffer();
		data.append(datos.toString());
		data.append(" " + ubicacion.toString());
		
		return data.toString();
	}
}
