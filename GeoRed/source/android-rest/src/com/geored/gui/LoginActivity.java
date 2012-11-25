package com.geored.gui;

import com.geored.rest.Main;
import com.geored.rest.R;
import com.geored.rest.ServicioRestAutenticacion;
import com.geored.rest.exception.RestBlowUpException;
import com.geored.rest.exception.UnauthorizedException;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends GenericActivity {

	
	private int tries = 2;
	
	protected void loadVista() {
		setContentView(R.layout.activity_login);
		tries = 0;
	}
	
	@Override
	protected void goToPreviousActivity(){	
        Intent setIntent = new Intent(this,Main.class);
        startActivity(setIntent); 
	}

	private void showLogin(){
		String emailText = ((EditText) findViewById(R.id.emailEditText))
				.getText().toString();
		String passwordText = ((EditText) findViewById(R.id.passwordEditText))
				.getText().toString();

		blockGUI(R.id.login_button);
		RegistryAsyncTask task = new RegistryAsyncTask();
		task.execute(new String[] { emailText, passwordText });

	}
	
	public void showLogin(View clickedButton) {
		showLogin();
	}

	private String login(String emailText, String passwordText)
			throws RestBlowUpException, UnauthorizedException {
		return ServicioRestAutenticacion.login(emailText, passwordText);
	}

	private class RegistryAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			try {
				usuarioId = login(params[0], params[1]);
			} catch (RestBlowUpException e) {
				Log.e("Error", "Rest blow up!", e);
				return "Rest blow up!";
			} catch (UnauthorizedException e) {
				Log.w("Warning", "Unautorized!", e);
				return "Unautorized!";
			}
			return "Exito";
		}
	
	    @Override
	    protected void onPostExecute(String result) {
	    	if (result.equals("Exito")){
	    		tries = 0;
	    		goToActivity(UsuarioActivity.class);
	    	}else{
	    		tries++;
	    		if (tries < 2){
	    			showLogin();
	    		}
	    		showToast(result);
	    	}	
	    	unBlockGUI(R.id.login_button);
	    }
	}
    
}
