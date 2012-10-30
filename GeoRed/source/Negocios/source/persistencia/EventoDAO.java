package persistencia;

public interface EventoDAO {
	
	public Evento insertar(Evento entidad);
	
	public Evento modificar(Evento entidad);
	
	public Evento buscarPorId(int id);

}
