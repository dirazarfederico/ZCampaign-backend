package ar.edu.unrn.seminario.DAOs;

import java.util.List;
import ar.edu.unrn.seminario.modelo.*;

public interface ILugarHabilitadoDAO {

	public void create(LugarHabilitado lugarHabilitado) throws Exception;
	
	public void update(LugarHabilitado lugarHabilitado) throws Exception;
	
	public void remove(Long id) throws Exception;
	
	public void remove(LugarHabilitado lugarHabilitado) throws Exception;
	
	public LugarHabilitado find(Long id) throws Exception;
	
	public List<LugarHabilitado> findAll() throws Exception;
	
}
