package com.geored.servicios;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("autenticar")
public interface ServicioAutenticacion {

	@POST
	@Path("login/{usuario}/{password}")
	public Response login(@PathParam("usuario") String usuario,
			@PathParam("password") String password);
	
	@POST
	@Path("login/{access-token}")
	public Response loginFacebook(@PathParam("access-token") String accessToken);
	
}
