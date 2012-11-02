package persistencia;

import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

@Stateless
public class EventoDAOImpl extends BaseDAO<Evento> implements EventoDAO {


	private static final String obtenerEventosQuery = 
			"SELECT e " +
			"FROM Evento e " +
			"WHERE e.inicio >= :fecha";
	
	@Override
	public Evento buscarPorId(int id) {
		return em.find(Evento.class, id);
	}

	@Override
	public List<Evento> obtenerEventos(Calendar fecha) {
		TypedQuery<Evento> query = em.createQuery(obtenerEventosQuery, Evento.class);
		query.setParameter("fecha", fecha);
		return query.getResultList();
	}

}
