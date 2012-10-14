package com.geored.gui;


import com.geored.rest.R;
import com.geored.rest.ServicioRestAutenticacion;
import com.geored.rest.ServicioRestUsuarios;
import com.geored.rest.data.Usuario;
import com.geored.rest.exception.NotFoundException;
import com.geored.rest.exception.RestBlowUpException;
import com.geored.rest.exception.UnauthorizedException;

import android.view.View;
import android.widget.EditText;

public class RegistrarActivity extends GenericActivity {

	@Override
	protected void loadVista() {
		setContentView(R.layout.activity_registrar);
	}
	
	public void showRegistrar(View clickedButton) {
    	if (doSomething()){
    		goToActivity(UsuarioActivity.class);
    	}        
    }
    
    protected String salvarUsuario(String name, String password) throws RestBlowUpException, UnauthorizedException, NotFoundException{
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
            	
        		usuarioId = salvarUsuario(emailText, passwordText);
        		
        		showToast( usuarioId);        	
        	}else {
        		showToast("los password no coinciden");
        		return false;
        	}        		
    	}catch(RestBlowUpException exbu){
    		
    		showToast("El servicio no responde");
        	
    		return false;
    	}catch(UnauthorizedException exu){
    		
    		showToast("El usuario no esta autorizado");
    		
    		return false;
    	}catch(Exception ex){    		
    		showToast(ex.getMessage());
    		return false;
    	}
    	
    	return true;
    }
    
    
}
