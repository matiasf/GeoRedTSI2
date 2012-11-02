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

}
