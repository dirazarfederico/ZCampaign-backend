package ar.edu.unrn.seminario.excepciones;

/* Es utilizada para abstraer las excepciones de niveles m�s bajos del API.
 * Cuando surge una excepci�n de construcci�n de objetos o de DAO, debe dispararse.
 */

public class AppException extends Exception {
	
	String msj;
	
	public AppException() {
		super();
	}
	
	public AppException(String nuevoMsj) {
		super(nuevoMsj);
	}
	
}
