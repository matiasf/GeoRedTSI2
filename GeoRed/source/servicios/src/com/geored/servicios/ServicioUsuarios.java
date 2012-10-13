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

import com.geored.servicios.json.InvitacionJSON;
import com.geored.servicios.json.UsuarioJSON;

@Path("usuarios")
public interface ServicioUsuarios {

	@GET
	@Path("contactos")
	@Produces("application/json")
	public List<UsuarioJSON> getContactos(
			@HeaderParam("Security-Token") final String userToken,
			@Context final HttpServletResponse response);

	@GET
	@Path("contactos/{contactId}")
	@Produces("application/json")
	public UsuarioJSON getContacto(
			@HeaderParam("Security-Token") final String userToken,
			@Context final HttpServletResponse response,
			@PathParam("idContacto") final int idContacto);

	@POST
	@Path("contactos/{id}")
	public void invitarContacto(
			@HeaderParam("Security-Token") final String userToken,
			@Context final HttpServletResponse response,
			@PathParam("idContacto") final int idContacto);

	@GET
	@Path("contactos/invitaciones")
	@Produces("application/json")
	public List<InvitacionJSON> getInvitaciones(
			@HeaderParam("Security-Token") final String userToken,
			@Context final HttpServletResponse response);

	@POST
	@Path("contactos/invitaciones/{id}")
	public void aceptarInvitacion(
			@HeaderParam("Security-Token") final String userToken,
			@Context final HttpServletResponse response,
			@FormParam("idContacto") final int idContacto);

}
