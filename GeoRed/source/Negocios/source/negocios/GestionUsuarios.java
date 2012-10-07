package negocios;

import java.util.List;

import persistencia.Invitacion;
import persistencia.Notificacion;
import persistencia.Usuario;

public interface GestionUsuarios {
	
	public boolean checkLogin(String nombre, String password);

	public void registrarUsuario(Usuario usuario);
	
	public void modificarUsuario(Usuario usuario);
	
	public List<Usuario> obtenerContactos(int id);
	
	public Usuario obtenerContacto(int idUsuario, int idContacto);
	
	public void invitarContacto(int idUsuario, int idContacto);
	
	public List<Invitacion> obtenerInvitaciones(int idUsuario);
	
	public void aceptarInvitacion(int idUsuario, int idInvitacion);
	
	public List<Notificacion> obtenerNotificaciones(int idUsuario);
}
