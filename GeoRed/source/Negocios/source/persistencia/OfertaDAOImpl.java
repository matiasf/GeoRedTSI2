package persistencia;

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



}
