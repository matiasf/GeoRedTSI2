package com.geored.servicios.impl.auth;

import java.util.HashMap;
import java.util.UUID;

import javax.ejb.Singleton;

@Singleton
public class GestionTokens {
	
	private HashMap<String, Integer> tokens = new HashMap<String, Integer>();
	
	public String obtenerToken(Integer idUsuario) {
		String token = UUID.randomUUID().toString();
		tokens.put(token, idUsuario);
		return token;
	}
	
	public boolean validarToken(String token) {
		return tokens.containsKey(token);
	}
	
	public Integer getIdUsuario(String token) {
		return tokens.get(token);
	}

}
