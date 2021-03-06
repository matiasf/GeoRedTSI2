package com.geored.servicios.json.converters;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.Singleton;

import persistencia.Categoria;
import persistencia.CheckIn;
import persistencia.Evento;
import persistencia.Invitacion;
import persistencia.Local;
import persistencia.Notificacion;
import persistencia.Oferta;
import persistencia.Pago;
import persistencia.SitioInteres;
import persistencia.Usuario;

import com.geored.servicios.json.CategoriaJSON;
import com.geored.servicios.json.CheckInJSON;
import com.geored.servicios.json.EventoJSON;
import com.geored.servicios.json.InvitacionJSON;
import com.geored.servicios.json.NotificacionJSON;
import com.geored.servicios.json.OfertaJSON;
import com.geored.servicios.json.PagoJSON;
import com.geored.servicios.json.PosicionJSON;
import com.geored.servicios.json.UsuarioJSON;


@Singleton
public class ConvertidorEntityJSON {
	
	public enum TipoNotifiacion {
		SITIO_DE_INTERES, SITIO_DE_INTERES_INTEGRACION, EVENTO, LOCAL, LOCAL_INTEGRACION, CHECK_IN
	}
	
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
	
	public NotificacionJSON convertir(Notificacion notificacion) {
		NotificacionJSON notifiacionJSON = null;
		PosicionJSON posicionJSON;
		if (notificacion instanceof SitioInteres) {
			SitioInteres sitioInteres = (SitioInteres)notificacion;
			notifiacionJSON = new NotificacionJSON();
			notifiacionJSON.setId(String.valueOf(sitioInteres.getId()));
			notifiacionJSON.setDescripcion(sitioInteres.getDescripcion());
			notifiacionJSON.setTipo(TipoNotifiacion.SITIO_DE_INTERES.toString());
			notifiacionJSON.setNombre(sitioInteres.getNombre());
			posicionJSON = new PosicionJSON();
			posicionJSON.setLatitud(sitioInteres.getLatitud());
			posicionJSON.setLongitud(sitioInteres.getLongitud());
			notifiacionJSON.setPosicion(posicionJSON);
		}
		else if (notificacion instanceof Evento) {
			Evento evento = (Evento)notificacion;
			notifiacionJSON = new NotificacionJSON();
			notifiacionJSON.setId(String.valueOf(evento.getId()));
			notifiacionJSON.setDescripcion(evento.getDescripcion());
			notifiacionJSON.setTipo(TipoNotifiacion.EVENTO.toString());
			notifiacionJSON.setNombre(evento.getNombre());
			posicionJSON = new PosicionJSON();
			posicionJSON.setLatitud(evento.getLatitud());
			posicionJSON.setLongitud(evento.getLongitud());
			notifiacionJSON.setPosicion(posicionJSON);
		}
		else if (notificacion instanceof Local) {
			Local local = (Local)notificacion;
			notifiacionJSON = new NotificacionJSON();
			notifiacionJSON.setId(String.valueOf(local.getId()));
			notifiacionJSON.setDescripcion(local.getDescripcion());
			notifiacionJSON.setTipo(TipoNotifiacion.LOCAL.toString());
			notifiacionJSON.setNombre(local.getNombre());
			posicionJSON = new PosicionJSON();
			posicionJSON.setLatitud(local.getLatitud());
			posicionJSON.setLongitud(local.getLongitud());
			notifiacionJSON.setPosicion(posicionJSON);
		}
		else if (notificacion instanceof CheckIn) {
			CheckIn checkIn = (CheckIn)notificacion;
			notifiacionJSON = new NotificacionJSON();
			notifiacionJSON.setId(String.valueOf(checkIn.getId()));
			notifiacionJSON.setDescripcion(checkIn.getComentario());
			notifiacionJSON.setTipo(TipoNotifiacion.CHECK_IN.toString() + ":" + checkIn.getSitioInteres().getId());
			notifiacionJSON.setNombre(checkIn.getUsuario().getNombre());
		}
		return notifiacionJSON;
	}
	
	private CategoriaJSON convertir(Categoria categoria) {
		CategoriaJSON categoriaJSON = new CategoriaJSON();
		categoriaJSON.setId(categoria.getId());
		categoriaJSON.setNombre(categoria.getNombre());
		categoriaJSON.setDescripcion(categoria.getDescripcion());
		return categoriaJSON;
	}
	
	private OfertaJSON convertir(Oferta oferta) {
		OfertaJSON ofertaJSON = new OfertaJSON();
		ofertaJSON.setId(oferta.getId());
		ofertaJSON.setNombre(oferta.getNombre());
		ofertaJSON.setCosto(oferta.getCosto());
		ofertaJSON.setDescripcion(oferta.getDescripcion());
		ofertaJSON.setComienzo(oferta.getComienzo().getTime());
		ofertaJSON.setFin(oferta.getFin().getTime());
		if (oferta.getFoto() != null) {
			ofertaJSON.setIdImagen(oferta.getFoto().getId());
		}
		return ofertaJSON;
	}
	
	public EventoJSON convertir(Evento evento) {
		EventoJSON eventoJSON = new EventoJSON();
		eventoJSON.setId(evento.getId());
		eventoJSON.setFin(evento.getFin().getTime());
		eventoJSON.setInicio(evento.getInicio().getTime());
		eventoJSON.setDescripcion(evento.getDescripcion());
		eventoJSON.setNombre(evento.getNombre());
		if (evento.getFoto() != null) {
			eventoJSON.setIdImagen(evento.getFoto().getId());
		}
		return eventoJSON;
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
	
	public Pago convertir(PagoJSON pagoJSON) {
		Pago pago = new Pago();
		pago.setEvaluacion(pagoJSON.getEvaluacion());
		pago.setComentario(pagoJSON.getComentario());
		return pago;
	}
	
	public List<CheckInJSON> convertBajo(List<CheckIn> checkIns) {
		List<CheckInJSON> checkInJSONs = new ArrayList<CheckInJSON>();
		CheckInJSON checkInJSON;
		for (CheckIn checkIn : checkIns) {
			checkInJSON = new CheckInJSON();
			checkInJSON.setComentario(checkIn.getComentario());
			checkInJSON.setId(checkIn.getId());
			checkInJSON.setIdImagen(checkIn.getFoto() != null ? checkIn.getFoto().getId() : null);
			checkInJSON.setUserName(checkIn.getUsuario().getNombre());
			checkInJSONs.add(checkInJSON);
		}
		return checkInJSONs;
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
			else if (s instanceof SitioInteres || s instanceof Local || s instanceof Evento || s instanceof CheckIn) {
				tt.add((T)convertir((Notificacion)s));
			}
			else if (s instanceof Categoria) {
				tt.add((T)convertir((Categoria)s));
			}
			else if (s instanceof Oferta) {
				tt.add((T)convertir((Oferta)s));
			}
			else if (s instanceof UsuarioJSON) {
				tt.add((T)convertir((UsuarioJSON)s));
			}			
		}
		return tt;
	}

}
