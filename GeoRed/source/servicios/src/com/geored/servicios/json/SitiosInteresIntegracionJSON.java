package com.geored.servicios.json;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SitiosInteresIntegracionJSON {
	
	private Integer idSitioInteres;
	private String nombre;
	private String descripcion;
	private Float latitud;
	private Float longitud;
	
	public Integer getIdSitioInteres() {
		return idSitioInteres;
	}
	
	public void setIdSitioInteres(Integer idSitioInteres) {
		this.idSitioInteres = idSitioInteres;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Float getLatitud() {
		return latitud;
	}
	
	public void setLatitud(Float latitud) {
		this.latitud = latitud;
	}
	
	public Float getLongitud() {
		return longitud;
	}
	
	public void setLongitud(Float longitud) {
		this.longitud = longitud;
	}

}
