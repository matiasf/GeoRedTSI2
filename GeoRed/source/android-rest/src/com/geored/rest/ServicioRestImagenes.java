package com.geored.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
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
			HttpPost request = new HttpPost(url);
			if (content != null) {
				File tmpUpload = File.createTempFile("tmpUpload", "jpg");
				FileOutputStream outputStream = new FileOutputStream(tmpUpload);
				outputStream.write(content);
				outputStream.close();
			    FileBody bin = new FileBody(tmpUpload);
			    StringBody header = new StringBody("upload-file");

			    MultipartEntity reqEntity = new MultipartEntity();
			    reqEntity.addPart("header", header);
			    reqEntity.addPart("payload", bin);
			    request.setEntity(reqEntity);

			    HttpClient httpclient = new DefaultHttpClient();
			    HttpResponse response = httpclient.execute(request); 
			    return response;
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
