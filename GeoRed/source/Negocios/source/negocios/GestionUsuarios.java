package negocios;

import java.util.List;

import persistencia.Invitacion;
import persistencia.Notificacion;
import persistencia.Usuario;

public interface GestionUsuarios {

	/**
	 * Verifica si el loguin del usuario es correcto.
	 * 
	 * @param nombre
	 *            Nombre de usuario.
	 * @param password
	 *            Contraseña del usuario.
	 * @return Retorna si es valido o no.
	 */
	public boolean checkLogin(String nombre, String password);

	/**
	 * Se registra un nuevo usuario.
	 * 
	 * @param usuario
	 *            Datos del usuario a registrar.
	 */
	public void registrarUsuario(Usuario usuario);

	/**
	 * Modifica la informacion de un usuario.
	 * 
	 * @param usuario
	 *            Los datos del usuario a modificar.
	 */
	public void modificarUsuario(Usuario usuario);

	/**
	 * Obtiene todos los contactos de un usuario con un id particular.
	 * 
	 * @param idUsuario
	 *            Identificador del usuario.
	 * @return Lista de contactos del usuario.
	 */
	public List<Usuario> getContactos(final int idUsuario);

	/**
	 * Obtiene la informacion de un contacto en particular.
	 * 
	 * @param idUsuario
	 *            Identificador del usuario.
	 * @param idContacto
	 *            Identificador del contacto.
	 * @return El contacto del usuario dado.
	 */
	public Usuario getContacto(final int idUsuario, final int idContacto);

	/**
	 * Invita a un usuario a ser contacto.
	 * 
	 * @param idUsuario
	 *            Identificador del usuario.
	 * @param idContacto
	 *            Identificador del contacto a pedir la invitacion.
	 */
	public void invitarContacto(final int idUsuario, final int idContacto);

	/**
	 * Obtener todas las invitaciones del usuario.
	 * 
	 * @param idUsuario
	 *            Identificador del usuario.
	 * @return Lista de invitaciones del usuario.
	 */
	public List<Invitacion> getInvitaciones(final int idUsuario);

	/**
	 * Aceptar la invitacion de un contacto.
	 * 
	 * @param idUsuario
	 *            Identificador del usuario.
	 * @param idContacto
	 *            Identificador del contacto.
	 */
	public void aceptarInvitacion(final int idUsuario, final int idContacto);

	/**
	 * Obtener las notificaciones del usuario.
	 * 
	 * @param idUsuario
	 *            Identificador del usuario.
	 * @return Lista de notficaciones a mostrar.
	 */
	public List<Notificacion> getNotificaciones(final int idUsuario);
}
