package ar.edu.unrn.seminario.excepciones;

/* Se utiliza en la validación de inicialización correcta de atributos,
 * si un campo está vacio, debe dispararse*/

public class DataEmptyException extends Exception{
	
	public DataEmptyException() {
		super();
	}
	
	public DataEmptyException(String msj) {
		super(msj);
	}
}
