package com.geored.servicios.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import negocios.GestionUsuarios;

import com.geored.servicios.ServicioIntegracion;
import com.geored.servicios.json.NotificacionJSON;
import com.geored.servicios.json.PosicionJSON;
import com.geored.servicios.json.converters.ConvertidorEntityJSON;

public class ImplServicioIntegracion implements ServicioIntegracion {
	
	@EJB
	GestionUsuarios gestionUsuarios;
	
	@EJB
	ConvertidorEntityJSON convertidorEntityJSON;
	
	@Override
	public List<NotificacionJSON> getNotificaciones(HttpServletResponse response, PosicionJSON posicion) {
		response.setStatus(Response.Status.OK.getStatusCode());
//		try {
			//return convertidorEntityJSON.convert(gestionUsuarios.getNotificaciones(posicion.getLatitud(), 
			//		posicion.getLongitud(), posicion.getDistancia()));
	//	} catch (EntidadNoExiste e) {
		//	response.setStatus(Response.Status.NOT_FOUND.getStatusCode());
		//}
		return null;
	}

}
