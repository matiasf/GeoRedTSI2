package com.geored.gui;

import com.geored.gui.map.MapsDemo;
import com.geored.rest.R;
import com.geored.rest.TestServicios;


import android.view.Menu;
import android.view.View;

public class UsuarioActivity extends GenericActivity {

	@Override
	protected void loadVista() {
		setContentView(R.layout.activity_usuario);
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

}
