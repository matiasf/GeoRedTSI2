package com.geored.gui;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.geored.rest.R;
import com.geored.rest.ServicioRestSitiosInteres;
import com.geored.rest.data.CheckIn;
import com.geored.rest.exception.RestBlowUpException;
import com.geored.rest.exception.UnauthorizedException;

public class CheckInsInfoActivity extends GenericActivity {

	private ListView listView;
	private List<CheckIn> data;
	private CheckInAdapter adapter;

	protected Hashtable<String, CheckIn> hashChks = new Hashtable<String, CheckIn>();

	protected void goToPreviousActivity(){	
		
	}
	
	@Override
	protected void loadVista() {
		setContentView(R.layout.activity_checkinsinfo);
		data = new ArrayList<CheckIn>();
		adapter = new CheckInAdapter(this, R.layout.activity_checkin_item, data);
		listView = (ListView) findViewById(R.id.checkInslistView);
		View header = (View) getLayoutInflater().inflate(R.layout.activity_checkinsinfo, null);
		listView.addHeaderView(header);
		listView.setAdapter(adapter);
		progressBar.show();
		GetCheckInsAsyncTask task = new GetCheckInsAsyncTask();
		task.execute(new String[]{getIntent().getExtras().getString("sitioDinteres_id")});
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
		} 
		catch (Exception ex) {
			showToast(ex.getMessage());
		}
	}

	private class GetCheckInsAsyncTask extends AsyncTask<String, Void, List<CheckIn>> {

		@Override
		protected List<CheckIn> doInBackground(String... params) {
			List<CheckIn> checkIns = null;
			try {
				checkIns = ServicioRestSitiosInteres.getCheckIns(params[0]);
			} 
			catch (RestBlowUpException e) {
				Log.e("ERROR", e.getMessage(), e);
			} 
			catch (UnauthorizedException e) {
				Log.w("Warning", e.getMessage(), e);
			}
			return checkIns;
		}

		@Override
		protected void onPostExecute(List<CheckIn> result) {
			if (result != null) {
				showToast(Integer.toString(result.size()));
				loadListView(result);
			} 
			else {
				showToast("Los checkins no se pudieron cargar.");
			}
			progressBar.dismiss();
			adapter.notifyDataSetChanged();
		}

	}

}
