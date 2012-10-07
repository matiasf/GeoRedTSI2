package persistencia;

public class EmpresaDAOImpl extends BaseDAO<Empresa> implements EmpresaDAO {

	@Override
	public Empresa buscarPorId(int id) {
		Empresa ret = em.find(Empresa.class, id);
		return ret;
	}

}
