package com.geored.servicios.json;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PagoJSON {
	
	private Integer id;
	private Integer evaluacion;
	private String comentario;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getEvaluacion() {
		return evaluacion;
	}
	
	public void setEvaluacion(Integer evaluacion) {
		this.evaluacion = evaluacion;
	}
	
	public String getComentario() {
		return comentario;
	}
	
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

}
