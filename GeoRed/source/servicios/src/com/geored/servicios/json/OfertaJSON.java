package com.geored.servicios.json;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OfertaJSON {
	
	private Integer id;
	private String nombre;
	private String descripcion;
	private Float costo;
	private Date comienzo;
	private Date fin;

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
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
	
	public Float getCosto() {
		return costo;
	}
	
	public void setCosto(Float costo) {
		this.costo = costo;
	}
	
	public Date getComienzo() {
		return comienzo;
	}
	
	public void setComienzo(Date comienzo) {
		this.comienzo = comienzo;
	}
	
	public Date getFin() {
		return fin;
	}
	
	public void setFin(Date fin) {
		this.fin = fin;
	}
	
}
