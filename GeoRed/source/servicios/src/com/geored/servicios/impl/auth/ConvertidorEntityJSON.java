package com.geored.servicios.impl.auth;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Singleton;

import persistencia.Usuario;

import com.geored.servicios.json.UsuarioJSON;

@Singleton
public class ConvertidorEntityJSON {
	
	public UsuarioJSON convertir(Usuario usuario) {
		UsuarioJSON usuarioJSON = new UsuarioJSON();
		usuarioJSON.setId(usuario.getId());
		usuarioJSON.setNombre(usuario.getNombre());
		usuarioJSON.setPassword(usuario.getPassword());
		return usuarioJSON;
	}
	
	@SuppressWarnings("unchecked")
	public <T, S> List<T> convert(List<S> ss) {
		List<T> tt = new ArrayList<T>();
		for (S s : ss) {
			if (s instanceof Usuario) {
				tt.add((T)convertir((Usuario)s));
			}			
		}
		return tt;
	}

}
