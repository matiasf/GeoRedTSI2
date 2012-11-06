package com.geored.rest.data;

import java.util.Date;

public class Oferta {
	
	private Integer id;
	private Integer idImagen;
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

	public Integer getIdImagen() {
		return idImagen;
	}

	public void setIdImagen(Integer idImagen) {
		this.idImagen = idImagen;
	}

}
