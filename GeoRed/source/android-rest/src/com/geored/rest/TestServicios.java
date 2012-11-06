package com.geored.rest;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.geored.rest.data.Imagen;
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
				//ServicioRestUsuarios.registrarUsuario("trollencio", usuario);
				//ServicioRestAutenticacion.login("trollencio", "trollencio");
				//List<Categoria> categorias = ServicioRestUsuarios.getCategorias();
				FileInputStream input = new FileInputStream("/mnt/sdcard/Pictures/icon.png");
				byte [] buffer = new byte[1024];
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				while (input.read(buffer) > 0) {
					output.write(buffer);
				}
				Imagen imagen = ServicioRestImagenes.subirImagen(output.toByteArray());
				ServicioRestImagenes.bajarImagen(imagen.getId());
				return "Hay Imagenes!";
			} catch (Exception e) {
				Log.i("Info", "Exploto el test", e);
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