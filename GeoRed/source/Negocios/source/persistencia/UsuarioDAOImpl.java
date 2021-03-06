package persistencia;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

@Stateless
public class UsuarioDAOImpl extends BaseDAO<Usuario> implements UsuarioDAO {

	private static final String findAllQuery = "SELECT u FROM Usuario u";
	
	private static final String checkLoginQuery = "SELECT u.id " +
			"FROM Usuario u " +
			"WHERE u.nombre = :userName " +
			"AND u.password = :password " +
			"AND u.facebookUser = 0";
	
	private static final String buscarUsuarioFacebook = "SELECT u.id FROM Usuario u " +
			"WHERE u.nombre = :nombre " +
			"AND u.facebookUser = 1";
	
	private static final String obtenerContactosQuery = "SELECT u1 FROM Usuario u2, Usuario u1 " +
			"WHERE u2.id = :id " +
			"AND u1 MEMBER OF u2.contactos";
	
	private static final String obtenerContactoQuery = "SELECT u1 FROM Usuario u2, Usuario u1 " +
			"WHERE u2.id = :id " +
			"AND u2.id = :idContacto " +
			"AND u1 MEMBER OF u2.contactos"; 
	
	private static final String buscarUsuariosQuery = "SELECT u FROM Usuario u " +
			"WHERE u.nombre LIKE :nombre";

	private static final String obtenerCategoriaQuery = "SELECT c FROM Usuario u JOIN u.categorias c " +
			"WHERE u.id = :idUsuario AND " +
			"c.id = :idCategoria";
	
	
	@Override
	public Usuario buscarPorId(int id) {
		Usuario ret = em.find(Usuario.class, id);
		return ret;
	}

	@Override
	public List<Usuario> obtenerTodos() {
		TypedQuery<Usuario> ret = em.createQuery(
				findAllQuery, Usuario.class);
		return ret.getResultList();
	}

	@Override
	public int checkLogin(String nombre, String password) {
		TypedQuery<Integer> query = em.createQuery(checkLoginQuery, Integer.class);
		query.setParameter("userName", nombre);
		query.setParameter("password", password);
		List<Integer> resultado = query.getResultList();
		int ret = -1;
		if (!resultado.isEmpty() && (resultado.size() == 1))
		{ 
			ret = resultado.get(0);
		}
		return ret;
	}
	
	@Override
	public int checkLoginUsuarioFacebook(String nombre) {
		TypedQuery<Integer> query = em.createQuery(buscarUsuarioFacebook, Integer.class);
		query.setParameter("nombre", nombre);
		List<Integer> resultado = query.getResultList();
		int ret = -1;
		if (!resultado.isEmpty()) {
			ret = resultado.get(0);
		}
		return ret;
	}
	
	@Override 
	public List<Usuario> obtenerContactos(int idUsuario) {
		TypedQuery<Usuario> query = em.createQuery(obtenerContactosQuery, Usuario.class);
		query.setParameter("id", idUsuario);
		return query.getResultList();
	}

	@Override
	public Usuario obtenerContacto(int idUsuario, int idContacto) {
		TypedQuery<Usuario> query = em.createQuery(obtenerContactoQuery, Usuario.class);
		query.setParameter("id", idUsuario);
		query.setParameter("idContacto", idContacto);
		List<Usuario> contactos = query.getResultList();
		Usuario ret = null;
		if (!contactos.isEmpty()) {
			ret = contactos.get(0);
		}
		return ret;
	}

	@Override
	public List<Usuario> buscarUsuarios(String nombre) {
		TypedQuery<Usuario> query = em.createQuery(buscarUsuariosQuery, Usuario.class);
		nombre = nombre + "%";
		query.setParameter("nombre", nombre);
		return query.getResultList(); 
	}

	@Override
	public Categoria obtenerCategoria(int idUsuario, int idCategoria) {
		TypedQuery<Categoria> query = em.createQuery(obtenerCategoriaQuery, Categoria.class);
		query.setParameter("idUsuario", idUsuario);
		query.setParameter("idCategoria", idCategoria);
		List<Categoria> categorias = query.getResultList();
		Categoria ret = null;
		if (!categorias.isEmpty()) {
			ret = categorias.get(0);
		}
		return ret;
	}
	
	
	
}
