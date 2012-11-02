package persistencia;


import java.util.Calendar;
import java.util.List;
import javax.ejb.Local;

@Local
public interface EventoDAO {
	
	public Evento insertar(Evento entidad);
	
	public Evento modificar(Evento entidad);
	
	public Evento buscarPorId(int id);
	
	public List<Evento> obtenerEventos(Calendar fecha);

}
