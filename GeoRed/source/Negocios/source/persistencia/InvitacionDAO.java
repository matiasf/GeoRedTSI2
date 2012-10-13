package persistencia;

import java.util.List;

@javax.ejb.Local
public interface InvitacionDAO {

	public Invitacion insertar(Invitacion entidad);
	
	public Invitacion modificar(Invitacion entidad);
	
	public Invitacion buscarPorId(int id);
	
	public void borrar(Invitacion entidad);
	
	public void borrar(int id);
	
	public void flush();
	
	public List<Invitacion> getInvitacionesPorUsuario(int idUsuario);

	Invitacion getInvitacionPorContactoRmte(int idContacto, int remitente);
}
