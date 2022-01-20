package ar.edu.unrn.seminario.excepciones;

/* Es utilizada para abstraer las excepciones de niveles más bajos del API.
 * Cuando surge una excepción de construcción de objetos o de DAO, debe dispararse.
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
