package persistencia;

public interface InvitacionDAO {

	public Invitacion insertar(Invitacion entidad);
	
	public void modificar(Invitacion entidad);
	
	public Invitacion buscarPorId(int id);
}
