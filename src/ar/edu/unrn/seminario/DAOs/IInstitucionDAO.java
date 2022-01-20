package ar.edu.unrn.seminario.DAOs;

import java.util.List;

import ar.edu.unrn.seminario.modelo.*;

public interface IInstitucionDAO {
	
public void create(Institucion institucion) throws Exception;
	
	public void update(Institucion institucion) throws Exception;
	
	public void remove(Long id) throws Exception;
	
	public void remove(Institucion institucion) throws Exception;
	
	public Institucion find(Long id) throws Exception;
	
	public List<Institucion> findAll() throws Exception;
	
}
