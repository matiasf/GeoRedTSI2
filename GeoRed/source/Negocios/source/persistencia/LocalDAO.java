package persistencia;

public interface LocalDAO {
	
	public Local insertar(Local entidad);
	
	public void modificar(Local entidad);
	
	public Local buscarPorId(int id);

}
