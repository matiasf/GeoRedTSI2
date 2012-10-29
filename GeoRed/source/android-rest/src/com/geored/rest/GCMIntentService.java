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

	@Override
	protected void onError(Context context, String errorId) {
		Log.i(TAG, "Received error: " + errorId);
		// displayMessage(context, getString(R.string.gcm_error, errorId));
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		Log.i(TAG, "Received message");
		//String message = getString(R.string.gcm_message);
		displayMessage(context, "REgistrado minieri");
	}

	@Override
	protected void onRegistered(Context context, String regId) {
		Log.i(TAG, "Device registered: regId = " + regId);
		displayMessage(context, "");
		try {
			ServicioRestGCM.registrar(regId);
			Mensaje mensaje = new Mensaje();
			mensaje.setIdUsuario(1);
			mensaje.setMessage("Hola GCM!");
			ServicioRestGCM.enviarMensaje(mensaje);
		} catch (RestBlowUpException e) {
			GCMRegistrar.unregister(context);
			Log.w("Warning", e.getMessage(), e);
		} catch (UnauthorizedException e) {
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

	static void displayMessage(Context context, String message) {
		Intent intent = new Intent("com.google.android.gcm.demo.app.DISPLAY_MESSAGE");
		intent.putExtra("message", message);
		context.sendBroadcast(intent);
	}

}
