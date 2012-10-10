package com.geored.servicios.impl;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import com.geored.servicios.ServicioAutenticacion;
import com.geored.servicios.impl.auth.GestionTokens;

@Local
@Stateless
public class ImplServicioAutenticacion implements ServicioAutenticacion {
	
	@EJB
	GestionTokens gestionTokens;

	@Override
	public Response login(final String usuario, final String password) {
		gestionTokens.obtenerToken(1);
		return Response.status(Response.Status.OK).entity(gestionTokens.obtenerToken(1)).build();
	}

}
