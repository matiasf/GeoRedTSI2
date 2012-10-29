package com.geored.servicios.impl.gcm;

import java.util.HashMap;

import javax.ejb.Singleton;

@Singleton
public class GestionDevices {
	
	private HashMap<Integer, String> devices = new HashMap<Integer, String>();
	
	public void putDevice(final Integer idUsuario, final String idRegistro) {
		devices.put(idUsuario, idRegistro);
	}
	
	public String getDevice(final Integer idUsuario) {
		return devices.get(idUsuario);
	}
	
	public void removeDevice(final Integer idUsuario) {
		devices.remove(idUsuario);
	}

}
