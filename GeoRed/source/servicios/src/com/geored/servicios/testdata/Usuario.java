package com.geored.servicios.testdata;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Usuario {

	String nombre;
	String id; 
	//List<Invitacion> invitaciones;

	public Usuario() {	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	/*public List<Invitacion> getInvitaciones() {
		return invitaciones;
	}

	public void setInvitaciones(List<Invitacion> invitaciones) {
		this.invitaciones = invitaciones;
	}*/

}
