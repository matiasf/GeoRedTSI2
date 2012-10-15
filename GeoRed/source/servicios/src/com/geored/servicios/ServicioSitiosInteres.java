package com.geored.servicios;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

import com.geored.servicios.json.CheckInJSON;

@Path("sitios-interes")
public interface ServicioSitiosInteres {

	@POST
	@Path("checkin/{idSitioInteres}")
	void hacerCheckIn(@HeaderParam("Security-Token") final String userToken, 
			@Context final HttpServletResponse response, @PathParam("idSitioInteres") final Integer idSitioInteres,
			final CheckInJSON checkInJSON);

}
