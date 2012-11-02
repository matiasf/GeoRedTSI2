package persistencia;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

@Stateless
public class LocalDAOImpl extends BaseDAO<Local> implements LocalDAO {

	private static final String getLocalesEmpresaQuery = 
		"SELECT l FROM Empresa e , Local l " +
		"WHERE e.id = :idEmpresa " +
		"	AND l MEMBER OF e.locales";
	
	
	@Override
	public Local buscarPorId(int id) {
		Local ret = em.find(Local.class, id);
		return ret;
	}

	@Override
	public List<Local> getLocalesPorEmpresa(int idEmpresa) {
		TypedQuery<Local> query = em.createQuery(getLocalesEmpresaQuery, Local.class);
		query.setParameter("idEmpresa", idEmpresa);
		return query.getResultList();
	}

}
