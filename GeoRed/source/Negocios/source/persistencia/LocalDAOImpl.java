package persistencia;

public class LocalDAOImpl extends BaseDAO<Local> implements LocalDAO {

	@Override
	public Local buscarPorId(int id) {
		Local ret = em.find(Local.class, id);
		return ret;
	}

}
