package ar.edu.unrn.seminario.excepciones;

/* Se utiliza en la validaci�n de inicializaci�n correcta de atributos,
 * si un campo est� vacio, debe dispararse*/

public class DataEmptyException extends Exception{
	
	public DataEmptyException() {
		super();
	}
	
	public DataEmptyException(String msj) {
		super(msj);
	}
}
