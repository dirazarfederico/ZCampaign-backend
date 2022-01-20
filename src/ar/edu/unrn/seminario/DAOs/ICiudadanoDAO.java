package ar.edu.unrn.seminario.DAOs;

import ar.edu.unrn.seminario.modelo.*;
import java.util.*;

public interface ICiudadanoDAO {
	
	public void create(Ciudadano ciudadano) throws Exception;
	
	public void update(Ciudadano ciudadano) throws Exception;
	
	public void remove(Long id) throws Exception;
	
	public void remove(Ciudadano ciudadano) throws Exception;
	
	public Ciudadano find(Long id) throws Exception;
	
	public List<Ciudadano> findAll() throws Exception;
	
}
