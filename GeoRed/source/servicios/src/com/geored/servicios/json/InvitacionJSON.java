package com.geored.servicios.json;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class InvitacionJSON {
	
	private Integer id;
	
	private UsuarioJSON remitente;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UsuarioJSON getRemitente() {
		return remitente;
	}

	public void setRemitente(UsuarioJSON remitente) {
		this.remitente = remitente;
	}

}
