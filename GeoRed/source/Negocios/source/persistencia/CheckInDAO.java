package persistencia;

import java.util.List;

@javax.ejb.Local
public interface CheckInDAO {
	
	public CheckIn insertar(CheckIn entidad);
	
	public CheckIn modificar(CheckIn entidad);
	
	public CheckIn buscarPorId(int id);
	
	public List<CheckIn> getCheckInAmigos(int idUsuario);
	
	public List<CheckIn> getCheckInAmigosLocal(int idUsuario, int idSitio);
}
