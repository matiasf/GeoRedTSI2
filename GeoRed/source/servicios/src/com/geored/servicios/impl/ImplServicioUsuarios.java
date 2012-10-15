package com.geored.servicios.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import negocios.GestionUsuarios;
import negocios.excepciones.ContactoYaExiste;
import negocios.excepciones.EntidadNoExiste;
import persistencia.Usuario;

import com.geored.servicios.ServicioUsuarios;
import com.geored.servicios.impl.auth.ConvertidorEntityJSON;
import com.geored.servicios.impl.auth.GestionTokens;
import com.geored.servicios.json.InvitacionJSON;
import com.geored.servicios.json.NotificacionJSON;
import com.geored.servicios.json.PosicionJSON;
import com.geored.servicios.json.UsuarioJSON;

@Local
@Stateless
public class ImplServicioUsuarios implements ServicioUsuarios {

	@EJB
	GestionUsuarios gestionUsuarios;

	@EJB
	GestionTokens gestionTokens;
	
	@EJB
	ConvertidorEntityJSON convertidorEntityJSON;
	
	@Override
	public List<UsuarioJSON> buscarContactos(final String userToken, final HttpServletResponse response, final String query) {
		if (gestionTokens.validarToken(userToken)) {
			response.setStatus(Response.Status.OK.getStatusCode());
			return convertidorEntityJSON.convert(gestionUsuarios.buscarUsuario(query));
		}
		else {
			response.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
		}
		return null;
	}

	@Override
	public List<UsuarioJSON> getContactos(final String userToken, final HttpServletResponse response) {
		if (gestionTokens.validarToken(userToken)) {
			try {
				response.setStatus(Response.Status.OK.getStatusCode());
				return convertidorEntityJSON.convert(gestionUsuarios.getContactos(gestionTokens.getIdUsuario(userToken)));
			} catch (EntidadNoExiste e) {
				response.setStatus(Response.Status.NOT_FOUND.getStatusCode());
			}
		}
		else {
			response.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
		}
		return null;
	}

	@Override
	public UsuarioJSON getContacto(final String userToken, final HttpServletResponse response, final int idContacto) {
		if (gestionTokens.validarToken(userToken)) {
			response.setStatus(Response.Status.OK.getStatusCode());
			try {
				return convertidorEntityJSON.convertir(gestionUsuarios.getContacto(gestionTokens.getIdUsuario(userToken), idContacto));
			} catch (EntidadNoExiste e) {
				response.setStatus(Response.Status.NOT_FOUND.getStatusCode());
			}
		}
		else {
			response.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
		}		
		return null;
	}

	@Override
	public void invitarContacto(final String userToken, final HttpServletResponse response, final int idContacto) {
		if (gestionTokens.validarToken(userToken)) {
			try {
				response.setStatus(Response.Status.OK.getStatusCode());
				gestionUsuarios.invitarContacto(gestionTokens.getIdUsuario(userToken), idContacto);
			} catch (EntidadNoExiste e) {
				response.setStatus(Response.Status.NOT_FOUND.getStatusCode());
			} catch (ContactoYaExiste e) {
				response.setStatus(Response.Status.CONFLICT.getStatusCode());
			}
		}
		else {
			response.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
		}
	}

	@Override
	public List<InvitacionJSON> getInvitaciones(final String userToken, final HttpServletResponse response) {
		if (gestionTokens.validarToken(userToken)) {
			response.setStatus(Response.Status.OK.getStatusCode());
			try {
				return convertidorEntityJSON.convert(gestionUsuarios.getInvitaciones(gestionTokens.getIdUsuario(userToken)));
			} catch (EntidadNoExiste e) {
				response.setStatus(Response.Status.NOT_FOUND.getStatusCode());
			}
		}
		response.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
		return null;
	}

	@Override
	public void aceptarInvitacion(final String userToken, final HttpServletResponse response, final int idContacto) {
		if (gestionTokens.validarToken(userToken)) {
			try {
				response.setStatus(Response.Status.OK.getStatusCode());
				gestionUsuarios.aceptarInvitacion(gestionTokens.getIdUsuario(userToken), idContacto);
			} catch (EntidadNoExiste e) {
				response.setStatus(Response.Status.NOT_FOUND.getStatusCode());
			} catch (ContactoYaExiste e) {
				response.setStatus(Response.Status.CONFLICT.getStatusCode());
			}
		}
		else {
			response.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
		}
	}
	
	@Override
	public void registrarUsuario(final HttpServletResponse response, final String password, final UsuarioJSON usuarioJSON) {
		response.setStatus(Response.Status.OK.getStatusCode());
		Usuario usuario = convertidorEntityJSON.convertir(usuarioJSON);
		usuario.setPassword(password);
		gestionUsuarios.registrarUsuario(usuario);
	}
	
	@Override
	public void modificarUsuario(final String userToken, final HttpServletResponse response, 
			final String password, final UsuarioJSON usuarioJSON) {
		if(gestionTokens.validarToken(userToken)) {
			response.setStatus(Response.Status.OK.getStatusCode());
			Usuario usuario = convertidorEntityJSON.convertir(usuarioJSON);
			usuario.setPassword(password);
			try {
				gestionUsuarios.modificarUsuario(usuario);
			} catch (EntidadNoExiste e) {
				response.setStatus(Response.Status.NOT_FOUND.getStatusCode());
			}
		}
		else {
			response.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
		}		
	}
	
	@Override
	public List<NotificacionJSON> getNotificaciones(final String userToken, final HttpServletResponse response,
			final PosicionJSON posicion) {
		if (gestionTokens.validarToken(userToken)) {
			response.setStatus(Response.Status.OK.getStatusCode());
			try {
				return convertidorEntityJSON.convert(gestionUsuarios.getNotificaciones(gestionTokens.getIdUsuario(userToken),
						posicion.getLatitud(), posicion.getLongitud(), posicion.getDistancia()));
			} catch (EntidadNoExiste e) {
				response.setStatus(Response.Status.NOT_FOUND.getStatusCode());
			}
		}
		else {
			response.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
		}
		return null;
	}

}
