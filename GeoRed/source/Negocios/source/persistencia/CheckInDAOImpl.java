package persistencia;

public class CheckInDAOImpl extends BaseDAO<CheckIn> implements CheckInDAO {

	@Override
	public CheckIn buscarPorId(int id) {
		return em.find(CheckIn.class, id);
	}

}
