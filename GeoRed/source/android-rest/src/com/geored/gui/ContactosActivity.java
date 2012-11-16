package com.geored.gui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
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

import com.geored.rest.R;
import com.geored.rest.ServicioRestUsuarios;
import com.geored.rest.data.Usuario;
import com.geored.rest.exception.NotFoundException;
import com.geored.rest.exception.RestBlowUpException;
import com.geored.rest.exception.UnauthorizedException;

public class ContactosActivity extends GenericActivity {

	@Override
	protected void loadVista() {
		setContentView(R.layout.activity_contactos);
		loadListView();
		registerForContextMenu(getListView());
	}
	
	@Override
	protected void goToPreviousActivity(){	
        Intent setIntent = new Intent(this,UsuarioActivity.class);
        startActivity(setIntent); 
	}

	private void setListAdapter(ArrayAdapter<Usuario> adapter) {
		getListView().setAdapter(adapter);
	}

	private ListAdapter getListAdapter() {
		return getListView().getAdapter();
	}

	private ListView getListView() {
		return ((ListView) findViewById(R.id.listView));
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_contactos, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		Usuario itemList = (Usuario)getListAdapter().getItem(info.position);
		switch (item.getItemId()) {
		// case R.id.menu_invitar_contacto:
		// //showToast("Invitar: pos="+info.position +
		// " , usr="+itemList.toString());
		// showInvitar(itemList.toString());
		// showToast("Contacto <"+itemList.toString()+"> invitado");
		// return true;
		case R.id.menu_iniciar_chat:
			showChat(itemList.getId());
			// showToast("Chat: pos="+info.position +
			// " , usr="+itemList.toString());
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	private void showChat(String id) {
		Intent i = new Intent(getApplicationContext(), ChatActivity.class);
		i.putExtra("user_id", id);
		startActivity(i);
	}

	private void loadListView() {
		progressBar.show();
		RegistryAsyncTask task = new RegistryAsyncTask();
		task.execute();
	}

	private void loadListView(List<Usuario> usuarios) {
		try {
			if (usuarios != null) {
				showToast("La cantidad de contactos es " + Integer.toString(usuarios.size()));
				ArrayAdapter<Usuario> adapter = new UsuarioAdapter(ContactosActivity.this, R.layout.activity_chat_item, usuarios);
				setListAdapter(adapter);
			} 
			else {
				showToast("Contactos invalidos: usuarios == null");
			}
		} catch (Exception ex) {
			showToast(ex.getMessage());
		}
	}

	private class RegistryAsyncTask extends AsyncTask<String, Void, List<Usuario>> {
		
		@Override
		protected List<Usuario> doInBackground(String... params) {
			List<Usuario> usuarios = new ArrayList<Usuario>();
			try {
				usuarios = ServicioRestUsuarios.getContactos();
			} catch (RestBlowUpException e) {
				Log.e("ERROR", e.getMessage(), e);
			} catch (NotFoundException e) {
				Log.w("Warning", e.getMessage(), e);
			} catch (UnauthorizedException e) {
				Log.w("Warning", e.getMessage(), e);
			}
			return usuarios;
		}

		@Override
		protected void onPostExecute(List<Usuario> result) {
			if (result != null) {
				loadListView(result);
			} 
			else {
				showToast("Error, resultado invalido de usuarios :(");
			}
			progressBar.dismiss();
		}
		
	}
}
