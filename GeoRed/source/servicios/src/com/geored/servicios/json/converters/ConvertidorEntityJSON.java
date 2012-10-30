package com.geored.servicios.json.converters;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.Singleton;

import com.geored.servicios.json.CategoriaJSON;
import com.geored.servicios.json.CheckInJSON;
import com.geored.servicios.json.InvitacionJSON;
import com.geored.servicios.json.NotificacionJSON;
import com.geored.servicios.json.PosicionJSON;
import com.geored.servicios.json.UsuarioJSON;

import persistencia.Categoria;
import persistencia.CheckIn;
import persistencia.Invitacion;
import persistencia.SitioInteres;
import persistencia.Usuario;


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
	
	public NotificacionJSON convertir(SitioInteres sitioInteres) {
		NotificacionJSON notifiacionJSON = new NotificacionJSON();
		notifiacionJSON.setId(sitioInteres.getId());
		notifiacionJSON.setDescripcion(sitioInteres.getDescripcion());
		notifiacionJSON.setNombre(sitioInteres.getNombre());
		PosicionJSON posicionJSON = new PosicionJSON();
		posicionJSON.setLatitud(sitioInteres.getLatitud());
		posicionJSON.setLongitud(sitioInteres.getLongitud());
		notifiacionJSON.setPosicion(posicionJSON);
		return notifiacionJSON;
	}
	
	private CategoriaJSON convertir(Categoria categoria) {
		CategoriaJSON categoriaJSON = new CategoriaJSON();
		categoriaJSON.setId(categoria.getId());
		categoriaJSON.setNombre(categoria.getNombre());
		categoriaJSON.setDescripcion(categoria.getDescripcion());
		return categoriaJSON;
	}
	
	public CheckIn convertir(CheckInJSON checkInJSON) {
		CheckIn checkIn = new CheckIn();
		checkIn.setFecha(GregorianCalendar.getInstance());
		checkIn.setComentario(checkInJSON.getComentario());
		return checkIn;
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
			else if (s instanceof SitioInteres) {
				tt.add((T)convertir((SitioInteres)s));
			}
			else if (s instanceof Categoria) {
				tt.add((T)convertir((Categoria)s));
			}
		}
		return tt;
	}

}
