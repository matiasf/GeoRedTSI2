package com.geored.servicios.impl.inte;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geored.servicios.json.NotificacionJSON;
import com.geored.servicios.json.OfertaJSON;
import com.geored.servicios.json.OfertasIntegracionJSON;
import com.geored.servicios.json.PosicionJSON;
import com.geored.servicios.json.SitiosInteresIntegracionJSON;
import com.geored.servicios.json.converters.ConvertidorEntityJSON;

@Stateless
public class GestionIntegracion {
	
	private final String[] INTEGRACION_URLs = new String[]{"https://grupo11-georeduuy.rhcloud.com/ClienteWS/rest/IntegracionWS/", 
			"http://georeduy-uy.rhcloud.com/GeoRed_MobileWS/rest/integracion/"};
	
	public List<NotificacionJSON> getSitioInteresIntegracion(final Double latitud, final Double longitud,
			final Double distancia) {
		try {
			List<NotificacionJSON> notificaciones = new ArrayList<NotificacionJSON>();
			NotificacionJSON notificacion;
			PosicionJSON posicion;
			for (String INTEGRACION_URL : INTEGRACION_URLs) {
				final String json = getJSONRespone(INTEGRACION_URL + "obtenerSitiosInteresIntegracion/" + latitud + "/" + longitud + "/" + "100");
				ObjectMapper mapper = new ObjectMapper();
				List<SitiosInteresIntegracionJSON> sitiosInteres = mapper.readValue(json, new TypeReference<List<SitiosInteresIntegracionJSON>>(){});
				for (SitiosInteresIntegracionJSON sitioInteresJSON : sitiosInteres) {
					notificacion = new NotificacionJSON();
					notificacion.setId(sitioInteresJSON.getIdSitioInteres().toString() + "-externa");
					notificacion.setNombre(sitioInteresJSON.getNombre());
					notificacion.setDescripcion(sitioInteresJSON.getDescripcion());
					notificacion.setTipo(ConvertidorEntityJSON.TipoNotifiacion.SITIO_DE_INTERES_INTEGRACION.toString());
					posicion = new PosicionJSON();
					posicion.setDistancia(Double.valueOf("100"));
					posicion.setLatitud(sitioInteresJSON.getLatitud().doubleValue());
					posicion.setLongitud(sitioInteresJSON.getLongitud().doubleValue());
					notificacion.setPosicion(posicion);
					notificaciones.add(notificacion);
				}
			}			
			return notificaciones;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<NotificacionJSON> getLocalesIntegracion(final Double latitud, final Double longitud, final Double distancia) {
		try {
			HashMap<Integer, List<OfertasIntegracionJSON>> locales = new HashMap<Integer, List<OfertasIntegracionJSON>>();
			for (String INTEGRACION_URL : INTEGRACION_URLs) {
				final String json = getJSONRespone(INTEGRACION_URL + "obtenerOfertasIntegracion/" + latitud + "/" + longitud + "/" + "100");
				ObjectMapper mapper = new ObjectMapper();
				List<OfertasIntegracionJSON> ofertas = mapper.readValue(json, new TypeReference<List<OfertasIntegracionJSON>>(){});
				for (OfertasIntegracionJSON ofertasJSON : ofertas) {
					if (!locales.containsKey(ofertasJSON.getIdLocal())) {
						List<OfertasIntegracionJSON> eOfertas = new ArrayList<OfertasIntegracionJSON>();
						eOfertas.add(ofertasJSON);
						locales.put(ofertasJSON.getIdLocal(), eOfertas);
					}
					else {
						locales.get(ofertasJSON.getIdLocal()).add(ofertasJSON);
					}
				}
			}
			List<NotificacionJSON> localesNot = new ArrayList<NotificacionJSON>();
			NotificacionJSON notificacion;
			PosicionJSON posicion;
			for (Integer localId : locales.keySet()) {
				notificacion = new NotificacionJSON();
				notificacion.setId(localId.toString() + "-externa");
				notificacion.setNombre(locales.get(localId).get(0).getNombreLocal());
				notificacion.setDescripcion(locales.get(localId).get(0).getDescripcionLocal());
				notificacion.setTipo(ConvertidorEntityJSON.TipoNotifiacion.LOCAL_INTEGRACION.toString());
				posicion = new PosicionJSON();
				posicion.setDistancia(Double.valueOf("100"));
				posicion.setLatitud(locales.get(localId).get(0).getLatitud().doubleValue());
				posicion.setLongitud(locales.get(localId).get(0).getLongitud().doubleValue());
				notificacion.setPosicion(posicion);
				localesNot.add(notificacion);
			}
			return localesNot;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;		
	}

	public List<OfertaJSON> getOfertasIntegracion(final Double latitud, final Double longitud, final Double distancia, 
			final Integer idLocal) {
		try {
			HashMap<Integer, List<OfertasIntegracionJSON>> locales = new HashMap<Integer, List<OfertasIntegracionJSON>>();
			for (String INTEGRACION_URL : INTEGRACION_URLs) {
				final String json = getJSONRespone(INTEGRACION_URL + "obtenerOfertasIntegracion/" + latitud + "/" + longitud + "/" + "100");
				ObjectMapper mapper = new ObjectMapper();
				List<OfertasIntegracionJSON> ofertas = mapper.readValue(json, new TypeReference<List<OfertasIntegracionJSON>>(){});
				
				for (OfertasIntegracionJSON ofertasJSON : ofertas) {
					if (!locales.containsKey(ofertasJSON.getIdLocal())) {
						List<OfertasIntegracionJSON> eOfertas = new ArrayList<OfertasIntegracionJSON>();
						eOfertas.add(ofertasJSON);
						locales.put(ofertasJSON.getIdLocal(), eOfertas);
					}
					else {
						locales.get(ofertasJSON.getIdLocal()).add(ofertasJSON);
					}
				}
			}
			List<OfertaJSON> ofertasJSON = new ArrayList<OfertaJSON>();
			OfertaJSON oferta;
			if (locales.containsKey(idLocal)) {
				for (OfertasIntegracionJSON ofertaInt : locales.get(idLocal)) {
					oferta = new OfertaJSON();
					oferta.setId(ofertaInt.getIdOferta());
					oferta.setNombre(ofertaInt.getNombre());
					oferta.setDescripcion(ofertaInt.getDescripcion());
					oferta.setCosto(ofertaInt.getCosto());
					ofertasJSON.add(oferta);
				}
			}
			return ofertasJSON;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;		
	}
	
	private String getJSONRespone(final String stringUrl) {
		try {
			URL url = new URL(stringUrl);
			URLConnection urlConnection = url.openConnection();
			BufferedReader rd = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null)
			{
				sb.append(line);
			}
			rd.close();
			String json = sb.toString();
			return json;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return null;
	}

}
