package com.geored.rest.data;

public class Invitacion {
	
	String id;
	Usuario remitente;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Usuario getRemitente() {
		return remitente;
	}
	
	public void setRemitente(Usuario remitente) {
		this.remitente = remitente;
	}

}
