package com.geored.gui;

import com.geored.rest.Main;
import com.geored.rest.R;
import com.geored.rest.ServicioRestAutenticacion;
import com.geored.rest.ServicioRestUsuarios;
import com.geored.rest.data.Usuario;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrarActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
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
        
        String id = ((EditText)findViewById(R.id.emailEditText)).getText().toString();
    	newActivity.putExtra("user_id",id); 
        
        startActivity(newActivity);
    }
    
    public void showRegistrar(View clickedButton) {
    	if (doSomething()){
    		goToActivity(UsuarioActivity.class);
    	}else{
    		
    	}        
    }
    
    protected String salvarUsuario(String name, String password){
    	//showToast("Registar");
    	Usuario usuario = new Usuario();
    	usuario.setNombre(name);
    	
    	ServicioRestUsuarios.registrarUsuario(password, usuario);
    	return ServicioRestAutenticacion.login(name, password);
    	
    }
    
    protected boolean doSomething(){
    	try{
    		String emailText = ((EditText)findViewById(R.id.emailEditText)).getText().toString();
        	String passwordText = ((EditText)findViewById(R.id.passwordEditText)).getText().toString();
        	String passwordAgainText = ((EditText)findViewById(R.id.passwordAgainEditText)).getText().toString();
        	//showToast("<"+passwordText+">==<"+passwordAgainText+">");
        	if (passwordText.equals(passwordAgainText)){
            	
        		String respuestaLogin = salvarUsuario(emailText, passwordText);
            	//showToast( respuestaLogin);        	
        	}else {
        		showToast("los password no coinciden");
        		return false;
        	}        		
    	}catch(Exception ex){
    		showToast(ex.getMessage());
    		return false;
    	}    	
    	
    	return true;
    }
    
    
    protected void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
    
}
