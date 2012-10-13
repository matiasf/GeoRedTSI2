package negocios.impl;

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
import persistencia.Usuario;
import persistencia.UsuarioDAO;

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
	
	
	
	@Override
	public boolean checkLogin(String nombre, String password) {
		return usuarioDAO.checkLogin(nombre, password);
	}

	@Override
	public void registrarUsuario(Usuario usuario) {
		usuarioDAO.insertar(usuario);
	}

	@Override
	public void modificarUsuario(Usuario usuario) {
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
		if (usuarioDAO.existe(idUsuario)) {
			String msg = "El usuario " + idUsuario + "no existe";
			throw new EntidadNoExiste(idUsuario, msg);
		}
		if (usuarioDAO.existe(idContacto)) {
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
	public List<Invitacion> getInvitaciones(int idUsuario) {
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
		contacto = usuarioDAO.buscarPorId(idContacto);
		Invitacion invitacion = invitacionDAO.getInvitacionPorContactoRmte(idContacto, idUsuario);
		if (invitacion == null) {
			EntidadNoExiste e = new EntidadNoExiste();
			e.setMensaje("No existe una para el usuario " + contacto.getNombre() + " del usuario " + remitente.getNombre());
			throw e;
		}
		remitente.getContactos().add(contacto);
		contacto.getContactos().add(remitente);
		contacto.getInvitaciones().remove(invitacion);
		invitacionDAO.borrar(invitacion);
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
	public List<Notificacion> getNotificaciones(int idUsuario) {
		// TODO Auto-generated method stub
		return null;
	}

}
