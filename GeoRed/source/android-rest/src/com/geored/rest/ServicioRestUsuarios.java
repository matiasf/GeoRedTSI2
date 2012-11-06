package com.geored.rest;

import java.util.List;

import org.apache.http.HttpResponse;

import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geored.rest.data.Categoria;
import com.geored.rest.data.Invitacion;
import com.geored.rest.data.Notificacion;
import com.geored.rest.data.Oferta;
import com.geored.rest.data.Pago;
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
	final private static String URL_CATEGORIAS = SERVICIO_REST_USUARIOS_URL
			+ "/categorias";
	final private static String URL_NOTIFICACIONES = SERVICIO_REST_USUARIOS_URL
			+ "/notificaciones";
	final private static String URL_OFERTAS = SERVICIO_REST_USUARIOS_URL
			+ "/ofertas";
	
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
	
	public static Usuario getUsuario() throws RestBlowUpException, NotFoundException, UnauthorizedException {
		ObjectMapper mapper = new ObjectMapper();
		HttpResponse response = rest(Metodos.GET, URL_USUARIO);
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
	
	public static void agregarCategorias(final List<Integer> categorias) throws RestBlowUpException, UnauthorizedException {
		ObjectMapper mapper = new ObjectMapper();
		HttpResponse response;
		try {
			response = rest(Metodos.POST, URL_CATEGORIAS + "/agregar", mapper.writeValueAsString(categorias));
		} catch (Exception e) {
			throw new RestBlowUpException(e.getLocalizedMessage());
		}
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
	
	public static void borrarCategorias(final List<Integer> categorias) throws RestBlowUpException, UnauthorizedException {
		ObjectMapper mapper = new ObjectMapper();
		HttpResponse response;
		try {
			response = rest(Metodos.POST, URL_CATEGORIAS + "/borrar", mapper.writeValueAsString(categorias));
		} catch (Exception e) {
			throw new RestBlowUpException(e.getLocalizedMessage());
		}
		if (response.getStatusLine().getStatusCode() == OK) {
			return;		
		}
		else if (response.getStatusLine().getStatusCode() == UNAUTHORIZED) {
			throw new UnauthorizedException();
		}
		else {
			throw new RestBlowUpException();
		}
	}
	
	public static List<Categoria> getCategorias() throws RestBlowUpException, UnauthorizedException {
		ObjectMapper mapper = new ObjectMapper();
		HttpResponse response;
		response = rest(Metodos.GET	, URL_CATEGORIAS);
		if (response.getStatusLine().getStatusCode() == OK) {
			String asciiContent;
			try {
				asciiContent = Utils.getASCIIContentFromEntity(response.getEntity());
				List<Categoria> wrapper = mapper.readValue(asciiContent, new TypeReference<List<Categoria>>() {});
				return wrapper;
			} catch (Exception e) {
				throw new RestBlowUpException(e.getLocalizedMessage());
			}	
		}
		else if (response.getStatusLine().getStatusCode() == UNAUTHORIZED) {
			throw new UnauthorizedException();
		}
		else {
			throw new RestBlowUpException();
		}
	}
	
	public static List<Notificacion> getNotificaciones(final Posicion posicion) throws RestBlowUpException, NotFoundException, UnauthorizedException {
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
	
	public static void comprarOferta(final Integer idOferta, final Pago pago) throws RestBlowUpException, UnauthorizedException, NotFoundException {
		ObjectMapper mapper = new ObjectMapper();
		HttpResponse response;
		try {
			response = rest(Metodos.POST, URL_OFERTAS + "/" + idOferta, mapper.writeValueAsString(pago));
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
	
	public static List<Oferta> getOfertas(final Integer idLocal) throws RestBlowUpException, UnauthorizedException {
		ObjectMapper mapper = new ObjectMapper();
		HttpResponse response;
		response = rest(Metodos.GET, URL_OFERTAS + "/" + idLocal);
		if (response.getStatusLine().getStatusCode() == OK || response.getStatusLine().getStatusCode() == NOT_CONTENT) {
			String asciiContent;
			try {
				asciiContent = Utils.getASCIIContentFromEntity(response.getEntity());
				List<Oferta> wrapper = mapper.readValue(asciiContent, new TypeReference<List<Oferta>>() {});
				return wrapper;
			} catch (Exception e) {
				Log.e("FATAL ERROR", e.getMessage(), e);
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
