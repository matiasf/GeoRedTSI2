package persistencia;

import java.util.List;

@javax.ejb.Local
public interface InvitacionDAO {

	public Invitacion insertar(Invitacion entidad);
	
	public Invitacion modificar(Invitacion entidad);
	
	public Invitacion buscarPorId(int id);
	
	public void borrar(Invitacion entidad);
	
	public List<Invitacion> getInvitacionesPorUsuario(int idUsuario);

	Invitacion getInvitacionPorContactoRmte(int idContacto, int remitente);
}
