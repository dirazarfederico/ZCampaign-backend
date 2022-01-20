package ar.edu.unrn.seminario.DAOs;

import java.util.List;

import ar.edu.unrn.seminario.modelo.Persona;

public interface IPersonaDAO {

	public void create(Persona persona) throws Exception;
	
	public void update(Persona persona) throws Exception;
	
	public void remove(Long id) throws Exception;
	
	public void remove(Persona persona) throws Exception;
	
	public Persona find(Long id) throws Exception;
	
	public List<Persona>findAll() throws Exception;
}
