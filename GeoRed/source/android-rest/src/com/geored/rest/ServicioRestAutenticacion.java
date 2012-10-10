package com.geored.rest;

import android.util.Log;

import com.geored.rest.utils.Utils;

public class ServicioRestAutenticacion extends ServicioRest {

	final private static String SERVICIO_REST_AUTENTICACION_URL = SERVICIO_REST_URL
			+ "/autenticar";
	final private static String URL_LOGIN = SERVICIO_REST_AUTENTICACION_URL
			+ "/login";

	public static String login(String nombre, String password) {
		try {
			setSecurityToken(Utils.getASCIIContentFromEntity(rest(Metodos.POST,
					URL_LOGIN + "/" + nombre + "/" + password)));
			return getSecurityToken();
		} catch (Exception e) {
			Log.e("Error", "Problemas para hacer conulst rest", e);
			e.printStackTrace();
			return e.getMessage();
		}
	}

}
