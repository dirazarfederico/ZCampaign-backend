package ar.edu.unrn.seminario.DAOs;

import ar.edu.unrn.seminario.modelo.*;
import java.util.*;

public interface IDonacionDAO {
	
	public void create(Donacion donacion) throws Exception;
	
	public void update(Donacion donacion) throws Exception;
	
	public void remove(Long id) throws Exception;
	
	public void remove(Donacion donacion) throws Exception;
	
	public Donacion find(Long id) throws Exception;
	
	public List<Donacion> findAll() throws Exception;
	
}