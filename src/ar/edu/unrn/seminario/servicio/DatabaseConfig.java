package ar.edu.unrn.seminario.servicio;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import ar.edu.unrn.seminario.excepciones.AppException;
import ar.edu.unrn.seminario.excepciones.DataEmptyException;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {

	private static DatabaseConfig instancia = null;
	File configuracion;
	private String usuario;
	private String url;
	private String pwd;
	private String port;
	
	public static DatabaseConfig getInstance(String ruta) throws AppException {
		
		if(instancia==null) {
			instancia = new DatabaseConfig(ruta);
		}
		
		return instancia;
	}
	
	private DatabaseConfig(String ruta) throws AppException {
		configuracion = new File(ruta);
		
		try {
			
			Properties defecto = new Properties(), actuales;
			File propiedadesDefecto = new File("db.properties");
			FileInputStream entradaPropiedadesDefecto = new FileInputStream(propiedadesDefecto),
					entradaPropiedadesActuales = new FileInputStream(configuracion);
			
			// Leo propiedades por defecto
			defecto.load(entradaPropiedadesDefecto);
			
			//Inicializo la configuración nueva con la por defecto
			actuales = new Properties(defecto);
			
			actuales.load(entradaPropiedadesActuales);
			
			entradaPropiedadesActuales.close();
			entradaPropiedadesDefecto.close();
			
			this.usuario = actuales.getProperty("user");
			this.pwd = actuales.getProperty("pwd");
			this.port = actuales.getProperty("port");
			this.url = "jdbc:mysql://localhost:" + this.port + "/sistema_donaciones?serverTimezone=America/Argentina/Buenos_Aires";
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new AppException("El archivo de configuración no pudo ser encontrado");
		}
	}
	
	public java.sql.Connection conectarse() throws SQLException, DataEmptyException {
		
		if(usuario == null || usuario.isEmpty()) 
			throw new DataEmptyException("Error de configuración de base de datos: Debe ingresar un usuario");
		
		if(pwd == null || pwd.isEmpty())
			throw new DataEmptyException("Error de configuración de base de datos: Debe ingresar una contraseña");
		
		if(port == null || port.isEmpty())
			throw new DataEmptyException("Error de configuración de base de datos: Debe ingresar el puerto");
		
		return DriverManager.getConnection(url, usuario, pwd);
	}
}