package com.geored.rest;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.geored.rest.data.Usuario;
import com.geored.rest.exception.RestBlowUpException;
import com.geored.rest.exception.UnauthorizedException;

public class TestServicios extends Activity implements OnClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViewById(R.id.my_button).setOnClickListener(this);
    }

	@Override
	public void onClick(View arg0) {
		Button b = (Button)findViewById(R.id.my_button);
		b.setClickable(false);
		new LongRunningGetIO().execute();
	}
	
	private class LongRunningGetIO extends AsyncTask <Void, Void, String> {
		
		@Override
		protected String doInBackground(Void... params) {
			Usuario usuario = new Usuario();
			usuario.setNombre("fucken");
			String token = "";
			try {
				ServicioRestUsuarios.registrarUsuario("fucken", usuario);
				token = ServicioRestAutenticacion.login("fucken", "fucken");
			} catch (RestBlowUpException e) {
				return "Error: " + e.getMessage();
			} catch (UnauthorizedException e) {
				return "Error: " + e.getMessage();
			}
			return "Exit de exito! " + token;
		}	
		
		protected void onPostExecute(String results) {
			if (results!=null) {
				EditText et = (EditText)findViewById(R.id.my_edit);
				et.setText(results);
			}
			Button b = (Button)findViewById(R.id.my_button);
			b.setClickable(true);
		}
    }
}