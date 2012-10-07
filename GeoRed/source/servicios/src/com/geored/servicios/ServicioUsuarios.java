package com.geored.servicios;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.geored.servicios.testdata.Invitacion;
import com.geored.servicios.testdata.Usuario;

@Path("usuarios")
public interface ServicioUsuarios {
	
	@GET
	@Path("contactos")
	@Produces("application/json")
	public List<Usuario> getContactos();
	
	@GET
	@Path("contactos/{id}")
	@Produces("application/json")
	public Usuario getContacto(@PathParam("id") final String id);
	
	@POST
	@Path("contactos/{id}")
	public Response invitarContactos(@PathParam("id") final String id);
	
	@GET
	@Path("contactos/invitaciones")
	@Produces("application/json")
	public List<Invitacion> getInvitaciones();
	
	@POST
	@Path("contactos/invitaciones/{id}")
	public Response procesarInvitacion(@PathParam("id") final String id, @FormParam("aceptar") final Boolean aceptar) ;

}
