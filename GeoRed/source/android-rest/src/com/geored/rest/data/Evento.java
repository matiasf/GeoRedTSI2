package com.geored.rest.data;

import java.util.Date;

public class Evento {
	
	private Integer id;	
	private String nombre;
	private String descripcion;
	private Date inicio;
	private Date fin;
	private Integer idImagen;
	
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
	
	public Integer getIdImagen() {
		return idImagen;
	}
	
	public void setIdImagen(Integer idImagen) {
		this.idImagen = idImagen;
	}
	
}

