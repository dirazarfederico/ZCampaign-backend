package ar.edu.unrn.seminario.DAOs;

import ar.edu.unrn.seminario.modelo.*;
import java.util.*;

public interface IOrdenDeRetiroDAO {
	
	public void create(OrdenDeRetiro orden) throws Exception;
	
	public void update(OrdenDeRetiro orden) throws Exception;
	
	public void remove(Long id) throws Exception;
	
	public void remove(OrdenDeRetiro orden) throws Exception;
	
	public OrdenDeRetiro find(Long id) throws Exception;
	
	public List<OrdenDeRetiro> findAll() throws Exception;
	
}

