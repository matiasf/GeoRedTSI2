package com.geored.rest;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.geored.rest.data.CheckIn;
import com.geored.rest.data.Notificacion;
import com.geored.rest.data.Posicion;
import com.geored.rest.data.Usuario;

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
			usuario.setNombre("trollencio");
			String token = "";
			try {
				ServicioRestUsuarios.registrarUsuario("trollencio", usuario);
				token = ServicioRestAutenticacion.login("trollencio", "trollencio");
				for (Usuario contacto : ServicioRestUsuarios.buscarContactos("troll")) {
					token += "Buscado: " + contacto.getNombre() + "\n";
				}
				Posicion posicion = new Posicion();
				posicion.setDistancia((float)20);
				posicion.setLatitud((float)0);
				posicion.setLongitud((float)0);
				for (Notificacion notifiacion : ServicioRestUsuarios.getNotificaciones(posicion)) {
					token += "Notifiacion: " + notifiacion.getNombre();
				}
				CheckIn checkin = new CheckIn();
				checkin.setComentario("que bueno que esta!");
				ServicioRestSitiosInteres.hacerCheckIn("0", checkin);
			} catch (Exception e) {
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