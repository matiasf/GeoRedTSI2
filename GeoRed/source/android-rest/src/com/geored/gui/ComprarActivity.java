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
import android.widget.EditText;
import android.widget.ImageView;

import com.geored.rest.R;
import com.geored.rest.ServicioRestImagenes;
import com.geored.rest.ServicioRestUsuarios;
import com.geored.rest.data.Pago;
import com.geored.rest.exception.NotFoundException;
import com.geored.rest.exception.RestBlowUpException;
import com.geored.rest.exception.UnauthorizedException;

public class ComprarActivity extends GenericActivity {
	
	private String idOferta;
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	public void showNotificaciones(View clickedButton) {
		ComprarAsyncTask task = new ComprarAsyncTask();
		task.execute();
	}
	
	@Override
	protected void loadVista() {
		setContentView(R.layout.activity_comprar);
		loadListView();
	}
		
	private void loadListView() {
		progressBar.show();
		ImagenAsyncTask task = new ImagenAsyncTask();
		Bundle extras = getIntent().getExtras();
		idOferta = extras.getString("idOferta");
		String[] params = new String[]{extras.getString("idImagen")};
		task.execute(params);
	}
	
	private void loadListView(Bitmap bitmap) {
		showToast("Dibujando imagen");
		ImageView imageView = (ImageView) findViewById(R.id.imageView);
		imageView.setImageBitmap(bitmap);
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
	
	private class ComprarAsyncTask extends AsyncTask<String, Void, Void> {
		
		@Override
		protected Void doInBackground(String... params) {
			try {
				Pago pago = new Pago();
				pago.setComentario(((EditText) findViewById(R.id.valorText)).getText().toString());
				pago.setEvaluacion(Integer.valueOf(((EditText) findViewById(R.id.comentarText)).getText().toString()));
				ServicioRestUsuarios.comprarOferta(Integer.valueOf(idOferta), pago);
				goToActivity(NotificacionesOfertasActivity.class);
			} 
			catch (RestBlowUpException e) {
				Log.e("ERROR", e.getMessage(), e);
			}
			catch (NotFoundException e) {
				Log.e("Warning", e.getMessage(), e);
			}
			catch (UnauthorizedException e) {
				Log.e("Warning", e.getMessage(), e);
			}
			return null;
		}
		
	}

}
