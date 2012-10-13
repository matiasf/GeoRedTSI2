package persistencia;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

@Stateless
public class InvitacionDAOImpl extends BaseDAO<Invitacion> implements
		InvitacionDAO {
	
	private static final String invitacionesPorUsuarioQuery = 
			"SELECT i FROM Usuario u, Invitacion i " +
			"	WHERE u.id = :idUsuario " +
			"	AND i MEMBER OF u.invitaciones";
	
	private static final String invitacionPorContactoRteQuery = 
			"SELECT i FROM Usuario u, Invitacion i " +
			"WHERE i MEMBER OF u.invitaciones " +
			"	AND u.id = :idContacto " +
			"	AND i.remitente.id = :idRemitente ";

	@Override
	public Invitacion buscarPorId(int id) {
		Invitacion ret = em.find(Invitacion.class, id);
		return ret;
	}

	@Override
	public List<Invitacion> getInvitacionesPorUsuario(int idUsuario) {
		TypedQuery<Invitacion> query = em.createQuery(invitacionesPorUsuarioQuery, 
				Invitacion.class);
		query.setParameter("idUsuario", idUsuario);
		return query.getResultList();
	}
	
	@Override
	public Invitacion getInvitacionPorContactoRmte(int idContacto, int remitente) {
		TypedQuery<Invitacion> query = em.createQuery(invitacionPorContactoRteQuery, Invitacion.class);
		query.setParameter("idRemitente", remitente);
		query.setParameter("idContacto", idContacto);
		Invitacion ret = null;
		List<Invitacion> res = query.getResultList();
		if (!res.isEmpty()) {
			ret = res.get(0);
		}
		return ret;
	}
	

}
