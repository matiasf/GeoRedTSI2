package com.geored.servicios.impl;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import com.geored.servicios.ServicioGCM;
import com.geored.servicios.impl.auth.GestionTokens;
import com.geored.servicios.impl.gcm.GestionDevices;
import com.geored.servicios.json.MensajeJSON;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;

@Local
@Stateless
public class ImplServicioGCM implements ServicioGCM {
	private final Sender sender = new Sender("AIzaSyDQ__cG4ZWQgfDTNjexVWvZzI7CyRdQtpU");
	private final int RETRY_ATTEMPS = 5;
	
	@EJB
	GestionTokens gestionTokens;
	
	@EJB
	GestionDevices gestionDevices;
	
	@Override
	public void registrarDispositivo(final String userToken, final HttpServletResponse response, final String idDispositivo) {
		if (gestionTokens.validarToken(userToken)) {
			response.setStatus(Response.Status.OK.getStatusCode());
			gestionDevices.putDevice(gestionTokens.getIdUsuario(userToken), idDispositivo);
		}
		else {
			response.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
		}
	}

	@Override
	public void desregistrarDispositivo(final String userToken, final HttpServletResponse response) {
		if (gestionTokens.validarToken(userToken)) {
			response.setStatus(Response.Status.OK.getStatusCode());
			gestionDevices.removeDevice(gestionTokens.getIdUsuario(userToken));
		}
		else {
			response.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
		}
	}

	@Override
	public void enviarMensaje(final String userToken, final HttpServletResponse response, final MensajeJSON mensaje) {
		if (gestionTokens.validarToken(userToken)) {
			try {
				response.setStatus(Response.Status.OK.getStatusCode());
				Message message = new Message.Builder().addData("mensaje", mensaje.getMessage()).
						addData("idUsuario", mensaje.getIdUsuario().toString()).build();
				String idDevice = gestionDevices.getDevice(gestionTokens.getIdUsuario(userToken));
				sender.send(message, idDevice, RETRY_ATTEMPS);
			} catch (IOException e) {
				e.printStackTrace();
				response.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
			}
		}
		else {
			response.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
		}
	}

}
