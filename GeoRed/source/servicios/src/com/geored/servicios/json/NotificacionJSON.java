package com.geored.servicios.json;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NotificacionJSON {
	
	private Integer id;
	private String nombre;
	private String descripcion;
	private PosicionJSON posicion;
	
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

	public PosicionJSON getPosicion() {
		return posicion;
	}

	public void setPosicion(PosicionJSON posicion) {
		this.posicion = posicion;
	}

}
