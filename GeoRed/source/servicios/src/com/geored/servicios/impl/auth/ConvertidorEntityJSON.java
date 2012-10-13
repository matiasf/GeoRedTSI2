package com.geored.servicios.impl.auth;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Singleton;

import persistencia.Invitacion;
import persistencia.Usuario;

import com.geored.servicios.json.InvitacionJSON;
import com.geored.servicios.json.UsuarioJSON;

@Singleton
public class ConvertidorEntityJSON {
	
	public UsuarioJSON convertir(Usuario usuario) {
		UsuarioJSON usuarioJSON = new UsuarioJSON();
		usuarioJSON.setId(usuario.getId());
		usuarioJSON.setNombre(usuario.getNombre());
		return usuarioJSON;
	}
	
	public InvitacionJSON convertir(Invitacion invitacion) {
		InvitacionJSON invitacionJSON = new InvitacionJSON();
		invitacionJSON.setId(invitacion.getId());
		invitacionJSON.setRemitente(convertir(invitacion.getRemitente()));
		return invitacionJSON;
	}
	
	public Usuario convertir(UsuarioJSON usuarioJSON) {
		Usuario usuario = new Usuario();
		usuario.setId(usuarioJSON.getId() == null ? 0 : usuarioJSON.getId());
		usuario.setNombre(usuarioJSON.getNombre());
		return usuario;
	}
	
	@SuppressWarnings("unchecked")
	public <T, S> List<T> convert(List<S> ss) {
		List<T> tt = new ArrayList<T>();
		for (S s : ss) {
			if (s instanceof Usuario) {
				tt.add((T)convertir((Usuario)s));
			}
			else if (s instanceof Invitacion) {
				tt.add((T)convertir((Invitacion)s));
			}
			else if (s instanceof UsuarioJSON) {
				tt.add((T)convertir((UsuarioJSON)s));
			}
		}
		return tt;
	}

}
