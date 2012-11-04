package com.geored.servicios;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.geored.servicios.json.CheckInJSON;

@Path("sitios-interes")
public interface ServicioSitiosInteres {

	@POST
	@Path("checkin/{idSitioInteres}")
	public void hacerCheckIn(@HeaderParam("Security-Token") final String userToken, 
			@Context final HttpServletResponse response, @PathParam("idSitioInteres") final Integer idSitioInteres,
			final CheckInJSON checkInJSON);

	@GET
	@Path("checkin/{idSitioInteres}")
	@Produces("application/json")
	public List<CheckInJSON> getCheckIns(@HeaderParam("Security-Token") final String userToken, @Context final HttpServletResponse response,
			@PathParam("idSitioInteres") final Integer idSitioInteres);

}
