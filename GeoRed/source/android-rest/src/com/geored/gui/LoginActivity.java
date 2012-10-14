package com.geored.gui;

import com.geored.rest.R;
import com.geored.rest.ServicioRestAutenticacion;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends GenericActivity {

	protected void loadVista() {
    	setContentView(R.layout.activity_login);
    }

    public void showLogin(View clickedButton) {
    	if (doSomething()){
    		goToActivity(UsuarioActivity.class);
    	}else{
    		showToast("please enter the correct information");
    	}        
    }
    
    private boolean doSomething(){
    	try{
    		String emailText = ((EditText)findViewById(R.id.emailEditText)).getText().toString();
        	String passwordText = ((EditText)findViewById(R.id.passwordEditText)).getText().toString();
        	
        	usuarioId = ServicioRestAutenticacion.login(emailText, passwordText);        	
    		
        	showToast( usuarioId );
    	}catch(Exception ex){
    		showToast(ex.getMessage());
    		return false;
    	}
    	
    	return true;
    }
    
    
}
