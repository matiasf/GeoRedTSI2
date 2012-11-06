package com.geored.gui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.geored.rest.R;
import com.geored.rest.ServicioRestUsuarios;
import com.geored.rest.data.Oferta;
import com.geored.rest.exception.RestBlowUpException;
import com.geored.rest.exception.UnauthorizedException;

public class OfertasActivity extends GenericActivity {
	
	private String value;
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_compras, menu);
		Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("ofertas_id");
            
        }
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		Oferta oferta = (Oferta) getListAdapter().getItem(info.position);
		switch (item.getItemId()) {
			case R.id.menu_comprar:
				goToActivity(ComprarActivity.class, oferta.getId(), oferta.getIdImagen());
				return true;
			default:
				return super.onContextItemSelected(item);
		}
	}
	
	@Override
	protected void loadVista() {
		setContentView(R.layout.activity_ofertas);
		Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("ofertas_id");
            
        }
		loadListView();
		registerForContextMenu(getListView());
	}
	
	protected void goToActivity(Class<? extends Activity> activityClass, Integer idOferta, Integer idImagen) {
        Intent newActivity = new Intent(OfertasActivity.this, activityClass);
        newActivity.putExtra("idOferta", idOferta.toString());
        newActivity.putExtra("idImagen", idImagen.toString());
        startActivity(newActivity);
    }

	private void setListAdapter(ArrayAdapter<Oferta> adapter) {
		getListView().setAdapter(adapter);
	}
	
	private ListAdapter getListAdapter() {
		return getListView().getAdapter();
	}
	
	private ListView getListView() {
		return ((ListView) findViewById(R.id.listView));
	}
	
	private void loadListView() {
		progressBar.show();
		OfertasAsyncTask task = new OfertasAsyncTask();
		task.execute(new String[] { value });
	}
	
	private void loadListView(List<Oferta> ofertas) {
		try {
			if (ofertas != null) {
				showToast("La cantidad de ofertas es " + Integer.toString(ofertas.size()));
				ArrayAdapter<Oferta> adapter = new OfertaAdapter(OfertasActivity.this, R.layout.activity_ofertas_item, ofertas);
				setListAdapter(adapter);
			} 
			else {
				showToast("Contactos invalidos: usuarios == null");
			}
		} 
		catch (Exception ex) {
			showToast(ex.getMessage());
		}
	}
	
	private class OfertasAsyncTask extends AsyncTask<String, Void, List<Oferta>> {
		
		@Override
		protected List<Oferta> doInBackground(String... params) {
			List<Oferta> ofertas = new ArrayList<Oferta>();
			try {
				//ofertas = ServicioRestUsuarios.getOfertas(16);
				ofertas = ServicioRestUsuarios.getOfertas(Integer.parseInt(params[0]));
			} catch (RestBlowUpException e) {
				Log.e("ERROR", e.getMessage(), e);
			} catch (UnauthorizedException e) {
				Log.w("Warning", e.getMessage(), e);
			}
			return ofertas;
		}

		@Override
		protected void onPostExecute(List<Oferta> result) {
			if (result != null) {
				loadListView(result);
			} 
			else {
				showToast("Error, resultado invalido de ofertas :(");
			}
			progressBar.dismiss();
		}
		
	}
	
}
