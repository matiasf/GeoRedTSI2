package com.geored.gui;

import com.geored.rest.R;
import com.geored.rest.ServicioRestAutenticacion;
import com.geored.rest.exception.RestBlowUpException;
import com.geored.rest.exception.UnauthorizedException;

import android.view.View;
import android.widget.EditText;

public class LoginActivity extends GenericActivity {

	protected void loadVista() {
    	setContentView(R.layout.activity_login);
    }

    public void showLogin(View clickedButton) {
    	if (doSomething()){
    		goToActivity(UsuarioActivity.class);
    	}        
    }
    
    private boolean doSomething(){
    	try{
    		String emailText = ((EditText)findViewById(R.id.emailEditText)).getText().toString();
        	String passwordText = ((EditText)findViewById(R.id.passwordEditText)).getText().toString();
        	
        	usuarioId = ServicioRestAutenticacion.login(emailText, passwordText);        	
    		
        	showToast( usuarioId );
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
