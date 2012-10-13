package com.geored.rest;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;

import android.util.Log;

public class ServicioRest {

	public enum Metodos {
		GET, POST, PUT
	}

	final protected static int OK = 200;
	
	final protected static String SERVICIO_REST_URL = "https://tsi2test-rectadeeuler.rhcloud.com/servicios/rest";
	final private static String SECURITY_HEADER = "Security-Token";
	
	private static String securityToken;
	
	protected static String getSecurityToken() {
		return securityToken;
	}

	protected static void setSecurityToken(String securityToken) {
		ServicioRest.securityToken = securityToken;
	}
	
	protected static HttpResponse rest(Metodos metodo, String url) {
		return rest(metodo, url, null);
	}

	protected static HttpResponse rest(Metodos metodo, String url, String content) {
		try {
			HttpRequestBase request = null;
			switch (metodo) {
				case GET: {
					request = new HttpGet(url);
				}
				break;
				case POST: {
					request = new HttpPost(url);
					if (content != null) {
						StringEntity entry = new StringEntity(content);  
						entry.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
						((HttpPost)request).setEntity(entry);
					}
				}
				break;
				case PUT: {
					request = new HttpPut(url);
					if (content != null) {
						StringEntity entry = new StringEntity(content);  
						entry.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
						((HttpPut)request).setEntity(entry);
					}
				}
				break;
			}			
			if (request != null) {
				if (getSecurityToken() != null)
					request.addHeader(SECURITY_HEADER, getSecurityToken());
				HttpClient cliente = new DefaultHttpClient();
				HttpResponse response = cliente.execute(request, new BasicHttpContext());
				return response;
			} 
			else {
				Log.d("Error", "Request mal formado.");
				return null;
			}
		} catch (ClientProtocolException e) {
			Log.e("Error", "URI syntax was incorrect.", e);
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("Error", "There was a problem when sending the request.", e);
			e.printStackTrace();
		}
		return null;
	}

}
