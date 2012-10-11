package com.geored.servicios;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import persistencia.Invitacion;
import persistencia.Usuario;

@Path("usuarios")
public interface ServicioUsuarios {

	@GET
	@Path("contactos")
	@Produces("application/json")
	public List<Usuario> getContactos(
			@HeaderParam("Security-Token") final String userToken,
			@Context final HttpServletResponse response);

	@GET
	@Path("contactos/{contactId}")
	@Produces("application/json")
	public Usuario getContacto(
			@HeaderParam("Security-Token") final String userToken,
			@Context final HttpServletResponse response,
			@PathParam("idContacto") final int idContacto);

	@POST
	@Path("contactos/{id}")
	public Response invitarContacto(
			@HeaderParam("Security-Token") final String userToken,
			@Context final HttpServletResponse response,
			@PathParam("idContacto") final int idContacto);

	@GET
	@Path("contactos/invitaciones")
	@Produces("application/json")
	public List<Invitacion> getInvitaciones(
			@HeaderParam("Security-Token") final String userToken,
			@Context final HttpServletResponse response);

	@POST
	@Path("contactos/invitaciones/{id}")
	public Response aceptarInvitacion(
			@PathParam("Security-Token") final String userToken,
			@Context final HttpServletResponse response,
			@FormParam("idContacto") final int idContacto);

}
