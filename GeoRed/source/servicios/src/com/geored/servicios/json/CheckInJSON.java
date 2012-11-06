package com.geored.servicios.json;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CheckInJSON {
	
	private Integer id;
	private String comentario;
	private String userName;
	private Integer idImagen;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getComentario() {
		return comentario;
	}
	
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getIdImagen() {
		return idImagen;
	}

	public void setIdImagen(Integer idImagen) {
		this.idImagen = idImagen;
	}	

}
