package negocios.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import negocios.GestionUsuarios;
import negocios.excepciones.ContactoYaExiste;
import negocios.excepciones.EntidadNoExiste;
import persistencia.Invitacion;
import persistencia.InvitacionDAO;
import persistencia.Notificacion;
import persistencia.Oferta;
import persistencia.OfertaDAO;
import persistencia.Pago;
import persistencia.PagoDAO;
import persistencia.SitioInteres;
import persistencia.SitioInteresDAO;
import persistencia.Usuario;
import persistencia.UsuarioDAO;
import negocios.GestionUsuarios;
import negocios.excepciones.ContactoYaExiste;
import negocios.excepciones.EntidadNoExiste;

@Stateless
public class GestionUsuariosImpl implements GestionUsuarios {

	@EJB
	private UsuarioDAO usuarioDAO;
	
	@EJB
	private InvitacionDAO invitacionDAO;
	
	@EJB
	private OfertaDAO ofertaDAO;
	
	@EJB
	private PagoDAO pagoDAO;
	
	@EJB
	private SitioInteresDAO sitioInteresDAO;
	
	@Override
	public int checkLogin(String nombre, String password) {
		return usuarioDAO.checkLogin(nombre, password);
	}

	@Override
	public void registrarUsuario(Usuario usuario) {
		usuarioDAO.insertar(usuario);
	}

	@Override
	public void modificarUsuario(Usuario usuario) throws EntidadNoExiste {
		if (!usuarioDAO.existe(usuario.getId())) {
			String msg = "El usuario " + usuario.getId() + "no existe";
			throw new EntidadNoExiste(usuario.getId(), msg);
		}
		usuarioDAO.modificar(usuario);
	}

	@Override
	public List<Usuario> getContactos(int idUsuario) throws EntidadNoExiste {
		if (!usuarioDAO.existe(idUsuario)) {
			String msg = "El usuario " + idUsuario + "no existe";
			throw new EntidadNoExiste(idUsuario, msg);
		}
		return usuarioDAO.obtenerContactos(idUsuario);
	}

	@Override
	public Usuario getContacto(int idUsuario, int idContacto) throws EntidadNoExiste {
		if (!usuarioDAO.existe(idUsuario)) {
			String msg = "El usuario " + idUsuario + "no existe";
			throw new EntidadNoExiste(idUsuario, msg);
		}
		if (!usuarioDAO.existe(idContacto)) {
			String msg = "El usuario " + idContacto + "no existe";
			throw new EntidadNoExiste(idContacto, msg);
		}
		return usuarioDAO.obtenerContacto(idUsuario, idContacto);
	}

	@Override
	public void invitarContacto(int idUsuario, int idContacto) throws EntidadNoExiste, ContactoYaExiste {
		Usuario remitente = usuarioDAO.buscarPorId(idUsuario);
		if (remitente == null) {
			String msg = "El usuario " + idUsuario + "no existe";
			throw new EntidadNoExiste(idUsuario, msg);
		}
		Usuario contacto = usuarioDAO.buscarPorId(idContacto);
		if (contacto == null) {
			String msg = "El usuario " + idContacto + "no existe";
			throw new EntidadNoExiste(idContacto, msg);
		}
		if (usuarioDAO.obtenerContacto(idUsuario, idContacto) != null) {
			String msg = "El usuario " + contacto.getNombre() + 
					" ya es contacto de " + remitente.getNombre();
			throw new ContactoYaExiste(msg);
		}
		Invitacion invitacion = new Invitacion();
		invitacion.setRemitente(remitente);
		contacto.getInvitaciones().add(invitacion);
		invitacionDAO.insertar(invitacion);
	}

	@Override
	public List<Invitacion> getInvitaciones(int idUsuario) throws EntidadNoExiste {
		if (!usuarioDAO.existe(idUsuario)) {
			String msg = "El usuario " + idUsuario + "no existe";
			throw new EntidadNoExiste(idUsuario, msg);
		}
		
		return invitacionDAO.getInvitacionesPorUsuario(idUsuario);
	}

	@Override
	public void aceptarInvitacion(int idUsuario, int idContacto) throws EntidadNoExiste, ContactoYaExiste {
		Usuario remitente = usuarioDAO.buscarPorId(idContacto);
		Usuario contacto = this.getContacto(idUsuario, idContacto);
		if (contacto != null) {
			String msg = "El usuario " + contacto.getNombre() + 
					" ya es contacto de " + remitente.getNombre();
			throw new ContactoYaExiste(msg);
		}
		contacto = usuarioDAO.buscarPorId(idUsuario);
		Invitacion invitacion = invitacionDAO.getInvitacionPorContactoRmte(idUsuario, idContacto);
		if (invitacion == null) {
			EntidadNoExiste e = new EntidadNoExiste();
			e.setMensaje("No existe una invitacion para el usuario " + contacto.getNombre() + " del usuario " + remitente.getNombre());
			throw e;
		}
		remitente.getContactos().add(contacto);
		contacto.getContactos().add(remitente);
		Collection<Invitacion> invitaciones = contacto.getInvitaciones();
		invitaciones.remove(invitacion);
		usuarioDAO.flush();
		invitacionDAO.borrar(invitacion.getId());
	}


	@Override
	public void comprarOferta(int idUsuario, int idOferta, Pago pago)
			throws EntidadNoExiste {
		Usuario usuario = usuarioDAO.buscarPorId(idUsuario);
		if (usuario == null) {
			String msg = "El usuario con id " + idUsuario + " no existe";
			throw new EntidadNoExiste(idUsuario, msg);
		}
		Oferta oferta = ofertaDAO.buscarPorId(idOferta);
		if (oferta == null) {
			String msg = "La oferta con id " + idOferta + " no existe";
			throw new EntidadNoExiste(idOferta, msg);
		}
		Pago pagoIns = pagoDAO.insertar(pago);
		oferta.getPagos().add(pagoIns);
	}

	@Override
	public List<Notificacion> getNotificaciones(final int idUsuario, 
			final float latitud, 
			final float longitud, 
			final float distancia) throws EntidadNoExiste {
		
		if (!usuarioDAO.existe(idUsuario)) {
			String msg = "El usuario " + idUsuario + " no existe";
			throw new EntidadNoExiste(idUsuario, msg);
		}
		
		List<SitioInteres> sitios = sitioInteresDAO.obtenerTodos();
		List<Notificacion> ret = new ArrayList<Notificacion>();
		for (SitioInteres sitio : sitios) {
			if (this.distanciaEntrePuntos(latitud, longitud, sitio.getLatitud(), sitio.getLongitud()) <= distancia) {
				ret.add(sitio);
			}
		}
		return ret;
	}
	
	private float distanciaEntrePuntos(float lat1, float long1, float lat2, float long2) {
		double earthRadius = 6371;
	    double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(long2-long1);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	               Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
	               Math.sin(dLng/2) * Math.sin(dLng/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    double dist = earthRadius * c;

	    return (float) dist;
	}

}
