package ar.edu.unrn.seminario.DAOs;

import java.util.List;
import ar.edu.unrn.seminario.modelo.Personal;

public interface IPersonalDAO {

	public void create(Personal personal) throws Exception;
	
	public void update(Personal personal) throws Exception;
	
	public void remove(Long id) throws Exception;
	
	public void remove(Personal personal) throws Exception;
	
	public Personal find(Long id) throws Exception;
	
	public List<Personal> findAll() throws Exception;
}
