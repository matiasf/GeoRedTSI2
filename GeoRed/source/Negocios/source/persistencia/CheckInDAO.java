package persistencia;

public interface CheckInDAO {
	
	public CheckIn insertar(CheckIn entidad);
	
	public void modificar(CheckIn entidad);
	
	public CheckIn buscarPorId(int id);
	

}
