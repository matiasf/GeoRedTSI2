package com.geored.gui;

import com.geored.gui.map.MapsDemo;
import com.geored.rest.Main;
import com.geored.rest.R;
import com.geored.rest.ServicioRestAutenticacion;
import com.geored.rest.ServicioRestUsuarios;
import com.geored.rest.TestServicios;
import com.geored.rest.data.Usuario;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class UsuarioActivity extends Activity {

	private Usuario usuario = null;
	private String usuarioId;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
        	usuarioId = extras.getString("user_id");
            
            setUsuario(usuarioId);
        }
    }
    
    private void setUsuario(String id){
    	try{
    		this.usuario = ServicioRestUsuarios.getContacto(id);
    	}catch(Exception ex){
    		showToast(ex.getMessage());
    	}
    }
    private String getUsuario(){
    	if (usuario == null){
    		return usuarioId;    		
    	}else{
    		return usuario.getNombre();
    	}
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
        
        newActivity.putExtra("user_id",getUsuario());
        
        startActivity(newActivity);
    }
    
    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
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


}
