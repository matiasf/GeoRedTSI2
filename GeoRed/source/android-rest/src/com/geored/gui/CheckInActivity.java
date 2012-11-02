package com.geored.gui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.geored.rest.R;
import com.geored.rest.ServicioRestSitiosInteres;
import com.geored.rest.data.CheckIn;
import com.geored.rest.exception.NotFoundException;
import com.geored.rest.exception.RestBlowUpException;
import com.geored.rest.exception.UnauthorizedException;


public class CheckInActivity extends GenericActivity {

	protected void loadVista() {
		setContentView(R.layout.activity_checkin);   
		
		Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("sitioDinteres_id");
            
            TextView txtView = (TextView)findViewById(R.id.textView2);
            txtView.setText(value);
            
        }
	}    

	
	
	public void showCheckin(View clickedButton) {
		TextView txtView = (TextView)findViewById(R.id.textView2);
        
    	CheckInAsyncTask task = new CheckInAsyncTask();
		task.execute(new String[] { txtView.getText().toString()});
    }
	
	private class CheckInAsyncTask extends AsyncTask<String, Void, String> {
	    @Override
	    protected String doInBackground(String... idSitioInteres) {	      
			try {
				CheckIn checkin = new CheckIn();
				checkin.setComentario("no comentarios");
				ServicioRestSitiosInteres serv = new ServicioRestSitiosInteres();
				serv.hacerCheckIn(idSitioInteres[0], checkin);
			} catch (RestBlowUpException e) {
				e.printStackTrace();
				return null;
			} catch (NotFoundException e) {
				e.printStackTrace();
				return null;
			} catch (UnauthorizedException e) {
				e.printStackTrace();
				return null;
			}
			
			return "Exito";
	    }
	
	    @Override
	    protected void onPostExecute(String result) {
	    	if (result != null){
	    		goToActivity(NotificacionesActivity.class);
	    		//goToActivity(UsuarioActivity.class);
	    	}else{
	    		showToast("error");
	    	}	    	
	    }
	}
}
