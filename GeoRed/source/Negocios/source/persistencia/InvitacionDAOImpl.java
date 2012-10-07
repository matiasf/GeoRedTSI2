package persistencia;

public class InvitacionDAOImpl extends BaseDAO<Invitacion> implements
		InvitacionDAO {

	@Override
	public Invitacion buscarPorId(int id) {
		Invitacion ret = em.find(Invitacion.class, id);
		return ret;
	}

}
