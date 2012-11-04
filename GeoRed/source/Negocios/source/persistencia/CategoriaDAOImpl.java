package persistencia;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

@Stateless
public class CategoriaDAOImpl extends BaseDAO<Categoria> implements CategoriaDAO{
		
	private static final String obtenerCatSitioInteres = 
			"SELECT csi " +
				"FROM SitioInteres si JOIN si.categorias csi " +
					"WHERE si.id = :idServicio";

	@Override
	public Categoria buscarPorId(int id) {
		Categoria ret = em.find(Categoria.class, id);
		return ret;
	}

	@Override
	public List<Categoria> obtenerTodos() { 
		TypedQuery<Categoria> ret = em.createQuery(
				"SELECT c FROM Categoria c", Categoria.class);
		return ret.getResultList();
	}
	
	@Override
	public List<Categoria> obtenerCategoriasDeSitioInteres(int idServicio) { 
		TypedQuery<Categoria> query = em.createQuery(obtenerCatSitioInteres, Categoria.class);
		query.setParameter("idServicio", idServicio);
		return query.getResultList();
	}

}
