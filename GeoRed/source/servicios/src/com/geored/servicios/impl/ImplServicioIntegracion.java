package com.geored.servicios.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import persistencia.Oferta;
import persistencia.SitioInteres;

import negocios.GestionUsuarios;

import com.geored.servicios.ServicioIntegracion;
import com.geored.servicios.json.OfertasIntegracionJSON;
import com.geored.servicios.json.SitiosInteresIntegracionJSON;

@Local
@Stateless
public class ImplServicioIntegracion implements ServicioIntegracion {
	
	@EJB
	GestionUsuarios gesitonUsuario;

	@Override
	public List<SitiosInteresIntegracionJSON> getSitiosInteresIntegracion(Float latitud, Float longitud, Float radio) {
		List<SitioInteres> sitiosInteres = gesitonUsuario.getSitioInteresIntegracion(latitud, longitud, radio);
		List<SitiosInteresIntegracionJSON> sitiosIntegracion = new ArrayList<SitiosInteresIntegracionJSON>();
		SitiosInteresIntegracionJSON sitioTmp;
		for (SitioInteres sitioInters : sitiosInteres) {
			sitioTmp = new SitiosInteresIntegracionJSON();
			sitioTmp.setIdSitioInteres(sitioInters.getId());
			sitioTmp.setDescripcion(sitioInters.getDescripcion());
			sitioTmp.setNombre(sitioInters.getNombre());
			sitioTmp.setLatitud(Double.valueOf(sitioInters.getLatitud()).floatValue());
			sitioTmp.setLongitud(Double.valueOf(sitioInters.getLongitud()).floatValue());
			sitiosIntegracion.add(sitioTmp);
		}
		return sitiosIntegracion;
	}

	@Override
	public List<OfertasIntegracionJSON> getOfertasIntegracion(Float latitud, Float longitud, Float radio) {
		List<persistencia.Local> locales = gesitonUsuario.getOfertasIntegracion(latitud, longitud, radio);
		List<OfertasIntegracionJSON> ofertas = new ArrayList<OfertasIntegracionJSON>();
		OfertasIntegracionJSON ofertaJSON;
		for (persistencia.Local local : locales) {
			ofertaJSON = new OfertasIntegracionJSON();
			ofertaJSON.setIdLocal(local.getId());
			ofertaJSON.setDescripcionLocal(local.getDescripcion());
			ofertaJSON.setLatitud(local.getLatitud());
			ofertaJSON.setLatitud(local.getLongitud());
			ofertaJSON.setNombreLocal(local.getNombre());
			for (Oferta oferta : local.getOfertas()) {
				ofertaJSON.setCosto(oferta.getCosto());
				ofertaJSON.setDescripcion(oferta.getDescripcion());
				ofertaJSON.setFin(oferta.getFin().getTime());
				ofertaJSON.setInicio(oferta.getComienzo().getTime());
				ofertaJSON.setIdOferta(oferta.getId());
				ofertaJSON.setNombre(oferta.getNombre());
				ofertas.add(ofertaJSON);
			}
		}
		return ofertas;
	}
	
}
