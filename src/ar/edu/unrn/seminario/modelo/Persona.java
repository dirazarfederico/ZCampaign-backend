package ar.edu.unrn.seminario.modelo;
import ar.edu.unrn.seminario.excepciones.*;

public class Persona {
	private Long idPersona;
	private String nombre;
	private String apellido;
	private int dni;
	private boolean activo;
	
	public Persona(String nuevoNombre, String nuevoApellido, int nuevoDni) throws DataEmptyException, InvalidStringLengthException {
		if(nuevoDni<=11111111 || nuevoDni>99999999) {
			throw new DataEmptyException("El DNI proporcionado no es valido");
		}
		if(nuevoNombre.isEmpty() || nuevoNombre==null) {
			throw new DataEmptyException("El nombre proporcionado no es valido");
		}
		if(nuevoNombre.length() > 100) {
			throw new InvalidStringLengthException("El nombre debe tener como máximo 45 caracteres");
		}
		if(nuevoApellido.isEmpty() || nuevoApellido==null) {
			throw new DataEmptyException("El apellido proporcionado no es valido");
		}
		if(nuevoApellido.length() > 100) {
			throw new InvalidStringLengthException("El apellido debe tener como máximo 45 caracteres");
		}
		nombre=nuevoNombre;
		apellido=nuevoApellido;
		dni=nuevoDni;
		activo = true;
	}
	
	public Long getId() {
		return idPersona;
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
		idPersona=nuevoId;
	}
	
	public int getDni() {
		return dni;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getApellido() {
		return apellido;
	}
	
	public String toString() {
		StringBuffer data=new StringBuffer();
		data.append(nombre);
		data.append(" " + apellido);
		data.append(" " + dni);
		
		return data.toString();
	}
}
