package com.geored.servicios.json;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UsuarioJSON {
	
	private Integer id;
	
	private String nombre;

	private String password;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
