package com.geored.rest;

import java.util.List;

import org.apache.http.HttpResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geored.rest.data.CheckIn;
import com.geored.rest.exception.NotFoundException;
import com.geored.rest.exception.RestBlowUpException;
import com.geored.rest.exception.UnauthorizedException;
import com.geored.rest.utils.Utils;

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

	public static List<CheckIn> getCheckIns(String idSitioInteres) throws RestBlowUpException, UnauthorizedException {
		ObjectMapper mapper = new ObjectMapper();
		HttpResponse response = rest(Metodos.GET, URL_CHECKIN + "/" + idSitioInteres);
		if (response.getStatusLine().getStatusCode() == OK || response.getStatusLine().getStatusCode() == NOT_CONTENT) {
			String asciiContent;
			try {
				asciiContent = Utils.getASCIIContentFromEntity(response.getEntity());
				List<CheckIn> wrapper = mapper.readValue(asciiContent, new TypeReference<List<CheckIn>>() {});
				return wrapper;
			} catch (Exception e) {
				throw new RestBlowUpException(e.getMessage());
			}
		}
		else if (response.getStatusLine().getStatusCode() == UNAUTHORIZED) {
			throw new UnauthorizedException();
		}
		else {
			throw new RestBlowUpException();
		}
	}

}
