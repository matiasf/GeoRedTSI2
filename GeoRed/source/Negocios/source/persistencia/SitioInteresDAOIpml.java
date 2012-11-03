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
	
	private static final String obtenerCatUsuarioQuery = 
			"SELECT si " +
			"FROM SitioInteres si JOIN si.categorias csi " +
			"WHERE EXISTS ( " +
			"	SELECT c FROM Usuario u JOIN u.categorias c" +
			"		WHERE c = csi AND" +
			"		u.id = :userId)";
	
	private static final String cantCheckInsQuery = 
			"SELECT count(c.id) " +
			"FROM SitioInteres si JOIN si.checkIns c " +
			"WHERE si.id = :idSitio";

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

	@Override
	public List<SitioInteres> obtenerParaUsuario(int id) {
		TypedQuery<SitioInteres> query = em.createQuery(obtenerCatUsuarioQuery, SitioInteres.class);
		query.setParameter("userId", id);
		return query.getResultList();
	}

	@Override
	public long cantCheckInSitio(int idSitio) {
		TypedQuery<Long> query = em.createQuery(cantCheckInsQuery, Long.class);
		query.setParameter("idSitio", idSitio);
		Long ret = query.getSingleResult();
		return ret;
	}

}
