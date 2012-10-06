package persistencia;

public class UsuarioDAOImpl extends BaseDAO<Usuario> implements UsuarioDAO{

	@Override
	public Usuario buscarPorId(int id) {
		Usuario ret = em.find(Usuario.class, id);
		return ret;
	}
	

}
