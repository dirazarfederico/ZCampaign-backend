package ar.edu.unrn.seminario.DAOs;

import ar.edu.unrn.seminario.modelo.*;
import java.util.*;

public interface ICampa�aDAO {
	
	public void create(Campa�a campa�a) throws Exception;
	
	public void update(Campa�a campa�a) throws Exception;
	
	public void remove(Long id) throws Exception;
	
	public void remove(Campa�a campa�a) throws Exception;
	
	public Campa�a find(Long id) throws Exception;
	
	public List<Campa�a> findAll() throws Exception;
	
}
