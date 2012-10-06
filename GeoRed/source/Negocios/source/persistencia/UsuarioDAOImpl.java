package persistencia;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

@Stateless
public class UsuarioDAOImpl extends BaseDAO<Usuario> implements UsuarioDAO{

	@Override
	public Usuario buscarPorId(int id) {
		Usuario ret = em.find(Usuario.class, id);
		return ret;
	}

	@Override
	public List<Usuario> obtenerTodos() {
		TypedQuery<Usuario> ret = em.createQuery(
				"SELECT u from USUARIOS u", Usuario.class);
		return ret.getResultList();
	}
	

}
