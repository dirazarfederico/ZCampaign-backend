package ar.edu.unrn.seminario.DAOs;

import ar.edu.unrn.seminario.modelo.*;
import java.util.*;

public interface ICampañaDAO {
	
	public void create(Campaña campaña) throws Exception;
	
	public void update(Campaña campaña) throws Exception;
	
	public void remove(Long id) throws Exception;
	
	public void remove(Campaña campaña) throws Exception;
	
	public Campaña find(Long id) throws Exception;
	
	public List<Campaña> findAll() throws Exception;
	
}
