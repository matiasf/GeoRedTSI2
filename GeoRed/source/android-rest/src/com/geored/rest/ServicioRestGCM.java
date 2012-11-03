package com.geored.rest;

import org.apache.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geored.rest.data.Mensaje;
import com.geored.rest.exception.NotFoundException;
import com.geored.rest.exception.RestBlowUpException;
import com.geored.rest.exception.UnauthorizedException;

public class ServicioRestGCM extends ServicioRest {
	
	final private static String SERVICIO_REST_GCM_URL = SERVICIO_REST_URL
			+ "/gcm";
	final private static String URL_REGISTRAR = SERVICIO_REST_GCM_URL
			+ "/registrar";
	final private static String URL_DESREGISTRAR = SERVICIO_REST_GCM_URL
			+ "/desregistrar";
	final private static String URL_ENVIAR_MENSAJE = SERVICIO_REST_GCM_URL
			+ "/enviarMensaje";
	
	public static void registrar(final String idDispositivo) throws RestBlowUpException, UnauthorizedException {
		HttpResponse response = rest(Metodos.POST, URL_REGISTRAR + "/" + idDispositivo);
		if (response.getStatusLine().getStatusCode() == OK || response.getStatusLine().getStatusCode() == NOT_CONTENT) {
			return;
		}
		else if (response.getStatusLine().getStatusCode() == UNAUTHORIZED) {
			throw new UnauthorizedException();
		}
		else {
			throw new RestBlowUpException();
		}
	}
	
	public static void desregistrar() throws RestBlowUpException, UnauthorizedException {
		HttpResponse response = rest(Metodos.POST, URL_DESREGISTRAR);
		if (response.getStatusLine().getStatusCode() == OK || response.getStatusLine().getStatusCode() == NOT_CONTENT) {
			return;
		}
		else if (response.getStatusLine().getStatusCode() == UNAUTHORIZED) {
			throw new UnauthorizedException();
		}
		else {
			throw new RestBlowUpException();
		}
	}

	public static void enviarMensaje(final Mensaje mensaje) throws RestBlowUpException, UnauthorizedException, NotFoundException {
		ObjectMapper mapper = new ObjectMapper();
		HttpResponse response;
		try {
			response = rest(Metodos.POST, URL_ENVIAR_MENSAJE, mapper.writeValueAsString(mensaje));
		} catch (Exception e) {
			throw new RestBlowUpException(e.getMessage());
		}
		if (response.getStatusLine().getStatusCode() == OK || response.getStatusLine().getStatusCode() == NOT_CONTENT) {
			return;
		}
		else if (response.getStatusLine().getStatusCode() == UNAUTHORIZED) {
			throw new UnauthorizedException();
		}
		else if (response.getStatusLine().getStatusCode() == NOT_FOUND) {
			throw new NotFoundException();
		}
		else {
			throw new RestBlowUpException();
		}
	}

}
