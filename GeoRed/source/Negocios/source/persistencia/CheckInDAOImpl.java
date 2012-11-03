package persistencia;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

@Stateless
public class CheckInDAOImpl extends BaseDAO<CheckIn> implements CheckInDAO {

	private static final String getCheckInAmigos = 
			"SELECT c FROM Usuario u " +
			"	JOIN u.contactos con " +
			"	JOIN con.checkIns c " +
			"	WHERE u.id = :userId";
	
	private static final String getCheckInAmigosLocal = 
			"SELECT c FROM Usuario u " +
			"	JOIN u.contactos con " +
			"	JOIN con.checkIns c " +
			"	WHERE u.id = :userId " +
			"		AND c.sitioInteres.id = :sitioId";
	
	@Override
	public CheckIn buscarPorId(int id) {
		return em.find(CheckIn.class, id);
	}

	@Override
	public List<CheckIn> getCheckInAmigos(int idUsuario) {
		TypedQuery<CheckIn> query = em.createQuery(getCheckInAmigos, CheckIn.class);
		query.setParameter("userId", idUsuario);
		return query.getResultList();
	}

	@Override
	public List<CheckIn> getCheckInAmigosLocal(int idUsuario, int sitioId) {
		TypedQuery<CheckIn> query = em.createQuery(getCheckInAmigosLocal, CheckIn.class);
		query.setParameter("userId", idUsuario);
		query.setParameter("sitioId", sitioId);
		return query.getResultList();
	}

}
