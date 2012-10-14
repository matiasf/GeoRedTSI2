package com.geored.gui;

import com.geored.rest.R;
import com.geored.rest.ServicioRestUsuarios;
import com.geored.rest.data.Usuario;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ModificarUsuarioActivity extends RegistrarActivity {

	private String usuarioId ;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        Button botonModificar = ((Button)findViewById(R.id.registrar_button));
    	botonModificar.setText("Modificar");
    	
    	Bundle extras = getIntent().getExtras();
        if (extras != null) {
        	usuarioId = extras.getString("user_id");
        	objectToScreen();
        }
    }
   
    private void objectToScreen() {
    	try{
    		Usuario usuario = ServicioRestUsuarios.getContacto(usuarioId);
    		if (usuario != null){
    			EditText emailEditText = ((EditText)findViewById(R.id.emailEditText));
            	emailEditText.setText(usuario.getNombre());	
    		}
    	}catch(Exception ex){
    		showToast(ex.getMessage());
    	}    	
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
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
    	
    	return usuarioId;
    }
    
    
}
