package ar.edu.unrn.seminario.excepciones;

/* Se utiliza en la validación de fechas, cuando una fecha está
 * fuera del rango establecido, debe dispararse*/

	public class DateOutOfBoundariesException extends Exception{
		
		public DateOutOfBoundariesException() {
			super();
		}
	
		public DateOutOfBoundariesException(String msj) {
			super(msj);
		}

}
