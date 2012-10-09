package persistencia;

@javax.ejb.Local
public interface CheckInDAO {
	
	public CheckIn insertar(CheckIn entidad);
	
	public CheckIn modificar(CheckIn entidad);
	
	public CheckIn buscarPorId(int id);
	

}
