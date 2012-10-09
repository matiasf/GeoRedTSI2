package persistencia;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

@Stateless
public class SitioInteresDAOIpml extends BaseDAO<SitioInteres> implements
		SitioInteresDAO {
	
	private static final String obtenerTodosQuery = 
			"SELECT si " +
			"FROM SitioInteres si";

	@Override
	public SitioInteres buscarPorId(int id) {
		SitioInteres ret = em.find(SitioInteres.class, id);
		return ret;
	}

	@Override
	public List<SitioInteres> obtenerTodos() {
		TypedQuery<SitioInteres> query = em.createQuery(obtenerTodosQuery, SitioInteres.class);
		return query.getResultList();
	}

}
