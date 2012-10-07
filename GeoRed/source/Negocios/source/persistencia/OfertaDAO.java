package persistencia;

public interface OfertaDAO {

	public Oferta insertar(Oferta entidad);
	
	public void modificar(Oferta entidad);
	
	public Oferta buscarPorId(int id);
}
