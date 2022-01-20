package ar.edu.unrn.seminario.excepciones;

/**
 * Esta excepción se dispara cuando se introduce un
 * texto con cantidad de caracteres distinta a los
 * rangos establecidos para un campo en particular.
 * 
 * @author Federico Dirazar
 *
 */

public class InvalidStringLengthException extends Exception {
	
	String msj;
	
	public InvalidStringLengthException() {
		super();
	}
	
	public InvalidStringLengthException(String msj) {
		super(msj);
	}
	
}
