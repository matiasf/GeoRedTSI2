package com.geored.rest;

import com.geored.rest.R;
import com.geored.gui.*;
import com.geored.gui.map.MapsDemo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class Main extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    /** Switches to the given class (which must be a subclass of Activity). 
     *  You must also register the new Activity in AndroidManifest.xml.
     */
    private void goToActivity(Class<? extends Activity> activityClass) {
        Intent newActivity = new Intent(this, activityClass);
        startActivity(newActivity);
    }
    
    /** Switches to the ButtonActivity when the associated button is clicked. */
    
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

    public void showCheckIn(View clickedButton) {
        goToActivity(CheckInActivity.class);
    }
    
    public void showTestServicios(View clickedButton) {
        goToActivity(TestServicios.class);
    }
    
    public void showGPSLocation(View clickedButton) {
        goToActivity(MapsDemo.class);
    }    
    
}
