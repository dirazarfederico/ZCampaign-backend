package ar.edu.unrn.seminario.modelo;

import ar.edu.unrn.seminario.excepciones.*;

public class Personal {
	private Long idPersonal;
	private String foto;
	private String email;
	private Persona datos;
	private Institucion institucion;
	private boolean activo;
	
	public Personal(String nuevaFoto, String nuevoEmail, Persona nuevosDatos, Institucion nuevaInstitucion) throws DataEmptyException, InvalidStringLengthException {
		if(nuevosDatos==null) {
			throw new DataEmptyException("Personal: Los datos proporcionados no son validos");
		}
		if(nuevaInstitucion==null) {
			throw new DataEmptyException("Personal: Debe indicarse una institución");
		}
		if(nuevoEmail.isEmpty() || nuevoEmail==null) {
			throw new DataEmptyException("Personal: Debe ingresar un email");
		}
		if(nuevoEmail.length() > 255) {
			throw new InvalidStringLengthException("Personal: El email debe tener como máximo 255 caracteres");
		}
		if(nuevaFoto.isEmpty() || nuevaFoto==null) {
			throw new DataEmptyException("Personal: Debe ingresar una foto");
		}
		if(nuevaFoto.length() > 255) {
			throw new InvalidStringLengthException("Personal: La ruta de la imagen debe tener como máximo 255 caracteres");
		}
		foto=nuevaFoto;
		email=nuevoEmail;
		datos=nuevosDatos;
		institucion=nuevaInstitucion;
	}
		
	public Persona getPersona() {
		return this.datos;
	}
	
	public Institucion getInstitucion() {
		return this.institucion;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getFoto() {
		return this.foto;
	}
	
	public boolean activo() {
		return this.activo;
	}
	
	public void setActivo(boolean nuevo) {
		this.activo = nuevo;
	}
		
	public void setId(Long id) throws DataEmptyException {
		if(id==null) {
			throw new DataEmptyException("Debe ingresar un id válido");
		}
		this.idPersonal = id;
	}
	
	public Long getId() {
		return this.idPersonal;
	}
	
	public String toString() {
		StringBuffer data=new StringBuffer();
		data.append(email);
		data.append(" " + datos);
		
		return data.toString();
	}
}
