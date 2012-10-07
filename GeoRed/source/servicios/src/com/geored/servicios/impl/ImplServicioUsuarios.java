package com.geored.servicios.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import negocios.GestionUsuarios;
import persistencia.Invitacion;
import persistencia.Usuario;

import com.geored.servicios.ServicioUsuarios;

@Local
@Stateless
public class ImplServicioUsuarios implements ServicioUsuarios {

	@EJB
	GestionUsuarios gestionUsuarios;
	
	public List<Usuario> getContactos(final int idUsuario) {
		return gestionUsuarios.getContactos(idUsuario);
	}
	
	public Usuario getContacto(final int idUsuario, final int idContacto) {
		return gestionUsuarios.getContacto(idUsuario, idContacto);
	}
	
	public Response invitarContacto(final int idUsuario, final int idContacto) {
		gestionUsuarios.invitarContacto(idUsuario, idContacto);
		return Response.ok().build();
	}
	
	public List<Invitacion> getInvitaciones(final int idUsuario) {
		return gestionUsuarios.getInvitaciones(idUsuario);
	}
	
	public Response aceptarInvitacion(final int idUsuario, final int idContacto) {
		gestionUsuarios.aceptarInvitacion(idUsuario, idContacto);
		return Response.ok().build();
	}

}
