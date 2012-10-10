package persistencia;

import java.util.List;

public interface SitioInteresDAO {

	public SitioInteres insertar(SitioInteres entidad);
	
	public SitioInteres modificar(SitioInteres entidad);
	
	public SitioInteres buscarPorId(int id);
	
	public void borrar(int id);
	
	public void borrar(SitioInteres entidad);
	
	public boolean existe(int id);
	
	public List<SitioInteres> obtenerTodos();
}
