package persistencia;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

@Stateless
public class CategoriaDAOImpl extends BaseDAO<Categoria> implements CategoriaDAO{

	private static final String categoriasOfertaQuery = 
			"SELECT c " +
			"FROM Oferta e JOIN e.categorias c " +
			"WHERE e.id = :id";
	
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
	public List<Categoria> obtenerCategoriaOferta(int idOferta) {
		TypedQuery<Categoria> query = em.createQuery(categoriasOfertaQuery, Categoria.class);
		query.setParameter("id", idOferta);
		return query.getResultList();
	}

}
