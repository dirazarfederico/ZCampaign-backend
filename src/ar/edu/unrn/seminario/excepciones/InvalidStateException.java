package ar.edu.unrn.seminario.excepciones;

/**
 * Esta excepción se dispara cuando se quiere
 * finalizar una orden de retiro pero no tiene
 * ninguna visita
 * 
 * @author Federico Dirazar
 *
 */

public class InvalidStateException extends Exception {
	
	String msj;
	
	public InvalidStateException() {
		super();
	}
	
	public InvalidStateException(String msj) {
		super(msj);
	}
	
}