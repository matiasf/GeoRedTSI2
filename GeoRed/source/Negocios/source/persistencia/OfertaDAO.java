package persistencia;

import java.util.List;

@javax.ejb.Local
public interface OfertaDAO {

	public Oferta insertar(Oferta entidad);
	
	public Oferta modificar(Oferta entidad);
	
	public Oferta buscarPorId(int id);
	
	public List<Oferta> ofertasRelacionadas(int idLocal, int idUsuario);
}
