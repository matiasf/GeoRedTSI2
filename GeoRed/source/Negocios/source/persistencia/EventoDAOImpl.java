package persistencia;

public class EventoDAOImpl extends BaseDAO<Evento> implements EventoDAO {

	@Override
	public Evento buscarPorId(int id) {
		return em.find(Evento.class, id);
	}

}
