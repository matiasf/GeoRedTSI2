package com.geored.servicios.json;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PosicionJSON {
	
	Double latitud;
	Double longitud;
	Double distancia;
	
	public Double getLatitud() {
		return latitud;
	}
	
	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}
	
	public Double getLongitud() {
		return longitud;
	}
	
	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}
	
	public Double getDistancia() {
		return distancia;
	}
	
	public void setDistancia(Double distancia) {
		this.distancia = distancia;
	}	

}
