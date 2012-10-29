package com.geored.servicios;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

import com.geored.servicios.json.MensajeJSON;

@Path("gcm")
public interface ServicioGCM {
	
	@POST
	@Path("registrar/{idDispositivo}")
	public void registrarDispositivo(@HeaderParam("Security-Token") final String userToken, @Context final HttpServletResponse response,
			@PathParam("idDispositivo") final String idDispositivo);
	
	@POST
	@Path("desregistrar")
	public void desregistrarDispositivo(@HeaderParam("Security-Token") final String userToken, @Context final HttpServletResponse response);
	
	@POST
	@Path("enviarMensaje")
	public void enviarMensaje(@HeaderParam("Security-Token") final String userToken, @Context final HttpServletResponse response,
			final MensajeJSON mensaje);

}
