package persistencia;

public interface UsuarioDAO {

	public Usuario insertar(Usuario entidad);
	
	public void modificar(Usuario entidad);
	
	public Usuario buscarPorId(int id);
}
