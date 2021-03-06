package negocios;

import java.util.Collection;
import java.util.List;

import javax.ejb.Local;

import negocios.excepciones.ContactoYaExiste;
import negocios.excepciones.EntidadNoExiste;

import persistencia.Categoria;
import persistencia.Imagen;
import persistencia.Invitacion;
import persistencia.Notificacion;
import persistencia.Oferta;
import persistencia.Pago;
import persistencia.SitioInteres;
import persistencia.Usuario;

@Local
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
	public int checkLogin(String nombre, String password);

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
	public void modificarUsuario(Usuario usuario) throws EntidadNoExiste;

	/* Funciones Android */

	/**
	 * Obtiene todos los contactos de un usuario con un id particular.
	 * 
	 * @param idUsuario
	 *            Identificador del usuario.
	 * @return Lista de contactos del usuario.
	 * @throws EntidadNoExiste 
	 * 		Si no existe el usuario se lanza esta excepcion
	 */
	public List<Usuario> getContactos(final int idUsuario) throws EntidadNoExiste;

	/**
	 * Obtiene la informacion de un contacto en particular.
	 * 
	 * @param idUsuario
	 *            Identificador del usuario.
	 * @param idContacto
	 *            Identificador del contacto.
	 * @return El contacto del usuario dado.
	 * @throws EntidadNoExiste 
	 * 		Si alguno de los usuario no existe se lanza esta excepcion
	 */
	public Usuario getContacto(final int idUsuario, final int idContacto) throws EntidadNoExiste;

	/**
	 * Invita a un usuario a ser contacto.
	 * 
	 * @param idUsuario
	 *            Identificador del usuario.
	 * @param idContacto
	 *            Identificador del contacto a pedir la invitacion.
	 * @throws EntidadNoExiste
	 * 		Si no existe alguno de los usuarios lanza esta excepcion 
	 * @throws ContactoYaExiste 
	 * 		Si el usuario idUsuario ya tiene como contacto a el usuairo idContacto se lanza esta excepcion
	 */
	public void invitarContacto(final int idUsuario, final int idContacto) throws EntidadNoExiste, ContactoYaExiste;

	/**
	 * Obtener todas las invitaciones del usuario.
	 * 
	 * @param idUsuario
	 *            Identificador del usuario.
	 * @return Lista de invitaciones del usuario.
	 */
	public List<Invitacion> getInvitaciones(final int idUsuario) throws EntidadNoExiste;

	/**
	 * Aceptar la invitacion de un contacto.
	 * 
	 * @param idUsuario
	 *            Identificador del usuario.
	 * @param idContacto
	 *            Identificador del contacto.
	 * @throws EntidadNoExiste 
	 * 		Si alguno de los usuario no existe se lanza esta excepcion
	 * @throws ContactoYaExiste
	 * 		Si los usuarios ya son contactos se lanza esta excepcion 
	 */
	public void aceptarInvitacion(final int idUsuario, final int idContacto) throws EntidadNoExiste, ContactoYaExiste;

	/**
	 * Obtener las notificaciones del usuario.
	 * 
	 * @param idUsuario
	 *            Identificador del usuario.
	 * @return Lista de notficaciones a mostrar.
	 */
	public List<Notificacion> getNotificaciones(final int idUsuario, final double latitud, final double longitud, final double distancia) throws EntidadNoExiste;

	/**
	 * Permite al usuario realizar una compra de una ofera
	 * @param idUsuario
	 * 		Identificador del usuario
	 * @param idOferta
	 * 		Identificado de la oferta
	 * @param pago
	 * 		El pago realizado por el usuario	
	 * @throws EntidadNoExiste
	 * 		En caso de que no exista el usuario o la oferta se lanza esta excepcion
	 */
	public void comprarOferta(int idUsuario, int idOferta, Pago pago) throws EntidadNoExiste;
	
	/**
	 * Busca usuarios
	 * @param nombre 
	 * El nombre o comienzo del nombre
	 * @return
	 * Los usuarios cuyo nombre comienzan con el parametro nombre
	 */
	public List<Usuario> buscarUsuario(String nombre);
	
	/**
	 * Agrega categorias al usuario
	 * @param idUsuario usuario al que se le agregan las categorias
	 * @param idCategorias los id's categorias a agregar
	 */
	public void agregarCategorias(int idUsuario, Collection<Integer> idCategorias);
	
	/**
	 * Borra categorias al usuario
	 * @param idUsuario usuario al que se le borran las categorias
	 * @param idCategorias los id's categorias a borrar
	 */
	public void borrarCategorias(int idUsuario, Collection<Integer> idCategorias);
	
	/**
	 * Obtiene una imagen a partir del id
	 * @param id Id de la imagen
	 * @return La imagen que se busca
	 */
	public Imagen obtenerImagen(int id);
	
	/**
	 * Obtiene todas las categorias del sistema
	 * @return Las categorias del sistema
	 */
	public List<Categoria> obtenerCategorias();
	
	/**
	 * Funcion anolaga a checkLogin pero para usuarios de facebook
	 * @param nombre
	 * @return En caso de que exista el usuario devuelve el id si no devuelve -1
	 */
	public int checkLoginUsuarioFacebook(String nombre);
	

	/**
	 * Da de alta una imagen en el sistema
	 * @param imagen la imagen a dar de alta
	 * @return el id de la imagen
	 */
	public int altaImagen(Imagen imagen);
	
	/**
	 * Obtiene las ofertas de un local para un usuario
	 * @param idLocal el local al que se le piden las ofertas
	 * @param idUsuario el usuario que esta buscando las oferas
	 * @return Las ofertas que pertencen al local y compartenn una categoria con usuario
	 */
	public List<Oferta> obtenerOfertasLocalUsuario(int idLocal, int idUsuario);
	
	/**
	 * Obtiene un usuario
	 * @param idUsuario Id del usuario
	 * @return el usuario que tiene el id en cuestion
	 */
	public Usuario obtenerUsario(int idUsuario);
	
	public List<SitioInteres> getSitioInteresIntegracion(double latitud, double longitud, double radio);
	
	public List<persistencia.Local> getOfertasIntegracion(double latitud, double longitud,double radio);
}
