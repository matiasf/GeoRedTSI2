package com.geored.servicios.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import negocios.GestionUsuarios;
import persistencia.Invitacion;
import persistencia.Usuario;

import com.geored.servicios.ServicioUsuarios;
import com.geored.servicios.impl.auth.GestionTokens;

@Local
@Stateless
public class ImplServicioUsuarios implements ServicioUsuarios {

	@EJB
	GestionUsuarios gestionUsuarios;

	@EJB
	GestionTokens gestionTokens;

	public List<Usuario> getContactos(final String userToken, final HttpServletResponse response) {
		if (gestionTokens.validarToken(userToken)) {
			return gestionUsuarios.getContactos(gestionTokens.getIdUsuario(userToken));
		}
		response.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
		return null;
	}

	public Usuario getContacto(final String userToken, final HttpServletResponse response, final int idContacto) {
		if (gestionTokens.validarToken(userToken)) {
			return gestionUsuarios.getContacto(gestionTokens.getIdUsuario(userToken), idContacto);
		}
		response.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
		return null;
	}

	public Response invitarContacto(final String userToken, final HttpServletResponse response, final int idContacto) {
		if (gestionTokens.validarToken(userToken)) {
			return gestionUsuarios.invitarContacto(gestionTokens.getIdUsuario(userToken), idContacto);
		}
		response.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
		return null;
	}

	public List<Invitacion> getInvitaciones(final String userToken, final HttpServletResponse response) {
		if (gestionTokens.validarToken(userToken)) {
			return gestionUsuarios.getInvitaciones(gestionTokens.getIdUsuario(userToken));
		}
		response.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
		return null;
	}

	public Response aceptarInvitacion(final String userToken, final HttpServletResponse response, final int idContacto) {
		if (gestionTokens.validarToken(userToken)) {
			return gestionUsuarios.aceptarInvitacion(gestionTokens.getIdUsuario(userToken), idContacto);
		}
		response.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
		return null;
	}

}
