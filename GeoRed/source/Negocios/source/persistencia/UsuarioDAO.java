package persistencia;

import java.util.List;

import javax.ejb.Local;

@Local
public interface UsuarioDAO {

	public Usuario insertar(Usuario entidad);
	
	public Usuario modificar(Usuario entidad);
	
	public Usuario buscarPorId(int id);
	
	public List<Usuario> obtenerTodos();
	
	public boolean checkLogin(String nombre, String password);
	
	public Usuario buscarPorNombre(String nombre);
	
	public List<Usuario> obtenerContactos(int idUsuario);
	
	public Usuario obtenerContacto(int idUsuario, int idContacto);
	
	public boolean existe(int id);
}
