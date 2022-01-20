package ar.edu.unrn.seminario.DAOs;

import ar.edu.unrn.seminario.modelo.*;
import java.util.*;

public interface IPedidoDAO {
	
	public void create(PedidoDeRetiro pedido) throws Exception;
	
	public void update(PedidoDeRetiro pedido) throws Exception;
	
	public void remove(Long id) throws Exception;
	
	public void remove(PedidoDeRetiro pedido) throws Exception;
	
	public PedidoDeRetiro find(Long id) throws Exception;

	public List<PedidoDeRetiro> findAll() throws Exception;
}
