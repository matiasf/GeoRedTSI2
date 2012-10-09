package persistencia;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

@Stateless
public class InvitacionDAOImpl extends BaseDAO<Invitacion> implements
		InvitacionDAO {
	
	private static final String invitacionesPorUsuarioQuery = 
			"SELECT i FROM Usuario u" +
			"WHERE i MEMBER OF u.invitaciones" +
			"	AND u.id = :idUsuario";
	
	private static final String invitacionPorContactoRteQuery = 
			"SELECT i FROM Usuario u" +
			"WHERE i MEMBER OF u.invitaciones" +
			"	AND u.id = :idContacto" +
			"	AND i.remitente = :idRemitente";

	@Override
	public Invitacion buscarPorId(int id) {
		Invitacion ret = em.find(Invitacion.class, id);
		return ret;
	}

	@Override
	public List<Invitacion> getInvitacionesPorUsuario(int idUsuario) {
		TypedQuery<Invitacion> query = em.createQuery(invitacionesPorUsuarioQuery, 
				Invitacion.class);
		return query.getResultList();
	}
	
	@Override
	public Invitacion getInvitacionPorContactoRmte(int idContacto, int remitente) {
		TypedQuery<Invitacion> query = em.createQuery(invitacionPorContactoRteQuery, Invitacion.class);
		Invitacion ret = null;
		List<Invitacion> res = query.getResultList();
		if (!res.isEmpty()) {
			ret = res.get(0);
		}
		return ret;
	}
	

}
