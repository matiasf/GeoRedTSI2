package com.geored.gui;

import android.os.AsyncTask;
import android.util.Log;
import android.view.Menu;

import com.geored.rest.R;
import com.geored.rest.ServicioRestGCM;
import com.geored.rest.data.Mensaje;
import com.geored.rest.exception.NotFoundException;
import com.geored.rest.exception.RestBlowUpException;
import com.geored.rest.exception.UnauthorizedException;
import com.google.android.gcm.GCMRegistrar;

public class GCMDemoActivity extends GenericActivity {

	private final static String SENDER_ID = "786328023735";
	private final static String EXTRA_MESSAGE = "message";
	AsyncTask<Void, Void, Void> mRegisterTask;

	@Override
	protected void loadVista() {
		//Drawable draw = this.getResources().getDrawable(R.drawable.androidmarker);
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		final String regId = GCMRegistrar.getRegistrationId(this);
		if (regId.equals("")) {
			GCMRegistrar.register(this, SENDER_ID);
		} else {
			Log.i("Info", "Already registered");
			mRegisterTask = new AsyncTask<Void, Void, Void>() {
				@Override
				protected Void doInBackground(Void... params) {
					try {
						ServicioRestGCM.registrar(regId);
						Mensaje mensaje = new Mensaje();
						mensaje.setIdUsuario(1);
						mensaje.setMessage("Hola GCM!");
						ServicioRestGCM.enviarMensaje(mensaje);
					} catch (RestBlowUpException e) {
						Log.e("Error", "Rest blow up!", e);
					} catch (UnauthorizedException e) {
						Log.w("Warning", "Unautorized!", e);
					} catch (NotFoundException e) {
						Log.w("Warning", "Not found!", e);
					}
					return null;
				}				
			};
			mRegisterTask.execute();
		}
	/*registerReceiver(mHandleMessageReceiver, new IntentFilter(
				"com.google.android.gcm.demo.app.DISPLAY_MESSAGE"));*/
		setContentView(R.layout.activity_chat);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	/*private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
			Toast.makeText(GCMDemoActivity.this, newMessage, Toast.LENGTH_LONG)
					.show();
		}
	};*/
}
