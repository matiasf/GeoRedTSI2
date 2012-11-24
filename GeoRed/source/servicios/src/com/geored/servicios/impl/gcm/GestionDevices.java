package com.geored.servicios.impl.gcm;

import java.util.HashMap;

import javax.ejb.Singleton;

import com.geored.servicios.json.PosicionJSON;

@Singleton
public class GestionDevices {
	
	private HashMap<Integer, Device> devices = new HashMap<Integer, Device>();
	
	public void putDevice(final Integer idUsuario, final String idRegistro) {
		Device device = new Device();
		device.setIdRegisto(idRegistro);
		device.setPosicion(null);
		devices.put(idUsuario, device);
	}
	
	public String getIdDevice(final Integer idUsuario) {
		return devices.get(idUsuario).getIdRegisto();
	}
	
	public PosicionJSON getPosicion(final Integer idUsuario) {
		return devices.get(idUsuario).getPosicion();
	}
	
	public void removeDevice(final Integer idUsuario) {
		devices.remove(idUsuario);
	}
	
	public void putPosicion(final Integer idUsuario, final PosicionJSON posicion) {
		devices.get(idUsuario).setPosicion(posicion);
	}

}
