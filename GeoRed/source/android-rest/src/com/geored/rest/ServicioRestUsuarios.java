package com.geored.rest;

import java.util.List;

import org.apache.http.HttpResponse;

import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geored.rest.data.Invitacion;
import com.geored.rest.data.Notificacion;
import com.geored.rest.data.Posicion;
import com.geored.rest.data.Usuario;
import com.geored.rest.exception.ConflictException;
import com.geored.rest.exception.NotFoundException;
import com.geored.rest.exception.RestBlowUpException;
import com.geored.rest.exception.UnauthorizedException;
import com.geored.rest.utils.Utils;

public class ServicioRestUsuarios extends ServicioRest {

	final private static String SERVICIO_REST_USUARIOS_URL = SERVICIO_REST_URL
			+ "/usuarios";
	final private static String URL_CONTACTOS = SERVICIO_REST_USUARIOS_URL
			+ "/contactos";
	final private static String URL_INVITACIONES = SERVICIO_REST_USUARIOS_URL 
			+ "/invitaciones";
	final private static String URL_USUARIO = SERVICIO_REST_USUARIOS_URL
			+ "/usuario";
	final private static String URL_NOTIFICACIONES = SERVICIO_REST_USUARIOS_URL
			+ "/notificaciones";
	
	public static List<Usuario> buscarContactos(String query) throws RestBlowUpException, UnauthorizedException {
		ObjectMapper mapper = new ObjectMapper();
		HttpResponse response = rest(Metodos.GET, URL_CONTACTOS + "/buscar/" + query);
		if (response.getStatusLine().getStatusCode() == OK) {
			String asciiContent;
			try {
				asciiContent = Utils.getASCIIContentFromEntity(response.getEntity());
				List<Usuario> wrapper = mapper.readValue(asciiContent, new TypeReference<List<Usuario>>() {});
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

	public static List<Usuario> getContactos() throws RestBlowUpException, NotFoundException, UnauthorizedException {		
		ObjectMapper mapper = new ObjectMapper();
		HttpResponse response = rest(Metodos.GET, URL_CONTACTOS);
		if (response.getStatusLine().getStatusCode() == OK) {
			String asciiContent;
			try {
				asciiContent = Utils.getASCIIContentFromEntity(response.getEntity());
				List<Usuario> wrapper = mapper.readValue(asciiContent, new TypeReference<List<Usuario>>() {});
				return wrapper;
			} catch (Exception e) {
				throw new RestBlowUpException(e.getLocalizedMessage());
			}			
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
	
	public static Usuario getContacto(String idUsuario) throws RestBlowUpException, NotFoundException, UnauthorizedException {
		ObjectMapper mapper = new ObjectMapper();
		HttpResponse response = rest(Metodos.GET, URL_CONTACTOS + "/" + idUsuario);
		if (response.getStatusLine().getStatusCode() == OK) {
			String asciiContent;
			try {
				asciiContent = Utils.getASCIIContentFromEntity(response.getEntity());
				Usuario wrapper = mapper.readValue(asciiContent, new TypeReference<Usuario>() {});
				return wrapper;
			} catch (Exception e) {
				throw new RestBlowUpException(e.getMessage());
			}			
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
	
	public static List<Invitacion> getInvitaciones() throws RestBlowUpException, NotFoundException, UnauthorizedException {
		
			ObjectMapper mapper = new ObjectMapper();
			HttpResponse response = rest(Metodos.GET, URL_INVITACIONES);
			if (response.getStatusLine().getStatusCode() == OK) {
				try {
					String asciiContent = Utils.getASCIIContentFromEntity(response.getEntity());
					List<Invitacion> wrapper = mapper.readValue(asciiContent, new TypeReference<List<Invitacion>>() {});
					return wrapper;
				} catch (Exception e) {
					throw new RestBlowUpException(e.getMessage());
				}
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
	
	public static void invitarContacto(String idContacto) throws RestBlowUpException, NotFoundException,
		UnauthorizedException, ConflictException {
		HttpResponse response = rest(Metodos.PUT, URL_INVITACIONES + "/" + idContacto);
		if (response.getStatusLine().getStatusCode() == OK || response.getStatusLine().getStatusCode() == NOT_CONTENT) {
			return;
		}
		else if (response.getStatusLine().getStatusCode() == NOT_FOUND) {
			throw new NotFoundException();
		}
		else if (response.getStatusLine().getStatusCode() == CONFLICT) {
			throw new ConflictException();
		}
		else if (response.getStatusLine().getStatusCode() == UNAUTHORIZED) {
			throw new UnauthorizedException();
		}
		else {
			throw new RestBlowUpException();
		}
	}
	
	public static void aceptarInvitacion(String idContacto) throws RestBlowUpException, NotFoundException,
		UnauthorizedException, ConflictException {	
		HttpResponse response = rest(Metodos.POST, URL_INVITACIONES + "/" + idContacto);
		if (response.getStatusLine().getStatusCode() == OK || response.getStatusLine().getStatusCode() == NOT_CONTENT) {
			return;
		}
		else if (response.getStatusLine().getStatusCode() == NOT_FOUND) {
			throw new NotFoundException();
		}
		else if (response.getStatusLine().getStatusCode() == CONFLICT) {
			throw new ConflictException();
		}
		else if (response.getStatusLine().getStatusCode() == UNAUTHORIZED) {
			throw new UnauthorizedException();
		}
		else {
			throw new RestBlowUpException();
		}
	}
	
	public static void registrarUsuario(String password, Usuario usuario) throws RestBlowUpException {
		ObjectMapper mapper = new ObjectMapper();
		HttpResponse response;
		try {
			response = rest(Metodos.PUT, URL_USUARIO + "/" + password, mapper.writeValueAsString(usuario), false);
		} catch (Exception e) {
			Log.e("FATAL ERROR", e.getMessage(), e);
			throw new RestBlowUpException(e.getMessage());
		}
		if (response.getStatusLine().getStatusCode() == OK || response.getStatusLine().getStatusCode() == NOT_CONTENT) {
			return;
		}
		else {
			throw new RestBlowUpException();
		}
	}
	
	public static void modificarUsuario(String password, Usuario usuario) throws RestBlowUpException, NotFoundException, UnauthorizedException {
		ObjectMapper mapper = new ObjectMapper();
		HttpResponse response;
		try {
			response = rest(Metodos.POST, URL_USUARIO + "/" + password, mapper.writeValueAsString(usuario));
		} catch (Exception e) {
			Log.e("FATAL ERROR", e.getMessage(), e);
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
	
	public static List<Notificacion> getNotificaciones(Posicion posicion) throws RestBlowUpException, NotFoundException, UnauthorizedException {
		ObjectMapper mapper = new ObjectMapper();
		HttpResponse response;
		try {
			response = rest(Metodos.POST, URL_NOTIFICACIONES, mapper.writeValueAsString(posicion));
		} catch (Exception e) {
			throw new RestBlowUpException(e.getLocalizedMessage());
		}
		if (response.getStatusLine().getStatusCode() == OK) {
			String asciiContent;
			try {
				asciiContent = Utils.getASCIIContentFromEntity(response.getEntity());
				List<Notificacion> wrapper = mapper.readValue(asciiContent, new TypeReference<List<Notificacion>>() {});
				return wrapper;
			} catch (Exception e) {
				throw new RestBlowUpException(e.getLocalizedMessage());
			}
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
