package com.geored.rest;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.geored.rest.data.Mensaje;
import com.geored.rest.exception.RestBlowUpException;
import com.geored.rest.exception.UnauthorizedException;
import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;

public class GCMIntentService extends GCMBaseIntentService {
	
	private final static String EXTRA_MESSAGE = "message";

	@Override
	protected void onError(Context context, String errorId) {
		Log.e(TAG, "Received error from GCM: " + errorId);
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		Log.i(TAG, "Received message");
		Mensaje mensaje = new Mensaje();
		mensaje.setIdUsuario(Integer.valueOf(intent.getExtras().getString("idUsuario")));
		mensaje.setMessage(intent.getExtras().getString("mensaje"));
		displayMessage(context, mensaje);
	}

	@Override
	protected void onRegistered(Context context, String regId) {
		Log.i(TAG, "Device registered: regId = " + regId);
		try {
			ServicioRestGCM.registrar(regId);
		} 
		catch (RestBlowUpException e) {
			GCMRegistrar.unregister(context);
			Log.w("Warning", e.getMessage(), e);
		} 
		catch (UnauthorizedException e) {
			GCMRegistrar.unregister(context);
			Log.w("Warning", e.getMessage(), e);
		} 
	}

	@Override
	protected void onUnregistered(Context context, String regId) {
		Log.i(TAG, "Device unregistered");
		if (GCMRegistrar.isRegisteredOnServer(context)) {
			try {
				ServicioRestGCM.desregistrar();
			} catch (RestBlowUpException e) {
				GCMRegistrar.unregister(context);
				Log.w("Warning", e.getMessage(), e);
			} catch (UnauthorizedException e) {
				GCMRegistrar.unregister(context);
				Log.w("Warning", e.getMessage(), e);
			}
		} else {
			Log.i(TAG, "Ignoring unregister callback");
		}
	}

	static void displayMessage(Context context, Mensaje mensaje) {
		Intent intent = new Intent("com.google.android.gcm.demo.app.DISPLAY_MESSAGE");
		intent.putExtra(EXTRA_MESSAGE, mensaje.getIdUsuario() + ": " + mensaje.getMessage());
		context.sendBroadcast(intent);
	}

}
