package com.geored.rest;

import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.geored.rest.data.Categoria;
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
			try {
				ServicioRestUsuarios.registrarUsuario("trollencio", usuario);
				ServicioRestAutenticacion.login("trollencio", "trollencio");
				List<Categoria> categorias = ServicioRestUsuarios.getCategorias();
				return "Hay " + categorias.size() + " categorias!";
			} catch (Exception e) {
				return "Error: " + e.getMessage();
			}
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