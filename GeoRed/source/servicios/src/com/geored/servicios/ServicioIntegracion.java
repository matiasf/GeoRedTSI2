package com.geored.servicios;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.geored.servicios.json.NotificacionJSON;
import com.geored.servicios.json.PosicionJSON;

@Path("integracion")
public interface ServicioIntegracion {
	
	@POST
	@Path("notificaciones")
	@Produces("application/json")
	List<NotificacionJSON> getNotificaciones(@Context final HttpServletResponse response, final PosicionJSON posicion);

}
