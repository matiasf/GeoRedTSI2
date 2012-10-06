package com.geored.servicios.testdata;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UsuarioJSON {
	
	private Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
