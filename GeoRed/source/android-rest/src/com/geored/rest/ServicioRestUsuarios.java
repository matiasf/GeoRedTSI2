package com.geored.rest;

import java.util.List;

import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geored.rest.data.Usuario;
import com.geored.rest.utils.Utils;

public class ServicioRestUsuarios extends ServicioRest {

	final private static String SERVICIO_REST_USUARIOS_URL = SERVICIO_REST_URL
			+ "/usuarios";
	final private static String URL_CONTACTOS = SERVICIO_REST_USUARIOS_URL
			+ "/contactos";

	public static List<Usuario> getContactos() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			List<Usuario> wrapper = mapper.readValue(
					Utils.getASCIIContentFromEntity(rest(Metodos.GET, URL_CONTACTOS)), new TypeReference<List<Usuario>>() {});
			return wrapper;
		} catch (Exception e) {
			Log.e("Error", "Problemas para hacer conulst rest", e);
			e.printStackTrace();
			return null;
		}
	}

}
