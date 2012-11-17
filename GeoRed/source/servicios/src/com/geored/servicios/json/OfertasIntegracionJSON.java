package com.geored.servicios.json;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OfertasIntegracionJSON {
	
	//Oferta
	private Integer idOferta;	
	private String nombre;	
	private String descripcion;	
	private Date inicio;
	private Date fin;
	private Float costo;
	
	//Local
	private Integer idLocal;
	private String nombreLocal;
	private String descripcionLocal;
	private Double latitud;
	private Double longitud;

	public Integer getIdOferta() {
		return idOferta;
	}
	
	public void setIdOferta(Integer idOferta) {
		this.idOferta = idOferta;
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
	
	public Date getInicio() {
		return inicio;
	}
	
	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}
	
	public Date getFin() {
		return fin;
	}
	
	public void setFin(Date fin) {
		this.fin = fin;
	}
	
	public Float getCosto() {
		return costo;
	}
	
	public void setCosto(Float costo) {
		this.costo = costo;
	}
	
	public Integer getIdLocal() {
		return idLocal;
	}
	
	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}
	
	public String getNombreLocal() {
		return nombreLocal;
	}
	
	public void setNombreLocal(String nombreLocal) {
		this.nombreLocal = nombreLocal;
	}
	
	public String getDescripcionLocal() {
		return descripcionLocal;
	}
	
	public void setDescripcionLocal(String descripcionLocal) {
		this.descripcionLocal = descripcionLocal;
	}
	
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

}
