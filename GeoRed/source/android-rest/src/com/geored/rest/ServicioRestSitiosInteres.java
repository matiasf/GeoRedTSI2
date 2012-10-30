package com.geored.rest;

import org.apache.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geored.rest.data.CheckIn;
import com.geored.rest.exception.NotFoundException;
import com.geored.rest.exception.RestBlowUpException;
import com.geored.rest.exception.UnauthorizedException;

public class ServicioRestSitiosInteres extends ServicioRest {
	
	final private static String SERVICIO_REST_SITIOINTERES_URL = SERVICIO_REST_URL
			+ "/sitios-interes";
	final private static String URL_CHECKIN = SERVICIO_REST_SITIOINTERES_URL
			+ "/checkin";
	
	public static void hacerCheckIn(String idSitioInteres, CheckIn checkin) throws RestBlowUpException, NotFoundException,
		UnauthorizedException {
		ObjectMapper mapper = new ObjectMapper();
		HttpResponse response;
		try {
			response = rest(Metodos.POST, URL_CHECKIN + "/" + idSitioInteres, mapper.writeValueAsString(checkin));
		} catch (Exception e) {
			throw new RestBlowUpException(e.getMessage());
		}
		if (response.getStatusLine().getStatusCode() == OK || response.getStatusLine().getStatusCode() == NOT_CONTENT) {
			return;
		}
		else if (response.getStatusLine().getStatusCode() == NOT_FOUND) {
			throw new NotFoundException();
		}
		else if (response.getStatusLine().getStatusCode() == UNAUTHORIZED) {
			throw new UnauthorizedException();
		}
		else {
			throw new RestBlowUpException();
		}
	}

}
