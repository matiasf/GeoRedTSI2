package com.geored.servicios.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import negocios.GestionSitioInteres;
import negocios.excepciones.EntidadNoExiste;

import com.geored.servicios.ServicioSitiosInteres;
import com.geored.servicios.impl.auth.GestionTokens;
import com.geored.servicios.json.CheckInJSON;
import com.geored.servicios.json.converters.ConvertidorEntityJSON;

@Local
@Stateless
public class ImplServicioSitiosInteres implements ServicioSitiosInteres {
	
	@EJB
	GestionTokens gestionTokens;
	
	@EJB
	GestionSitioInteres gestionSitioInteres;

	@EJB
	ConvertidorEntityJSON convertidorEntityJSON;

	@Override
	public void hacerCheckIn(final String userToken, final HttpServletResponse response, final Integer idSitioInteres,
			final CheckInJSON checkInJSON) {
		if (gestionTokens.validarToken(userToken)) {
			try {
				response.setStatus(Response.Status.OK.getStatusCode());
				gestionSitioInteres.hacerCheckIn(gestionTokens.getIdUsuario(userToken), idSitioInteres, checkInJSON.getIdImagen(),
						convertidorEntityJSON.convertir(checkInJSON));
			} catch (EntidadNoExiste e) {
				response.setStatus(Response.Status.NOT_FOUND.getStatusCode());
			}
		}
		else {
			response.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
		}
	}
	
	@Override
	public List<CheckInJSON> getCheckIns(final String userToken, final HttpServletResponse response, final Integer idSitioInteres) {
		if (gestionTokens.validarToken(userToken)) {
			response.setStatus(Response.Status.OK.getStatusCode());
			return convertidorEntityJSON.convertBajo(gestionSitioInteres.obtenerCheckInsAmigosLocal(gestionTokens.getIdUsuario(userToken), idSitioInteres));	
		}
		else {
			response.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
		}
		return null;
	}

}
