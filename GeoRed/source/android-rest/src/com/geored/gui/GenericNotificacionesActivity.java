package com.geored.gui;

import java.util.Date;
import java.util.List;

import com.geored.rest.R;
import com.geored.rest.ServicioRestUsuarios;
import com.geored.rest.data.Notificacion;
import com.geored.rest.data.Posicion;
import com.geored.rest.exception.NotFoundException;
import com.geored.rest.exception.RestBlowUpException;
import com.geored.rest.exception.UnauthorizedException;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class GenericNotificacionesActivity extends MapActivity implements
		LocationListener {

	private LocationManager locManager;
	private MapController controller;
	protected MapView mapView;
	private NotificacionesItemizedOverlay itemizedoverlay;

	private ProgressDialog progressBar;

	private double locationRange = 0.001;
	private Location currentlocation; 
	private Date lastDate = new Date();
	
	@Override
    public void onBackPressed()
    {
        Intent setIntent = new Intent(this,UsuarioActivity.class);
        startActivity(setIntent); 
        return;
    } 
	
    protected void showToast(String text) {
    	if (GenericActivity.showToast)
    		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    	
    	
    	Log.d("Toast", text);
    }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notificaciones);

		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);

		mapView.setBuiltInZoomControls(true);

		progressBar = new ProgressDialog(this);
		progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressBar.setMessage("Por favor espere...");

		// Use the location manager through GPS
		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.NO_REQUIREMENT);
		criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
		criteria.setCostAllowed(false);

		String bestProvider = locManager.getBestProvider(criteria, true);

		locManager.requestLocationUpdates(bestProvider, 0, 0, this);

		// get the current location (last known location) from the location
		// manager
		currentlocation = locManager.getLastKnownLocation(bestProvider);

		// if location found display as a toast the current latitude and
		// longitude
		if (currentlocation != null) {

			showToast(
					"Current location:\nLatitude: " + currentlocation.getLatitude()
							+ "\n" + "Longitude: " + currentlocation.getLongitude()
					);

			GeoPoint point = new GeoPoint((int) (currentlocation.getLatitude() * 1E6),
					(int) (currentlocation.getLongitude() * 1E6));

			// get the MapController object
			controller = mapView.getController();

			// animate to the desired point
			controller.animateTo(point);

			// set the map zoom to 13
			// zoom 1 is top world view
			// controller.setZoom(17);
			controller.setZoom(15);

			List<Overlay> mapOverlays = mapView.getOverlays();
			Drawable drawable = this.getResources().getDrawable(
					R.drawable.androidmarker);
			itemizedoverlay = new NotificacionesItemizedOverlay(drawable, this);

			OverlayItem overlayitem = new OverlayItem(point, "Hola, Mundo!",
					"I'm in Uruguay!");

			itemizedoverlay.addOverlay(overlayitem);
			mapOverlays.add(itemizedoverlay);

			// invalidate the map in order to show changes
			mapView.invalidate();

			controller.animateTo(point);
			showNotificaciones(currentlocation.getLatitude(), currentlocation.getLongitude());
		} else {
			showToast( "No se puede obtener la ubicacion actual! Por favor encienda Internet or GPS");
		}

	}

	private void showNotificaciones(double lat, double lon) {
		progressBar.show();

		NotificacionesAsyncTask task = new NotificacionesAsyncTask();

		Posicion[] posiciones = new Posicion[1];
		posiciones[0] = new Posicion();
		posiciones[0].setDistancia((double) 100);
		posiciones[0].setLatitud((double) lat);
		posiciones[0].setLongitud((double) lon);

		task.execute(posiciones);
	}

	/* When the activity starts up, request updates */
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

	protected void goToActivity(Class<? extends Activity> activityClass) {
		Intent newActivity = new Intent(this, activityClass);

		startActivity(newActivity);
	}


	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	public void onLocationChanged(Location location) {
		if (location != null) {
			if (searchLocation(location)){
				showToast(						
						"Current location:\nLatitude: " + location.getLatitude()
								+ "\n" + "Longitude: " + location.getLongitude());
				GeoPoint point = new GeoPoint((int) (location.getLatitude() * 1E6),
						(int) (location.getLongitude() * 1E6));

				// controller.setZoom(17);
				controller.animateTo(point);

				OverlayItem o = new OverlayItem(point, null, null);
				itemizedoverlay.clear();
				itemizedoverlay.addOverlay(o);

				// add the overlay item
				mapView.getOverlays().clear();
				mapView.getOverlays().add(itemizedoverlay);
				mapView.invalidate();

				showNotificaciones(location.getLatitude(), location.getLongitude());	
				currentlocation = location;
			}			
		} else {

			showToast("Cannot fetch current location!");
		}
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
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
				showToast("error al traer las notificaciones");
			}
			progressBar.dismiss();
		}

		
	}
	
	protected void loadNotifications(List<Notificacion> result) { }
	
	protected boolean searchLocation(Location location){
		
		boolean retVal =  currentlocation == null || (Math.abs(location.getLatitude()-currentlocation.getLatitude()) > locationRange 
		&& Math.abs(location.getLongitude()-currentlocation.getLongitude()) > locationRange );
		
		Date currentDate = new Date();
		if (!retVal){
			retVal = Math.abs(currentDate.getMinutes() - lastDate.getMinutes()) > 0;
			if (retVal){
				showToast("paso un minuto");
				lastDate = currentDate;
			}					
		}				
		return retVal;
	}


}
