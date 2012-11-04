package com.geored.rest.data;

public class Mensaje {
	
	private Integer idUsuario;
	private String message;
	
	public Mensaje() {}
	
	public Mensaje(Integer idUsuario, String message) {
		this.idUsuario = idUsuario;
		this.message = message;
	}
	
	public Integer getIdUsuario() {
		return idUsuario;
	}
	
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

}
