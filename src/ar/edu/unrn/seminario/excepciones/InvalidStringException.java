package ar.edu.unrn.seminario.excepciones;

/**
 * Esta excepci�n se dispara cuando se introduce un
 * texto con cantidad de caracteres distinta a los
 * rangos establecidos para un campo en particular.
 * 
 * @author Federico Dirazar
 *
 */

public class InvalidStringException extends Exception {
	
	String msj;
	
	public InvalidStringException() {
		super();
	}
	
	public InvalidStringException(String msj) {
		super(msj);
	}
	
}
