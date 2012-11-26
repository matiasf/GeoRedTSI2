package com.geored.gui;

import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.facebook.Session;
import com.geored.gui.map.MapsDemo;
import com.geored.gui.utils.Constantes;
import com.geored.rest.Main;
import com.geored.rest.R;
import com.geored.rest.ServicioRestAutenticacion;
import com.geored.rest.ServicioRestGCM;
import com.geored.rest.ServicioRestUsuarios;
import com.geored.rest.TestServicios;
import com.geored.rest.data.Notificacion;
import com.geored.rest.data.Posicion;
import com.geored.rest.exception.ConflictException;
import com.geored.rest.exception.NotFoundException;
import com.geored.rest.exception.RestBlowUpException;
import com.geored.rest.exception.UnauthorizedException;
import com.google.android.gcm.GCMRegistrar;

public class UsuarioActivity extends GenericActivity implements
		LocationListener {

	private LocationManager locManager;

	private final static String SENDER_ID = "786328023735";
	private final AsyncTask<Void, Void, Void> serverRegisterTask = new AsyncTask<Void, Void, Void>() {
		@Override
		protected Void doInBackground(Void... params) {
			try {
				ServicioRestGCM.registrar(GCMRegistrar
						.getRegistrationId(UsuarioActivity.this));
				GCMRegistrar.setRegisteredOnServer(UsuarioActivity.this, true);
			} catch (RestBlowUpException e) {
				Log.e("ERROR", e.getMessage(), e);
			} catch (UnauthorizedException e) {
				Log.e("ERROR", e.getMessage(), e);
			}
			return null;
		}
	};

	@Override
	protected void goToPreviousActivity() {
		Intent setIntent = new Intent(this, UsuarioActivity.class);
		startActivity(setIntent);
	}

	@Override
	protected void loadVista() {
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		if (GCMRegistrar.isRegistered(this)) {
			serverRegisterTask.execute();
		} else {
			GCMRegistrar.register(this, SENDER_ID);
		}
		setContentView(R.layout.activity_usuario);

		// Use the location manager through GPS
		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.NO_REQUIREMENT);
		criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
		criteria.setCostAllowed(false);

		String bestProvider = locManager.getBestProvider(criteria, true);

		// locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0,
		// this);
		locManager.requestLocationUpdates(bestProvider, 0, 0, this);

		// get the current location (last known location) from the location
		// manager
		// Location location =
		// locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		currentlocation = locManager.getLastKnownLocation(bestProvider);

		// if location found display as a toast the current latitude and
		// longitude
		if (currentlocation != null) {

			showToast("Current location:\nLatitude: "
					+ currentlocation.getLatitude() + "\n" + "Longitude: "
					+ currentlocation.getLongitude());

			showNotificaciones(currentlocation.getLatitude(),
					currentlocation.getLongitude());
		} else {

			showToast("No se puede obtener la ubicacion actual! Por favor encienda Internet or GPS");
		}

	}

	private void showNotificaciones(double lat, double lon) {

		NotificacionesAsyncTask task = new NotificacionesAsyncTask();

		Posicion[] posiciones = new Posicion[1];
		posiciones[0] = new Posicion();
		posiciones[0].setDistancia((double) 100);
		posiciones[0].setLatitud((double) lat);
		posiciones[0].setLongitud((double) lon);

		task.execute(posiciones);
	}

	@Override
	protected void onResume() {
		super.onResume();
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.NO_REQUIREMENT);
		criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
		criteria.setCostAllowed(false);

		String bestProvider = locManager.getBestProvider(criteria, true);
		locManager.requestLocationUpdates(bestProvider, 0, 0, this);

	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.NO_REQUIREMENT);
		criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
		criteria.setCostAllowed(false);

		String bestProvider = locManager.getBestProvider(criteria, true);
		Location location = locManager.getLastKnownLocation(bestProvider);
		if (location != null) {
			if (searchLocation(location)) {
				showNotificaciones(location.getLatitude(),
						location.getLongitude());
				currentlocation = location;
			}
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		locManager.removeUpdates(this); // activity pauses => stop listening for
										// updates
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void showContactos(View clickedButton) {
		goToActivity(ContactosActivity.class);
	}

	public void showInvitaciones(View clickedButton) {
		goToActivity(InvitacionesActivity.class);
	}

	public void showInvitarContacto(View clickedButton) {
		// goToActivity(InvitarContactoActivity.class);
	}

	public void showAceptarInvitacion(View clickedButton) {
		// goToActivity(AceptarInvitacionActivity.class);
	}

	public void showModificarUsuario(View clickedButton) {
		goToActivity(ModificarUsuarioActivity.class);
	}

	public void showLogin(View clickedButton) {
		Session session = Session.getActiveSession();
		if (session != null && !session.isClosed()) {
			session.closeAndClearTokenInformation();
		}
		GCMRegistrar.unregister(this);
		AsyncTask<Void, Void, Void> logoutTask = new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				try {
					ServicioRestAutenticacion.logout();
					goToActivity(Main.class);
				} catch (RestBlowUpException e) {
					Log.e("ERROR", e.getMessage(), e);
				} catch (UnauthorizedException e) {
					Log.w("Warning",
							"Ya estaba deslogueado: " + e.getMessage(), e);
					goToActivity(Main.class);
				}
				return null;
			}
		};
		logoutTask.execute();
	}

	/** Switches to the SpinnerActivity when the associated button is clicked. */

	public void showRegistrar(View clickedButton) {
		goToActivity(RegistrarActivity.class);
	}

	public void showFacebook(View clickedButton) {
		goToActivity(FacebookActivity.class);
	}

	public void showNotificaciones(View clickedButton) {
		goToActivity(NotificacionesActivity.class);
	}

	public void showNotificacionesSitio(View clickedButton) {
		goToActivity(NotificacionesActivity.class);
	}

	public void showNotificacionesLocal(View clickedButton) {
		goToActivity(NotificacionesActivity.class);
	}

	public void showCheckIn(View clickedButton) {
		goToActivity(CheckInActivity.class);
	}

	public void showTestServicios(View clickedButton) {
		goToActivity(TestServicios.class);
	}

	public void showGPSLocation(View clickedButton) {
		goToActivity(MapsDemo.class);
	}

	public void showBuscarContactos(View clickedButton) {
		goToActivity(BuscarContactosActivity.class);
	}

	public void showOfertaTest(View clickedButton) {
		goToActivity(OfertasActivity.class);
	}

	public void showNotificacionesLocales(View clickedButton) {
		goToActivity(NotificacionesOfertasActivity.class);
	}

	public void showNotificacionesEventos(View clickedButton) {

		goToActivity(NotificacionesEventosActivity.class);
	}

	public void enviarInvitacionExterna(View clickedButton) {
		final AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				final String email = ((EditText) findViewById(R.id.editTextEnvioInvitacion))
						.getText().toString();
				try {
					ServicioRestUsuarios.enviarInvitacionExterna(email);
				} catch (RestBlowUpException e) {
					Log.e("ERROR", e.getMessage(), e);
				} catch (UnauthorizedException e) {
					Log.w("Warining", e.getMessage());
				} catch (ConflictException e) {
					Log.w("Warning", e.getMessage());
				}
				return null;
			}
		};
		asyncTask.execute();
	}

	@Override
	public void onLocationChanged(Location location) {
		if (location != null) {
			if (searchLocation(location)) {
				this.currentlocation = location;

				showToast("Current location:\nLatitude: "
						+ location.getLatitude() + "\n" + "Longitude: "
						+ location.getLongitude());
				showNotificaciones(location.getLatitude(),
						location.getLongitude());
			}
		} else {

			showToast("No se puede obtener la ubicacion actual! Por favor encienda Internet or GPS");
		}

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	}

	private class NotificacionesAsyncTask extends
			AsyncTask<Posicion, Void, List<Notificacion>> {
		@Override
		protected List<Notificacion> doInBackground(Posicion... posicions) {
			List<Notificacion> notificaciones;
			try {
				notificaciones = ServicioRestUsuarios
						.getNotificaciones(posicions[0]);
			} catch (RestBlowUpException e) {
				e.printStackTrace();
				return null;
			} catch (NotFoundException e) {
				e.printStackTrace();
				return null;
			} catch (UnauthorizedException e) {
				e.printStackTrace();
				return null;
			}

			return notificaciones;
		}

		@Override
		protected void onPostExecute(List<Notificacion> result) {
			if (result != null) {
				loadNotifications(result);
				// goToActivity(UsuarioActivity.class);
			} else {
				showToast("error");
			}
		}

		private void loadNotifications(List<Notificacion> result) {
			try {
				Button button = (Button) findViewById(R.id.notificacionesSitio_button);
				String texto = getString(R.string.sitioDInteres);

				Button buttonOfertas = (Button) findViewById(R.id.notificaciones_button);
				String textoOfertas = getString(R.string.notificacionesLocales);

				Button buttonEventos = (Button) findViewById(R.id.notificacionesEventos_button);
				String textoEventos = getString(R.string.notificacionesEventos);

				if (result != null && result.size() > 0) {
					int contadorSitioInteres = 0;
					int contadorEventos = 0;
					int contadorLocal = 0;
					for (int i = 0; i < result.size(); i++) {
						// SITIO_DE_INTERES, EVENTO, LOCAL, CHECK_IN
						if (!GenericActivity.hashNotificaciones
								.containsKey(result.get(i).getId())) {
							if (result
									.get(i)
									.getTipo()
									.equalsIgnoreCase(
											Constantes.TipoNotifiacion.SITIO_DE_INTERES
													.toString())
									|| result
											.get(i)
											.getTipo()
											.equalsIgnoreCase(
													Constantes.TipoNotifiacion.SITIO_DE_INTERES_INTEGRACION
															.toString()))
								contadorSitioInteres++;
							if (result
									.get(i)
									.getTipo()
									.equalsIgnoreCase(
											Constantes.TipoNotifiacion.EVENTO
													.toString()))
								contadorEventos++;
							if (result
									.get(i)
									.getTipo()
									.equalsIgnoreCase(
											Constantes.TipoNotifiacion.LOCAL
													.toString())
									|| result
											.get(i)
											.getTipo()
											.equalsIgnoreCase(
													Constantes.TipoNotifiacion.LOCAL_INTEGRACION
															.toString()))
								contadorLocal++;
						}
					}
					button.setText(texto + " (" + contadorSitioInteres + ")");
					buttonOfertas.setText(textoOfertas + " (" + contadorLocal
							+ ")");
					buttonEventos.setText(textoEventos + " (" + contadorEventos
							+ ")");
				} else {
					button.setText(texto + " (0)");
					buttonOfertas.setText(textoOfertas + " (0)");
					buttonEventos.setText(textoEventos + " (0)");
				}
			} catch (Exception ex) {
				showToast(ex.getMessage());
			}
		}
	}

}
