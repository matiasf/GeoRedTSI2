package persistencia;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

@Stateless
public class CategoriaDAOImpl extends BaseDAO<Categoria> implements CategoriaDAO{

	@Override
	public Categoria buscarPorId(int id) {
		Categoria ret = em.find(Categoria.class, id);
		return ret;
	}

	@Override
	public List<Categoria> obtenerTodos() { 
		TypedQuery<Categoria> ret = em.createQuery(
				"SELECT c FROM Categoria", Categoria.class);
		return ret.getResultList();
	}

}
