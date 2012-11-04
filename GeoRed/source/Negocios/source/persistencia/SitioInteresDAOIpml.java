package persistencia;

import java.util.Calendar;
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
	
	private static final String cantCheckInsDesdeQuery = 
			"SELECT count(c.id) " +
			"FROM SitioInteres si JOIN si.checkIns c " +
			"WHERE si.id = :idSitio " +
			"	AND c.fecha >= :fecha";
	
	private static final String cantCheckInsHastaQuery = 
			"SELECT count(c.id) " +
			"FROM SitioInteres si JOIN si.checkIns c " +
			"WHERE si.id = :idSitio " +
			"	AND c.fecha <= :fecha";
	
	private static final String cantCheckInsEntreQuery = 
			"SELECT count(c.id) " +
			"FROM SitioInteres si JOIN si.checkIns c " +
			"WHERE si.id = :idSitio " +
			"	AND c.fecha >= :inicio " +
			"	AND c.fecha <= :fin";

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

	@Override
	public long cantCheckInSitioDesde(int idSitio, Calendar fecha) {
		TypedQuery<Long> query = em.createQuery(cantCheckInsDesdeQuery, Long.class);
		query.setParameter("idSitio", idSitio);
		query.setParameter("fecha", fecha);
		Long ret = query.getSingleResult();
		return ret;
	}

	@Override
	public long cantCheckInSitioHasta(int idSitio, Calendar fecha) {
		TypedQuery<Long> query = em.createQuery(cantCheckInsHastaQuery, Long.class);
		query.setParameter("idSitio", idSitio);
		query.setParameter("fecha", fecha);
		Long ret = query.getSingleResult();
		return ret;
	}

	@Override
	public long cantCheckInSitioEntre(int idSitio, Calendar inicio, Calendar fin) {
		TypedQuery<Long> query = em.createQuery(cantCheckInsEntreQuery, Long.class);
		query.setParameter("idSitio", idSitio);
		query.setParameter("inicio", inicio);
		query.setParameter("fin", fin);
		Long ret = query.getSingleResult();
		return ret;
	}

}
