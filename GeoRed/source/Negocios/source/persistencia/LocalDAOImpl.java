package persistencia;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

@Stateless
public class LocalDAOImpl extends BaseDAO<Local> implements LocalDAO {

	private static final String getLocalesEmpresaQuery = 
		"SELECT l FROM Empresa e" +
		"WHERE e.id = :idEmpresa" +
		"	AND l MEMBER OF e.Locales";
	
	@Override
	public Local buscarPorId(int id) {
		Local ret = em.find(Local.class, id);
		return ret;
	}

	@Override
	public List<Local> getLocalesPorEmpresa(int idEmpresa) {
		TypedQuery<Local> query = em.createQuery(getLocalesEmpresaQuery, Local.class);
		return query.getResultList();
	}

}
