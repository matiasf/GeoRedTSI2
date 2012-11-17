package com.geored.servicios;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.geored.servicios.json.OfertasIntegracionJSON;
import com.geored.servicios.json.SitiosInteresIntegracionJSON;

@Path("integracion")
public interface ServicioIntegracion {
	
	@GET
	@Path("obtenerSitiosInteresIntegracion/{latitud}/{longitud}/{radio}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<SitiosInteresIntegracionJSON> getSitiosInteresIntegracion(@PathParam("latitud") Float latitud,
			@PathParam("longitud") Float longitud, @PathParam("radio") Float radio);

	@GET
	@Path("obtenerOfertasIntegracion/{latitud}/{longitud}/{radio}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<OfertasIntegracionJSON> getOfertasIntegracion(@PathParam("latitud") Float latitud, @PathParam("longitud") Float longitud,
			@PathParam("radio") Float radio);
	
}
