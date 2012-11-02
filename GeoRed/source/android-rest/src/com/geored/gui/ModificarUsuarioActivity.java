package com.geored.gui;

import com.geored.rest.R;
import com.geored.rest.ServicioRestUsuarios;
import com.geored.rest.data.Usuario;
import com.geored.rest.exception.NotFoundException;
import com.geored.rest.exception.RestBlowUpException;
import com.geored.rest.exception.UnauthorizedException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ModificarUsuarioActivity extends RegistrarActivity {

	//private String usuarioId ;
	
	@Override
	protected void loadVista() {
	    setContentView(R.layout.activity_registrar);
        Button botonModificar = ((Button)findViewById(R.id.registrar_button));
    	botonModificar.setText("Modificar");
    	
    	Bundle extras = getIntent().getExtras();
        if (extras != null) {
        	objectToScreen();
        }
	}
	

    private void objectToScreen() {
    	try{
    		progressBar.show();
    		ObtenerUsuarioAsyncTask task = new ObtenerUsuarioAsyncTask();
    		task.execute(new String[] { usuarioId});
    	}catch(Exception ex){
    		showToast(ex.getMessage());
    	}    	
    }
    
    
    public void showRegistrar(View clickedButton) {
    	String emailText = ((EditText)findViewById(R.id.emailEditText)).getText().toString();
    	String passwordText = ((EditText)findViewById(R.id.passwordEditText)).getText().toString();
    	String passwordAgainText = ((EditText)findViewById(R.id.passwordAgainEditText)).getText().toString();
    	//showToast("<"+passwordText+">==<"+passwordAgainText+">");
    	if (passwordText.equals(passwordAgainText)){
    		blockGUI(R.id.registrar_button);
    		ModificarAsyncTask task = new ModificarAsyncTask();
    		task.execute(new String[] { emailText,passwordText});
    	}else
    		showToast("los passwords no coinciden");       
    }
    
    protected String salvarUsuario(String name, String password) throws RestBlowUpException, NotFoundException, UnauthorizedException{
    	//showToast("Modificar");
		Usuario usuario = new Usuario();
    	usuario.setNombre(name);
    	
    	ServicioRestUsuarios.modificarUsuario(password, usuario);
    	
    	return usuarioId;
    }
    
	private class ModificarAsyncTask extends AsyncTask<String, Void, String> {
	    @Override
	    protected String doInBackground(String... params) {
	      try {
			usuarioId = salvarUsuario(params[0],params[1]);
		} catch (RestBlowUpException e) {
			e.printStackTrace();
			return "El servicio no responde";
		} catch (UnauthorizedException e) {
			e.printStackTrace();
			return "El usuario no esta autorizado";
		} catch (NotFoundException e) {
			
			e.printStackTrace();
			return "No esta servicio";
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
	    	unBlockGUI(R.id.registrar_button);
	    }
	  }

	
	private class ObtenerUsuarioAsyncTask extends AsyncTask<String, Void, Usuario> {
	    @Override
	    protected Usuario  doInBackground(String... params) {
	    	Usuario usuario = null;
	    	try {
	    		usuario = ServicioRestUsuarios.getContacto(usuarioId);
			} catch (RestBlowUpException e) {
				e.printStackTrace();
				return null;
			} catch (NotFoundException e) {
				e.printStackTrace();
				return null;
			} catch (UnauthorizedException e) {
				e.printStackTrace();
				return null;
			}
			
			return usuario;
	    }
	
	    @Override
	    protected void onPostExecute(Usuario result) {
	    	if (result != null){
	    		if (result != null){
	    			EditText emailEditText = ((EditText)findViewById(R.id.emailEditText));
	            	emailEditText.setText(result.getNombre());	
	    		}
	    	}else{
	    		showToast("error");
	    	}	 
	    	progressBar.dismiss();
	    }
	  }


    
}
