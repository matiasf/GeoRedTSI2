package com.geored.servicios.impl.gcm;

import com.geored.servicios.json.PosicionJSON;

public class Device {
	
	private String idRegisto;
	private PosicionJSON posicion;
	
	public String getIdRegisto() {
		return idRegisto;
	}
	
	public void setIdRegisto(String idRegisto) {
		this.idRegisto = idRegisto;
	}
	
	public PosicionJSON getPosicion() {
		return posicion;
	}
	
	public void setPosicion(PosicionJSON posicion) {
		this.posicion = posicion;
	}

}
