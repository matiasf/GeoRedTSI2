package com.geored.gui;


import java.util.List;

import com.geored.gui.map.MapsDemo;
import com.geored.rest.R;
import com.geored.rest.ServicioRestUsuarios;
import com.geored.rest.TestServicios;
import com.geored.rest.data.Categoria;
import com.geored.rest.data.Notificacion;
import com.geored.rest.data.Posicion;
import com.geored.rest.exception.NotFoundException;
import com.geored.rest.exception.RestBlowUpException;
import com.geored.rest.exception.UnauthorizedException;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class UsuarioActivity extends GenericActivity implements LocationListener {

	private LocationManager locManager; 
	
	
	@Override
	protected void loadVista() {
		setContentView(R.layout.activity_usuario);
		
	     // Use the location manager through GPS
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.NO_REQUIREMENT);
        criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
        criteria.setCostAllowed(false);     

        String bestProvider = locManager.getBestProvider(criteria, true);  
        
        //locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, this);
        locManager.requestLocationUpdates(bestProvider, 0, 0, this);

        //get the current location (last known location) from the location manager
        //Location location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location location = locManager.getLastKnownLocation(bestProvider);
        
        //if location found display as a toast the current latitude and longitude
        if (location != null) {

            Toast.makeText(
                    this,
                    "Current location:\nLatitude: " + location.getLatitude()
                            + "\n" + "Longitude: " + location.getLongitude(),
                    Toast.LENGTH_LONG).show();
            
            showNotificaciones((int)(location.getLatitude()*1E6),(int)(location.getLongitude() *1E6));
         } else {

            Toast.makeText(this, "Cannot fetch current location! Please turn of Internet or GPS",
                    Toast.LENGTH_LONG).show();
        }
        

	}
	
    private void showNotificaciones(double lat,double lon){
    	
    	NotificacionesAsyncTask task = new NotificacionesAsyncTask();
        
        Posicion[] posiciones = new Posicion[1];
        posiciones[0] = new Posicion();
        posiciones[0].setDistancia((float) 100);
        posiciones[0].setLatitud((float) lat);
        posiciones[0].setLongitud((float)lon);
        
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
        locManager.requestLocationUpdates(bestProvider, 0,0, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        locManager.removeUpdates(this); //activity pauses => stop listening for updates
    }
    
	private class NotificacionesAsyncTask extends AsyncTask<Posicion, Void, List<Notificacion>> {
	    @Override
	    protected List<Notificacion> doInBackground(Posicion... posicions) {
	      List<Notificacion> notificaciones;
			try {
				notificaciones = ServicioRestUsuarios.getNotificaciones(posicions[0]);
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
	    	if (result != null){
	    		loadNotifications(result);
	    		//goToActivity(UsuarioActivity.class);
	    	}else{
	    		showToast("error");
	    	}
	    }

		private void loadNotifications(List<Notificacion> result) {
			try{    	
				  Button button = (Button) findViewById(R.id.notificacionesSitio_button);
				  String texto = getString(R.string.sitioDInteres);
				  
				  if (result != null && result.size() > 0){ 
					  button.setText(texto+" ("+result.size()+")");  		
				  }else{
					  button.setText(texto+" (0)");  
				  }
	    	}catch(Exception ex){
	    		showToast(ex.getMessage());    		
	    	}
			
		}
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
    	//goToActivity(InvitarContactoActivity.class);
    }
    public void showAceptarInvitacion(View clickedButton) {
    	//goToActivity(AceptarInvitacionActivity.class);
    }
    public void showModificarUsuario(View clickedButton) {
    	goToActivity(ModificarUsuarioActivity.class);
    }
    
    
    public void showLogin(View clickedButton) {
        goToActivity(LoginActivity.class);
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

	public void showGCMTest(View clickedButtom) {
    	goToActivity(GCMDemoActivity.class);
    }
    
    @Override
	public void onLocationChanged(Location location) {
		if (location != null) {

            Toast.makeText(
                    this,
                    "Current location:\nLatitude: " + location.getLatitude()
                            + "\n" + "Longitude: " + location.getLongitude(),
                    Toast.LENGTH_LONG).show();
            showNotificaciones((int)(location.getLatitude()*1E6),(int)(location.getLongitude() *1E6));
            
        } else {

            Toast.makeText(this, "Cannot fetch current location! Please turn of Internet or GPS",
                    Toast.LENGTH_LONG).show();
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
	

	

}
