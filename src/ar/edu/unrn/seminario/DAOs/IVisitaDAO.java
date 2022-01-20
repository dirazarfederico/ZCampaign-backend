package ar.edu.unrn.seminario.DAOs;

import java.util.List;

import ar.edu.unrn.seminario.modelo.*;

public interface IVisitaDAO {
	
public void create(Visita visita) throws Exception;
	
	public void update(Visita visita) throws Exception;
	
	public void remove(Long id) throws Exception;
	
	public void remove(Visita visita) throws Exception;
	
	public Visita find(Long id) throws Exception;
	
	public List<Visita> findAll() throws Exception;
	
}