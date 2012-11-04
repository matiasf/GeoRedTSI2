package persistencia;

import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

@Stateless
public class OfertaDAOImpl extends BaseDAO<Oferta> implements OfertaDAO {

	private static final String obtenerOfertasUsrQuery = 
		"SELECT o " +
		"FROM Oferta o JOIN o.categorias oc " +
		"WHERE EXISTS ( " +
		"	SELECT c FROM Usuario u JOIN u.categorias c " +
		"		WHERE c =  oc AND " +
		"		u.id = :userId) " +
		"	AND o.local.id = :localId";
	
	private static final String obtenerPromedioValoracion = 
			"SELECT AVG(p.evaluacion) " +
			"FROM Oferta o " +
			"	JOIN o.pagos p " +
			"	WHERE o.id = :idOferta";
	
	private static final String obtenerTodasQuery = "SELECT o FROM Oferta o";
	
	private static final String obtenerPagosQuery = 
			"SELECT count(p.id) " +
			"FROM Oferta o JOIN o.pagos p " +
			"WHERE o.id = :idOferta";
	
	private static final String obtenerPagosDesdeQuery = 
			"SELECT count(p.id) " +
			"FROM Oferta o JOIN o.pagos p " +
			"WHERE o.id = :idOferta " +
			"	AND o.fecha >= :fecha";
	
	private static final String obtenerPagosHastaQuery = 
			"SELECT count(p.id) " +
			"FROM Oferta o JOIN o.pagos p " +
			"WHERE o.id = :idOferta " +
			"	AND o.fecha <= :fecha";
	
	private static final String obtenerPagosEntreQuery = 
			"SELECT count(p.id) " +
			"FROM Oferta o JOIN o.pagos p " +
			"WHERE o.id = :idOferta " +
			"	AND o.fecha BETWEEN :inicio AND :fin";
	
	@Override
	public Oferta buscarPorId(int id) {
		Oferta ret = em.find(Oferta.class, id);
		return ret;
	}

	@Override
	public List<Oferta> ofertasRelacionadas(int idLocal, int idUsuario) {
		TypedQuery<Oferta> query = em.createQuery(obtenerOfertasUsrQuery,Oferta.class);
		query.setParameter("userId", idUsuario);
		query.setParameter("localId", idLocal);
		return query.getResultList();
	}

	@Override
	public float promedioValoraciones(int idOferta) {
		TypedQuery<Double> query = em.createQuery(obtenerPromedioValoracion, Double.class);
		query.setParameter("idOferta", idOferta);
		return query.getSingleResult().floatValue();
	}

	@Override
	public List<Oferta> obtenerTodas() {
		TypedQuery<Oferta> query = em.createQuery(obtenerTodasQuery, Oferta.class);
		return query.getResultList();
	}

	@Override
	public long obtenerCantPagos(int idOferta) {
		TypedQuery<Long> query = em.createQuery(obtenerPagosQuery, Long.class);
		query.setParameter("idOferta", idOferta);
		return query.getSingleResult();
	}

	@Override
	public long obtenerCantPagosDesde(int idOferta, Calendar fecha) {
		TypedQuery<Long> query = em.createQuery(obtenerPagosDesdeQuery, Long.class);
		query.setParameter("idOferta", idOferta);
		query.setParameter("fecha", fecha);
		return query.getSingleResult();
	}

	@Override
	public long obtenerCantPagosHasta(int idOferta, Calendar fecha) {
		TypedQuery<Long> query = em.createQuery(obtenerPagosHastaQuery, Long.class);
		query.setParameter("idOferta", idOferta);
		query.setParameter("fecha", fecha);
		return query.getSingleResult();
	}

	@Override
	public long obtenerCantPagosEntre(int idOferta, Calendar desde,
			Calendar hasta) {
		TypedQuery<Long> query = em.createQuery(obtenerPagosEntreQuery, Long.class);
		query.setParameter("idOferta", idOferta);
		query.setParameter("inicio", desde);
		query.setParameter("fin", hasta);
		return query.getSingleResult();
	}



}
