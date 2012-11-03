package com.geored.gui;


import java.util.List;

import com.geored.rest.R;
import com.geored.rest.ServicioRestAutenticacion;
import com.geored.rest.ServicioRestUsuarios;
import com.geored.rest.data.Categoria;
import com.geored.rest.data.Usuario;
import com.geored.rest.exception.NotFoundException;
import com.geored.rest.exception.RestBlowUpException;
import com.geored.rest.exception.UnauthorizedException;

import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;

public class RegistrarActivity extends GenericActivity {

	@Override
	protected void loadVista() {
		setContentView(R.layout.activity_registrar);
		//GetCategoriasAsyncTask task = new GetCategoriasAsyncTask();
		//task.execute(new String[] { "" });
		
		
	}
	
	public void showRegistrar(View clickedButton) {
		//Button b = (Button)findViewById(R.id.my_button);
		//b.setClickable(false);
		String emailText = ((EditText)findViewById(R.id.emailEditText)).getText().toString();
    	String passwordText = ((EditText)findViewById(R.id.passwordEditText)).getText().toString();
    	String passwordAgainText = ((EditText)findViewById(R.id.passwordAgainEditText)).getText().toString();
    	//showToast("<"+passwordText+">==<"+passwordAgainText+">");
    	if (passwordText.equals(passwordAgainText)){
    		blockGUI(R.id.registrar_button);
    		RegistryAsyncTask task = new RegistryAsyncTask();
    		 task.execute(new String[] { emailText,passwordText});
    	}else
    		showToast("los passwords no coinciden");        
    }
    
    protected String salvarUsuario(String name, String password) throws RestBlowUpException, UnauthorizedException, NotFoundException{
    	//showToast("Registar");
    	Usuario usuario = new Usuario();
    	usuario.setNombre(name);
    	
    	ServicioRestUsuarios.registrarUsuario(password, usuario);
    	return ServicioRestAutenticacion.login(name, password);
    	
    }
    
	private class RegistryAsyncTask extends AsyncTask<String, Void, String> {
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
	    		//goToActivity(UsuarioActivity.class);
	    		goToActivity(CategoriasActivity.class);
	    	}else{
	    		showToast(result);
	    	}	    	
	    	unBlockGUI(R.id.registrar_button);
	    }
	  }
/*
	protected List<Categoria> GetCategorias() throws RestBlowUpException, UnauthorizedException, NotFoundException{
    	
    	List<Categoria> cat = ServicioRestUsuarios.getCategorias();
    	
    	return cat;
    }
	    
			
	private class GetCategoriasAsyncTask extends AsyncTask<String, Void, List<Categoria>> {
	    @Override
	    protected List<Categoria> doInBackground(String... params) {
	  	  List<Categoria> cat = null;
	    	try {
		    	 cat = GetCategorias();
			} catch (RestBlowUpException e) {
				e.printStackTrace();
				return null;
			} catch (UnauthorizedException e) {
				e.printStackTrace();
				return null;
			} catch (NotFoundException e) {
				
				e.printStackTrace();
				return null;
			}
	   		return cat;
	    }
	
	    @Override
	    protected void onPostExecute( List<Categoria>  result) {
	    	if (result != null){
	    		showToast(Integer.toString(result.size()));
	    	}else{
	    		showToast("las categorias no se pudieron cargar");
	    	}    	
	    	
	    }
	  }
	  */
}
