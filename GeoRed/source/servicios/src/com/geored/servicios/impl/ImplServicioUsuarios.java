package com.geored.servicios.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.geored.servicios.ServicioUsuarios;
import com.geored.servicios.testdata.Invitacion;
import com.geored.servicios.testdata.Usuario;
import com.geored.servicios.testdata.UsuarioJSON;

@Local
@Stateless
public class ImplServicioUsuarios implements ServicioUsuarios {

	final List<Usuario> contactos;
	final List<Invitacion> invitaciones;
	
	public ImplServicioUsuarios() {
		contactos = new ArrayList<Usuario>();
		Usuario tmpContacto = new Usuario();
		tmpContacto.setId("1");
		tmpContacto.setNombre("Aragorn");
		contactos.add(tmpContacto);
		tmpContacto = new Usuario();
		tmpContacto.setId("2");
		tmpContacto.setNombre("Boromir");
		contactos.add(tmpContacto);
		invitaciones = new ArrayList<Invitacion>();
		Invitacion tmpInvitacion = new Invitacion();
		tmpInvitacion.setId("1");
		tmpInvitacion.setUsuario(tmpContacto);
		invitaciones.add(tmpInvitacion);
	}
	
	public List<Usuario> getContactos() {
		return contactos;
	}
	
	public UsuarioJSON getContacto(final String id) {
		for (Usuario contacto : contactos) {
			if (contacto.getId().equals(id)) {
				UsuarioJSON usuarioJson = new UsuarioJSON();
				usuarioJson.setUsuario(contacto);
				return usuarioJson;
			}
		}
		throw new WebApplicationException(Response.Status.NOT_FOUND);
	}
	
	public Response invitarContactos(final String id) {
		return Response.ok().build();
	}
	
	public List<Invitacion> getInvitaciones() {
		return invitaciones;
	}
	
	public Response procesarInvitacion(final String id, final Boolean aceptar) {
		return Response.ok().build();
	}

}
