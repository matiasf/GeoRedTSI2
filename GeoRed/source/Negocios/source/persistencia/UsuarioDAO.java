package persistencia;

import java.util.List;

import javax.ejb.Local;

@Local
public interface UsuarioDAO {

	public Usuario insertar(Usuario entidad);
	
	public void modificar(Usuario entidad);
	
	public Usuario buscarPorId(int id);
	
	public List<Usuario> obtenerTodos();
}
