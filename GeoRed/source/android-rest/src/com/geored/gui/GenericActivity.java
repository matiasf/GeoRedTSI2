package com.geored.gui;

import java.util.Date;
import java.util.Hashtable;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.Toast;
import com.geored.rest.R;
import com.geored.rest.data.Notificacion;
import com.geored.rest.data.Usuario;

public class GenericActivity extends Activity {
	
	protected ProgressDialog progressBar;
	
	protected String usuarioId;
	protected Hashtable<String, Usuario>  hashUsuarios = new Hashtable<String,Usuario> ();
	protected double locationRange = 0.001;
	protected Location currentlocation; 
	protected Date lastDate = new Date();
	
	static public boolean showToast = false;
	static public Hashtable<String, Notificacion>  hashNotificaciones = new Hashtable<String,Notificacion> ();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        progressBar = new ProgressDialog(this);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setMessage("Por favor espere...");

        //este metodo tiene que ejecutar el setContentView(R.layout.activity_ 
        //y algo mas particular de cada vista hija/o
        loadVista();
        
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
        	usuarioId = extras.getString("user_id");        
        }    	
    }
    
    public void onBackPressed()
    {
    	goToPreviousActivity();
        //Intent setIntent = new Intent(this,xxxxx.class);
        //startActivity(setIntent); 
        return;
    } 
    
    protected void goToPreviousActivity(){	super.onBackPressed(); }
    protected void loadVista() {	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    protected void goToActivity(Class<? extends Activity> activityClass) {
        Intent newActivity = new Intent(this, activityClass);
        
        newActivity.putExtra("user_id", usuarioId);
        
        startActivity(newActivity);
    }
    
    protected void showToast(String text) {
    	if (showToast)
    		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    	
    	Log.d("Toast", text);
    }
    
	protected void blockGUI(int id) {
		Button b = (Button)findViewById(id);
		b.setClickable(false);
		
		progressBar.show();
	}
	
	protected void unBlockGUI(int id) {
		Button b = (Button)findViewById(id);
		b.setClickable(true);
		
		progressBar.dismiss();
	}
    

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
