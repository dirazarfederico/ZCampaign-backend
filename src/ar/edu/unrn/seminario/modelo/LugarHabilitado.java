package ar.edu.unrn.seminario.modelo;
import ar.edu.unrn.seminario.excepciones.*;

public class LugarHabilitado {
	private Long idLugarHabilitado;
	private String nombre;
	private String contacto;
	private Direccion direccion;
	private Campa�a campa�a;
	private boolean activo;
	
	public LugarHabilitado(String nuevoNombre, String nuevoContacto, Direccion nuevaDireccion, Campa�a nuevaCampa�a) throws DataEmptyException, InvalidStringLengthException {
		if(nuevoNombre.isEmpty() || nuevoNombre==null) {
			throw new DataEmptyException("Lugar habilitado: El nombre proporcionado no es v�lido");
		}
		if(nuevoNombre.length() > 45) {
			throw new InvalidStringLengthException("Lugar habilitado: El nombre debe tener como m�ximo 45 caracteres");
		}
		if(nuevaDireccion==null) {
			throw new DataEmptyException("Lugar habilitado: La direccion proporcionada no es v�lida");
		}
		if(nuevaCampa�a==null) {
			throw new DataEmptyException("Lugar habilitado: Debe seleccionar una campa�a");
		}
		if(nuevoContacto==null) {
			throw new DataEmptyException("Lugar habilitado: Debe ingresar un contacto");
		}
		if(nuevoContacto.length() > 45) {
			throw new InvalidStringLengthException("Lugar habilitado: El contacto debe tener como m�ximo 45 caracteres");
		}
		
		nombre=nuevoNombre;
		contacto=nuevoContacto;
		direccion=nuevaDireccion;
		campa�a = nuevaCampa�a;
		activo = true;
	}
	
	public Direccion getDireccion() {
		return direccion;
	}
	
	public String getNombre() {
		return this.nombre;
	}

	public String getContacto() {
		return this.contacto;
	}
	
	public Campa�a getCampa�a() {
		return this.campa�a;
	}
	
	public void setActivo(boolean nuevo) {
		this.activo = nuevo;
	}
	
	public boolean activo() {
		return this.activo;
	}
	
	public Long getId() {
		return this.idLugarHabilitado;
	}
	
	public void setId(Long nuevoId) throws DataEmptyException {
		if(nuevoId==null) {
			throw new DataEmptyException("Debe ingresar un id v�lido");
		}
		this.idLugarHabilitado = nuevoId;
	}
	
	public String toString() {
		StringBuffer data=new StringBuffer();
		data.append(nombre);
		data.append(" " + contacto);
		data.append(" " + direccion);
		
		return data.toString();
	}

}
