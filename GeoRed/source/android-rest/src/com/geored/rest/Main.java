package com.geored.rest;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.geored.gui.CategoriasActivity;
import com.geored.gui.CheckInActivity;
import com.geored.gui.FacebookActivity;
import com.geored.gui.GenericActivity;
import com.geored.gui.LoginActivity;
import com.geored.gui.NotificacionesActivity;
import com.geored.gui.RegistrarActivity;
import com.geored.gui.map.MapsDemo;

public class Main extends GenericActivity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBackPressed()
    {
    	//goToPreviousActivity();
        //Intent setIntent = new Intent(this,xxxxx.class);
        //startActivity(setIntent); 
        return;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
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
    
    public void showCategoria(View clickedButton) {
        goToActivity(CheckInActivity.class);
    }
}
