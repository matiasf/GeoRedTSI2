package com.geored.gui;

import com.geored.rest.Main;
import com.geored.rest.R;
import com.geored.rest.ServicioRestAutenticacion;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
        	
    		String token = ServicioRestAutenticacion.login(emailText, passwordText);
        	
        	//showToast( token);
    	}catch(Exception ex){
    		showToast(ex.getMessage());
    		return false;
    	}
    	
    	return true;
    }
    
    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
