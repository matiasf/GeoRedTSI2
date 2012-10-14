package com.geored.gui;

import com.geored.rest.Main;
import com.geored.rest.R;
import com.geored.rest.ServicioRestUsuarios;
import com.geored.rest.data.Usuario;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ModificarUsuarioActivity extends RegistrarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        Button botonModificar = ((Button)findViewById(R.id.registrar_button));
    	botonModificar.setText("Modificar");
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
    
    public void showRegistrar(View clickedButton) {
    	if (doSomething()){
    		goToActivity(UsuarioActivity.class);
    	}else{
    		
    	}        
    }
    
    protected String salvarUsuario(String name, String password){
    	//showToast("Modificar");
		Usuario usuario = new Usuario();
    	usuario.setNombre(name);
    	
    	ServicioRestUsuarios.modificarUsuario(password, usuario);
    	//return ServicioRestAutenticacion.login(name, password);
    	return "";
    }
    
    
}
