package com.geored.servicios;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import com.geored.servicios.json.ImagenJSON;

@Path("imagenes")
public interface ServicioImagenes {

	@GET
	@Path("imagen/{idImagen}")
	@Produces("image/png")
	public Response bajarImagen(@HeaderParam("Security-Token") final String userToken, @Context final HttpServletResponse response,
			@PathParam("idImagen") final Integer idImagen);

	@POST
	@Path("imagen")
	@Produces("application/json")
	public ImagenJSON subirImagen(@HeaderParam("Security-Token") final String userToken,
			@Context final HttpServletResponse response, @Context final HttpServletRequest request);

}
