package persistencia;

public interface SitioInteresDAO {

	public SitioInteres insertar(SitioInteres entidad);
	
	public void modificar(SitioInteres entidad);
	
	public SitioInteres buscarPorId(int id);
}
