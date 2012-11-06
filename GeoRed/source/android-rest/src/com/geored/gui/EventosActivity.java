package com.geored.gui;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.geored.rest.R;
import com.geored.rest.ServicioRestImagenes;
import com.geored.rest.ServicioRestUsuarios;
import com.geored.rest.data.Evento;
import com.geored.rest.exception.RestBlowUpException;
import com.geored.rest.exception.UnauthorizedException;


public class EventosActivity extends GenericActivity {
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	@Override
	protected void loadVista() {
		setContentView(R.layout.activity_comprar);
		loadListView();
	}
	
	private void loadListView() {
		Bundle extras = getIntent().getExtras();
		String nombre = extras.getString("eventoNombre");
		String descripcion = extras.getString("eventoDescripcion");
		String comienzo = extras.getString("eventoComienzo");
		String fin = extras.getString("eventoFin");
		((TextView) findViewById(R.id.textViewNombre)).setText(nombre);
		((TextView) findViewById(R.id.textViewDescripcion)).setText(descripcion);
		((TextView) findViewById(R.id.textViewComienzo)).setText(comienzo);
		((TextView) findViewById(R.id.textViewFin)).setText(fin);
		if (Integer.valueOf(getIntent().getExtras().getString("eventoId")) != 0) {
			progressBar.show();
			EventoAsyncTask task = new EventoAsyncTask();
			task.execute(new String[]{getIntent().getExtras().getString("eventoId")});
		}
	}
	
	private void loadListView(Bitmap bitmap) {
		showToast("Dibujando imagen");
		ImageView imageView = (ImageView) findViewById(R.id.imageViewEvento);
		imageView.setImageBitmap(bitmap);
	}
	
	private class EventoAsyncTask extends AsyncTask<String, Void, Evento> {
		
		@Override
		protected Evento doInBackground(String... params) {
			try {
				return ServicioRestUsuarios.getEvento(params[0]);
			} 
			catch (RestBlowUpException e) {
				Log.e("ERROR", e.getMessage(), e);
			}
			catch (UnauthorizedException e) {
				Log.e("Warning", e.getMessage(), e);
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Evento evento) {
			ImagenAsyncTask task = new ImagenAsyncTask();
			task.execute(new String[]{evento.getIdImagen().toString()});
		}
		
	}
	
	private class ImagenAsyncTask extends AsyncTask<String, Void, Bitmap> {
		
		@Override
		protected Bitmap doInBackground(String... params) {
			Bitmap bitmap = null;
			try {
				InputStream imagen = ServicioRestImagenes.bajarImagen(Integer.valueOf(params[0]));
				bitmap = BitmapFactory.decodeStream(imagen);
			} 
			catch (RestBlowUpException e) {
				Log.e("ERROR", e.getMessage(), e);
			}
			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			if (bitmap != null) {
				loadListView(bitmap);
			} 
			else {
				showToast("Error, resultado invalido de ofertas :(");
			}
			progressBar.dismiss();
		}
		
	}
	
}
