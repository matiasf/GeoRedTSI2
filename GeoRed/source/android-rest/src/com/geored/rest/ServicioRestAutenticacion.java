package com.geored.rest;

import org.apache.http.HttpResponse;

import com.geored.rest.exception.RestBlowUpException;
import com.geored.rest.exception.UnauthorizedException;
import com.geored.rest.utils.Utils;

public class ServicioRestAutenticacion extends ServicioRest {

	final private static String SERVICIO_REST_AUTENTICACION_URL = SERVICIO_REST_URL
			+ "/autenticar";
	final private static String URL_LOGIN = SERVICIO_REST_AUTENTICACION_URL
			+ "/login";

	public static String login(String nombre, String password) throws RestBlowUpException, UnauthorizedException {
		HttpResponse response = rest(Metodos.POST, URL_LOGIN + "/" + nombre + "/" + password, null, false);
		if (response.getStatusLine().getStatusCode() == OK) {
			String idUsuario;
			try {				
				String sResponse = Utils.getASCIIContentFromEntity(response.getEntity());
				String token = sResponse.split(":")[0];
				idUsuario = sResponse.split(":")[1];
				setSecurityToken(token);
			} catch (Exception e) {
				throw new RestBlowUpException(e.getMessage());
			}
			return idUsuario;
		}
		else if (response.getStatusLine().getStatusCode() == UNAUTHORIZED) {
			throw new UnauthorizedException();
		}
		else {
			throw new RestBlowUpException();
		}
	}

}
