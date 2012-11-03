package com.geored.rest;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;

import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geored.rest.data.Imagen;
import com.geored.rest.exception.RestBlowUpException;
import com.geored.rest.exception.UnauthorizedException;
import com.geored.rest.utils.Utils;

public class ServicioRestImagenes extends ServicioRest {

	final private static String SERVICIO_REST_IMAGEN_URL = SERVICIO_REST_URL
			+ "/imagenes";
	final private static String URL_IMAGEN = SERVICIO_REST_IMAGEN_URL
			+ "/imagen";

	public static InputStream bajarImagen(final Integer idImagen) throws RestBlowUpException {
		HttpResponse response = rest(Metodos.GET, URL_IMAGEN + "/" + idImagen);
		if (response.getStatusLine().getStatusCode() == OK) {
			try {
				return response.getEntity().getContent();
			} 
			catch (Exception e) {
				throw new RestBlowUpException(e.getMessage());
			}
		} 
		else {
			throw new RestBlowUpException();
		}
	}

	public static Imagen subirImagen(final byte[] imagen) throws RestBlowUpException, UnauthorizedException {
		ObjectMapper mapper = new ObjectMapper();
		HttpResponse response = rest(Metodos.POST, URL_IMAGEN, imagen, true);
		if (response.getStatusLine().getStatusCode() == OK) {
			String asciiContent;
			try {
				asciiContent = Utils.getASCIIContentFromEntity(response.getEntity());
				Imagen wrapper = mapper.readValue(asciiContent, new TypeReference<Imagen>() {});
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

	private static HttpResponse rest(Metodos metodo, String url, byte[] content, Boolean secure) {
		try {
			HttpPost request = null;
			request = new HttpPost(url);
			if (content != null) {
				ByteArrayEntity entry = new ByteArrayEntity(content);
				request.setEntity(entry);
			}
			if (request != null) {
				if (secure)
					request.addHeader(SECURITY_HEADER, getSecurityToken());
				HttpClient cliente = new DefaultHttpClient();
				HttpResponse response;
				response = cliente.execute(request, new BasicHttpContext());
				return response;
			}
		} 
		catch (ClientProtocolException e) {
			Log.e("Error", "URI syntax was incorrect.", e);
			e.printStackTrace();
		} 
		catch (IOException e) {
			Log.e("Error", "There was a problem when sending the request.", e);
			e.printStackTrace();
		}
		return null;
	}

}
