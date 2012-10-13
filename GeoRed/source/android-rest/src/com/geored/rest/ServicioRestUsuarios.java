package com.geored.rest;

import java.util.List;

import org.apache.http.HttpResponse;

import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geored.rest.data.Invitacion;
import com.geored.rest.data.Usuario;
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

	public static List<Usuario> getContactos() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			HttpResponse response = rest(Metodos.GET, URL_CONTACTOS);
			if (response.getStatusLine().getStatusCode() == OK) {
				String asciiContent = Utils.getASCIIContentFromEntity(response.getEntity());
				List<Usuario> wrapper = mapper.readValue(asciiContent, new TypeReference<List<Usuario>>() {});
				return wrapper;
			}
		} catch (Exception e) {
			Log.e("Error", "Problemas para hacer consulta rest", e);
		}
		return null;
	}
	
	public static Usuario getContacto(String idUsuario) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			HttpResponse response = rest(Metodos.GET, URL_CONTACTOS + "/" + idUsuario);
			if (response.getStatusLine().getStatusCode() == OK) {
				String asciiContent = Utils.getASCIIContentFromEntity(response.getEntity());
				Usuario wrapper = mapper.readValue(asciiContent, new TypeReference<Usuario>() {});
				return wrapper;
			}
		} catch (Exception e) {
			Log.e("Error", "Problemas para hacer consulta rest", e);
		}
		return null;
	}
	
	public static List<Invitacion> getInvitaciones(String idUsuario) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			HttpResponse response = rest(Metodos.GET, URL_INVITACIONES);
			if (response.getStatusLine().getStatusCode() == OK) {
				String asciiContent = Utils.getASCIIContentFromEntity(response.getEntity());
				List<Invitacion> wrapper = mapper.readValue(asciiContent, new TypeReference<List<Invitacion>>() {});
				return wrapper;
			}
		} catch (Exception e) {
			Log.e("Error", "Problemas para hacer consulta rest", e);
		}
		return null;
	}
	
	public static void invitarContacto(String idContacto) {
		try {
			HttpResponse response = rest(Metodos.PUT, URL_INVITACIONES + "/" + idContacto);
			if (response.getStatusLine().getStatusCode() != OK) {
				Log.e("Error", "No se pudo invitar");
			}
		} catch (Exception e) {
			Log.e("Error", "Problemas para hacer consulta rest", e);
		}
		return;
	}
	
	public static void aceptarInvitacion(String idContacto) {
		try {
			HttpResponse response = rest(Metodos.POST, URL_INVITACIONES + "/" + idContacto);
			if (response.getStatusLine().getStatusCode() != OK) {
				Log.e("Error", "No se pudo aceptar invitacion");
			}
		} catch (Exception e) {
			Log.e("Error", "Problemas para hacer consulta rest", e);
		}
		return;
	}
	
	public static void registrarUsuario(String password, Usuario usuario) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			HttpResponse response = rest(Metodos.PUT, URL_USUARIO + "/" + password, mapper.writeValueAsString(usuario));
			if (response.getStatusLine().getStatusCode() != OK) {
				Log.e("Error", "No se pudo registrar");
			}
		} catch (Exception e) {
			Log.e("Error", "Problemas para hacer consulta rest", e);
		}
		return;
	}
	
	public static void modificarUsuario(String password, Usuario usuario) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			HttpResponse response = rest(Metodos.POST, URL_USUARIO + "/" + password, mapper.writeValueAsString(usuario));
			if (response.getStatusLine().getStatusCode() != OK) {
				Log.e("Error", "No se pudo modificar");
			}
		} catch (Exception e) {
			Log.e("Error", "Problemas para hacer consulta rest", e);
		}
		return;
	}

}
