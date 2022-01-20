package ar.edu.unrn.seminario.excepciones;

import java.sql.*;

/* Se dispara cuando ocurre alg�n error usando
 * el API JDBC */

public class JDBCException extends Exception {
	
	public JDBCException() {
		super();
	}
	
	public JDBCException(String msj) {
		super(msj);
	}
	
}
