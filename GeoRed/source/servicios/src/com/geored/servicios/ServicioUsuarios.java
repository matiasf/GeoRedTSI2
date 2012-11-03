package com.geored.servicios;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.geored.servicios.json.CategoriaJSON;
import com.geored.servicios.json.InvitacionJSON;
import com.geored.servicios.json.NotificacionJSON;
import com.geored.servicios.json.OfertaJSON;
import com.geored.servicios.json.PagoJSON;
import com.geored.servicios.json.PosicionJSON;
import com.geored.servicios.json.UsuarioJSON;

@Path("usuarios")
public interface ServicioUsuarios {

	@GET
	@Path("contactos/buscar/{query}")
	@Produces("application/json")
	List<UsuarioJSON> buscarContactos(@HeaderParam("Security-Token") final String userToken, 
			@Context final HttpServletResponse response, final String query);
	
	@GET
	@Path("contactos")
	@Produces("application/json")
	List<UsuarioJSON> getContactos(
			@HeaderParam("Security-Token") final String userToken,
			@Context final HttpServletResponse response);

	@GET
	@Path("contactos/{idContacto}")
	@Produces("application/json")
	UsuarioJSON getContacto(
			@HeaderParam("Security-Token") final String userToken,
			@Context final HttpServletResponse response,
			@PathParam("idContacto") final int idContacto);

	@GET
	@Path("invitaciones")
	@Produces("application/json")
	List<InvitacionJSON> getInvitaciones(
			@HeaderParam("Security-Token") final String userToken,
			@Context final HttpServletResponse response);

	@PUT
	@Path("invitaciones/{idContacto}")
	void invitarContacto(
			@HeaderParam("Security-Token") final String userToken,
			@Context final HttpServletResponse response,
			@PathParam("idContacto") final int idContacto);
	
	@POST
	@Path("invitaciones/{idContacto}")
	void aceptarInvitacion(
			@HeaderParam("Security-Token") final String userToken,
			@Context final HttpServletResponse response,
			@PathParam("idContacto") final int idContacto);

	@PUT
	@Path("usuario/{password}")
	void registrarUsuario(@Context final HttpServletResponse response,
			@PathParam("password") final String password, final UsuarioJSON usuarioJSON);

	@POST
	@Path("usuario/{password}")
	void modificarUsuario(@HeaderParam("Security-Token") final String userToken, @Context final HttpServletResponse response,
			@PathParam("password") final String password, final UsuarioJSON usuarioJSON);

	@POST
	@Path("categorias/agregar")
	void agregarCategorias(@HeaderParam("Security-Token") final String userToken, @Context final HttpServletResponse response,
			final List<Integer> categorias);

	@POST
	@Path("categorias/borrar")
	void borrarCategorias(@HeaderParam("Security-Token") final String userToken, @Context final HttpServletResponse response,
			final List<Integer> categorias);
	
	@GET
	@Path("categorias")
	@Produces("application/json")
	List<CategoriaJSON> getCategorias(@HeaderParam("Security-Token") final String userToken, @Context final HttpServletResponse response);
	
	@POST
	@Path("notificaciones")
	@Produces("application/json")
	List<NotificacionJSON> getNotificaciones(@HeaderParam("Security-Token") final String userToken,
			@Context final HttpServletResponse response, final PosicionJSON posicion);

	@POST
	@Path("ofertas/{idOferta}")
	void comprarOferta(@HeaderParam("Security-Token") final String userToken, @Context HttpServletResponse response, 
			@PathParam("{idOferta}") final Integer idOferta, final PagoJSON pago);

	@GET
	@Path("ofertas/{idLocal}")
	List<OfertaJSON> getOfertasLocal(@HeaderParam("Security-Token") final String userToken, @Context final HttpServletResponse response,
			@PathParam("{idLocal}") final Integer idLocal);

}
