package persistencia;

import java.util.Calendar;
import java.util.List;

public interface SitioInteresDAO {

	public SitioInteres insertar(SitioInteres entidad);
	
	public SitioInteres modificar(SitioInteres entidad);
	
	public SitioInteres buscarPorId(int id);
	
	public void borrar(int id);
	
	public void borrar(SitioInteres entidad);
	
	public boolean existe(int id);
	
	public List<SitioInteres> obtenerTodos();
	
	public List<SitioInteres> obtenerParaUsuario(int id);
	
	public long cantCheckInSitio(int idSitio);
	
	public long cantCheckInSitioDesde(int idSitio, Calendar fecha);
	
	public long cantCheckInSitioHasta(int idSitio, Calendar fecha);
	
	public long cantCheckInSitioEntre(int idSitio, Calendar inicio, Calendar fin);
	
	
	
	
}
