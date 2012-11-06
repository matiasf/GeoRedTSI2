package persistencia;

import java.util.Calendar;
import java.util.List;

@javax.ejb.Local
public interface OfertaDAO {

	public Oferta insertar(Oferta entidad);
	
	public Oferta modificar(Oferta entidad);
	
	public Oferta buscarPorId(int id);
	
	public List<Oferta> ofertasRelacionadas(int idLocal, int idUsuario);
	
	public float promedioValoraciones(int idOferta);

	public List<Oferta> obtenerTodas();
	
	public long obtenerCantPagos(int idOferta);
	
	public long obtenerCantPagosDesde(int idOferta, Calendar fecha);
	
	public long obtenerCantPagosHasta(int idOferta, Calendar fecha);
	
	public long obtenerCantPagosEntre(int idOferta, Calendar desde, Calendar hasta);
	
	public List<Oferta> obtenerOfertasLocal(int idLocal);
	
	public void flush();
}
