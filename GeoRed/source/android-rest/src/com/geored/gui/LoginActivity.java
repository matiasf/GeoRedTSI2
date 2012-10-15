package com.geored.gui;

import com.geored.rest.R;
import com.geored.rest.ServicioRestAutenticacion;
import com.geored.rest.exception.RestBlowUpException;
import com.geored.rest.exception.UnauthorizedException;

import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends GenericActivity {

	protected void loadVista() {
    	setContentView(R.layout.activity_login);
    }

    public void showLogin(View clickedButton) {
    	String emailText = ((EditText)findViewById(R.id.emailEditText)).getText().toString();
    	String passwordText = ((EditText)findViewById(R.id.passwordEditText)).getText().toString();
    	
    	RegistryAsyncTask task = new RegistryAsyncTask();
		task.execute(new String[] { emailText,passwordText});
    }
    
    private String login(String emailText,String passwordText) throws RestBlowUpException, UnauthorizedException{
    		
        	return ServicioRestAutenticacion.login(emailText, passwordText);        	
    		
    }
	private class RegistryAsyncTask extends AsyncTask<String, Void, String> {
	    @Override
	    protected String doInBackground(String... params) {
	      try {
			usuarioId = login(params[0],params[1]);
		} catch (RestBlowUpException e) {
			e.printStackTrace();
			return "El servicio no responde";
		} catch (UnauthorizedException e) {
			e.printStackTrace();
			return "El usuario no esta autorizado";
		}
	   		return "Exito";
	    }
	
	    @Override
	    protected void onPostExecute(String result) {
	    	if (result.equals("Exito")){
	    		goToActivity(UsuarioActivity.class);
	    	}else{
	    		showToast(result);
	    	}	    	
	    }
	  }
    
}
