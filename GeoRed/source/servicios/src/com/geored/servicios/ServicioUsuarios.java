package com.geored.servicios;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import persistencia.Invitacion;
import persistencia.Usuario;

@Path("usuarios")
public interface ServicioUsuarios {

	@GET
	@Path("contactos")
	@Produces("application/json")
	public List<Usuario> getContactos(@HeaderParam("idUsuario") final int idUsuario);

	@GET
	@Path("contactos/{contactId}")
	@Produces("application/json")
	public Usuario getContacto(@HeaderParam("idUsuario") final int idUsuario,
			@PathParam("idContacto") final int idContacto);

	@POST
	@Path("contactos/{id}")
	public Response invitarContacto(@HeaderParam("idUsuario") final int idUsuario,
			@PathParam("idContacto") final int idContacto);

	@GET
	@Path("contactos/invitaciones")
	@Produces("application/json")
	public List<Invitacion> getInvitaciones(@HeaderParam("idUsuario") final int idUsuario);

	@POST
	@Path("contactos/invitaciones/{id}")
	public Response aceptarInvitacion(@PathParam("idUsuario") final int idUsuario,
			@FormParam("idContacto") final int idContacto);

}
