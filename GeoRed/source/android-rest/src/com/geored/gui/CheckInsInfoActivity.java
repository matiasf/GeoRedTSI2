package com.geored.gui;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.geored.rest.R;
import com.geored.rest.ServicioRestUsuarios;
import com.geored.rest.data.Categoria;
import com.geored.rest.data.CheckIn;
import com.geored.rest.exception.NotFoundException;
import com.geored.rest.exception.RestBlowUpException;
import com.geored.rest.exception.UnauthorizedException;

public class CheckInsInfoActivity extends GenericActivity {

	private ListView listView;
	private List<CheckIn> data;
	
	private CheckInAdapter adapter;
	protected Hashtable<String, CheckIn> hashChks = new Hashtable<String, CheckIn>();

	protected void loadVista() {
		setContentView(R.layout.activity_checkinsinfo);
		data = new ArrayList<CheckIn>();
		adapter = new CheckInAdapter(this, R.layout.activity_categoria_item, data);
		listView = (ListView) findViewById(R.id.categoriaslistView);
		View header = (View) getLayoutInflater().inflate(
				R.layout.activity_categorias_footer_row, null);
		listView.addHeaderView(header);
		
		listView.setAdapter(adapter);
		progressBar.show();
		GetCategoriasAsyncTask task = new GetCategoriasAsyncTask();
		task.execute();
	}

	private void continuar() throws RestBlowUpException, UnauthorizedException {
		Iterator<CheckIn> it = adapter.getSelected().values().iterator();
		List<Integer> categorias = new ArrayList<Integer>();
		while (it.hasNext()) {
			CheckIn cat = (CheckIn) it.next();
			categorias.add(cat.getId());
		}
		ServicioRestUsuarios.agregarCategorias(categorias);
	}
	
	private void loadListView(List<CheckIn> checkIns) {
		try {
			hashUsuarios.clear();
			List<String> strs = new ArrayList<String>();
			if (checkIns != null) {
				showToast(Integer.toString(checkIns.size()));
				Iterator<CheckIn> it = checkIns.iterator();
				while (it.hasNext()) {
					CheckIn chk = (CheckIn) it.next();
					strs.add(chk.getComentario());
					hashChks.put(Integer.toString(chk.getId()), chk);
					data.add(chk);
				}
			} 
			else {
				showToast("Error: usuarios == null.");
			}
		} catch (Exception ex) {
			showToast(ex.getMessage());
		}
	}

	private class GetCategoriasAsyncTask extends AsyncTask<String, Void, List<CheckIn>> {
		
		@Override
		protected List<CheckIn> doInBackground(String... params) {
			List<CheckIn> cat = null;
			/*try {
				cat = getCategorias();
			} catch (RestBlowUpException e) {
				e.printStackTrace();
				return null;
			} catch (UnauthorizedException e) {
				e.printStackTrace();
				return null;
			} catch (NotFoundException e) {
				e.printStackTrace();
				return null;
			}*/
			return cat;
		}

		@Override
		protected void onPostExecute(List<CheckIn> result) {
			if (result != null) {
				showToast(Integer.toString(result.size()));
				loadListView(result);
			} else {
				showToast("Las categorias no se pudieron cargar.");
			}
			progressBar.dismiss();
			adapter.notifyDataSetChanged();
		}
		
		private List<Categoria> getCategorias() throws RestBlowUpException, UnauthorizedException, NotFoundException {
			List<Categoria> cat = ServicioRestUsuarios.getCategorias();
			return cat;
		}

	}
	
	private class AgregarAsyncTask extends AsyncTask<String, Void, String> {
	
		@Override
		protected String doInBackground(String... params) {
			try {
				continuar();
				return "Exito";
			} catch (RestBlowUpException e) {
				Log.e("Error", "Rest blow up!", e);
				return null;
			} catch (UnauthorizedException e) {
				Log.w("Warning", "Unautorized!", e);
				return null;
			} catch (Exception ex) {
				showToast("error al mandar el mensaje");
				return null;
			}
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				showToast("Categorias agregadas.");
			} else {
				showToast("Error al agregar las categorias.");
			}
			progressBar.dismiss();
			goToActivity(UsuarioActivity.class);
		}
		
	}

}
