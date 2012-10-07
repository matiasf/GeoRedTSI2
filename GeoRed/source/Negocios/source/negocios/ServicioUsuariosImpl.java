package negocios;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import persistencia.Usuario;
import persistencia.UsuarioDAO;

@Stateless
public class ServicioUsuariosImpl {
	
	@EJB
	private UsuarioDAO usuarioDAO;
	
	public List<Usuario> obtenerTodos() {
		return usuarioDAO.obtenerTodos();
	}
	
}
