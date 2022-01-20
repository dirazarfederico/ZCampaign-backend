package ar.edu.unrn.seminario.excepciones;

/* Se utiliza en la validación del rango de fecha,
 * si es inválido, debe dispararse.
 * 
 * Un rango de fecha es inválido si el inicio
 * es posterior al final o si el final es
 * anterior al final.
 * */
public class InvalidDateRangeException extends Exception{
	
	public InvalidDateRangeException() {
		super();
	}
	
	public InvalidDateRangeException(String msj) {
		super(msj);
	}

}
