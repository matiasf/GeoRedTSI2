package com.geored.servicios.impl;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import negocios.GestionUsuarios;

import com.geored.servicios.ServicioAutenticacion;
import com.geored.servicios.impl.auth.GestionTokens;

@Local
@Stateless
public class ImplServicioAutenticacion implements ServicioAutenticacion {
	
	@EJB
	GestionUsuarios gestionUsuarios;
	
	@EJB
	GestionTokens gestionTokens;

	@Override
	public Response login(final String usuario, final String password) {
		if (gestionUsuarios.checkLogin(usuario, password)) {
			gestionTokens.obtenerToken(1);
			return Response.status(Response.Status.OK).entity(gestionTokens.obtenerToken(1)).build();
		}
		else {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}

}
