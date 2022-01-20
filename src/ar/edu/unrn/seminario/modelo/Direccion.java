package ar.edu.unrn.seminario.modelo;
import ar.edu.unrn.seminario.excepciones.*;

public class Direccion {
	private Long idDireccion;
	private String calle;
	private int numero;
	private String latitud;
	private String longitud;
	private String ciudad;

	public Direccion(String nuevaCalle, int nuevoNumero, String nuevaLatitud, String nuevaLongitud, String nuevaCiudad) throws DataEmptyException, InvalidStringLengthException {
		if(nuevaCalle.isEmpty() || nuevaCalle==null) {
			throw new DataEmptyException("La calle no es valida");
		}
		if(nuevaCalle.length() < 5 || nuevaCalle.length() > 45) {
			throw new InvalidStringLengthException("La calle debe tener entre 5 y 45 caracteres");
		}
		if(nuevoNumero<=0) {
			throw new DataEmptyException("El numero no es valido");
		}
		if(nuevaLatitud.isEmpty() || nuevaLatitud==null) {
			throw new DataEmptyException("La latitud no es valida");
		}
		if(nuevaLatitud.length() != 12) {
			throw new InvalidStringLengthException("La latitud debe tener 12 caracteres");
		}
		if(nuevaLongitud.isEmpty() || nuevaLongitud==null) {
			throw new DataEmptyException("La longitud no es valida");
		}
		if(nuevaLongitud.length() != 12) {
			throw new InvalidStringLengthException("La longitud debe tener 12 caracteres");
		}
		if(nuevaCiudad.isEmpty() || nuevaCiudad==null) {
			throw new DataEmptyException("La ciudad ingresada no es valida");
		}
		if(nuevaCiudad.length() < 5 || nuevaCiudad.length() > 45) {
			throw new InvalidStringLengthException("La ciudad debe tener entre 5 y 45 caracteres");
		}
		calle=nuevaCalle;
		numero=nuevoNumero;
		latitud=nuevaLatitud;
		longitud=nuevaLongitud;
		ciudad=nuevaCiudad;
		
	}
	
	public String getCalle() {
		return this.calle;
	}
	
	public int getNumero() {
		return this.numero;
	}
	
	public String getLatitud() {
		return this.latitud;
	}
	
	public String getLongitud() {
		return this.longitud;
	}
	
	public String getCiudad() {
		return this.ciudad;
	}
	
	public Long getId() {
		return this.idDireccion;
	}
	
	public void setId(Long nuevoId) throws DataEmptyException {
		if(nuevoId==null) {
			throw new DataEmptyException("Debe ingresar un id válido");
		}
		this.idDireccion=nuevoId;
	}
	
	public String toString() {
		StringBuffer data=new StringBuffer();
		data.append(calle);
		data.append(" " + numero);
		data.append(", " + ciudad);
		
		return data.toString(); 
	}
}
