package com.geored.servicios.testdata;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Invitacion {
	
	private String id;
	private Usuario usuario;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
