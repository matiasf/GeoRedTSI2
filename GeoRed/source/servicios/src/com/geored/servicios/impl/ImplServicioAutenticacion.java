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
		int idUsuario = gestionUsuarios.checkLogin(usuario, password);
		if (idUsuario >= 0) {
			gestionTokens.obtenerToken(idUsuario);
			return Response.status(Response.Status.OK).entity(gestionTokens.obtenerToken(idUsuario) + ":" + idUsuario).build();
		}
		else {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}

}
