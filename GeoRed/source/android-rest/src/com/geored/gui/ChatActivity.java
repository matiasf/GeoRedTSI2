package com.geored.gui;

import java.util.ArrayList;
import java.util.List;

import com.geored.rest.R;
import com.geored.rest.ServicioRestGCM;
import com.geored.rest.data.Mensaje;
import com.geored.rest.exception.NotFoundException;
import com.geored.rest.exception.RestBlowUpException;
import com.geored.rest.exception.UnauthorizedException;
import com.google.android.gcm.GCMRegistrar;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ChatActivity extends GenericActivity {

	private ListView listView1;
	private Button enviarButton;
	//private List<Mensaje> data; 
	
	private final static String SENDER_ID = "786328023735";
	private final static String EXTRA_MESSAGE = "message";
	AsyncTask<Void, Void, Void> mRegisterTask;
	
	protected void loadVista() {
    	setContentView(R.layout.activity_chat);
    	
    	Bundle extras = getIntent().getExtras();
        final String value = extras.getString("user_id");            
        TextView txtView = (TextView)findViewById(R.id.textView2);
        txtView.setText(value);
        
        List<Mensaje> data = new ArrayList<Mensaje>();        
        MensajeAdapter adapter = new MensajeAdapter(this, R.layout.activity_chat_item, data);
                
        listView1 = (ListView)findViewById(R.id.chatlistView);
                 
        //View header = (View)getLayoutInflater().inflate(R.layout.activity_chat_header_row, null);
        //listView1.addHeaderView(header);        

        View footer = (View)getLayoutInflater().inflate(R.layout.activity_chat_footer_row, null);
        listView1.addFooterView(footer);
        
        
        
        
        enviarButton = (Button)findViewById(R.id.enviarButton);
        enviarButton.setOnClickListener(new Button.OnClickListener() { 
        	public void onClick (View v){
        			progressBar.show();
        			String text = ((EditText) findViewById(R.id.txtTextoEnviar)).getText().toString();
        			EnviarAsyncTask task = new EnviarAsyncTask();
        			task.execute(new String[] { text, value }); 
        	}
        });
        listView1.setAdapter(adapter);
        
        
        GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		final String regId = GCMRegistrar.getRegistrationId(this);
		if (regId.equals("")) {
			GCMRegistrar.register(this, SENDER_ID);
		} else {
			Log.i("Info", "Already registered");
			mRegisterTask = new AsyncTask<Void, Void, Void>() {
				@Override
				protected Void doInBackground(Void... params) {
					try {
						ServicioRestGCM.registrar(regId);
						
					} catch (RestBlowUpException e) {
						Log.e("Error", "Rest blow up!", e);
					} catch (UnauthorizedException e) {
						Log.w("Warning", "Unautorized!", e);
					}
					return null;
				}				
			};
			mRegisterTask.execute();
		}
		registerReceiver(mHandleMessageReceiver, new IntentFilter(
				"com.google.android.gcm.demo.app.DISPLAY_MESSAGE"));

        
    }
	
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
			Toast.makeText(ChatActivity.this, newMessage, Toast.LENGTH_LONG).show();
		}
	};
	
	private Mensaje enviarMensaje(String mensaje, String id) throws RestBlowUpException, UnauthorizedException{
		
			Mensaje msj = new Mensaje(Integer.parseInt(id), mensaje);
			try {
			
				ServicioRestGCM.enviarMensaje(msj);
				
			} catch (NotFoundException e) {
				Log.i("INFO", "El usuario " + id + " no esta conetado.");
			}
			return msj;
	}
	
	private class EnviarAsyncTask extends AsyncTask<String, Void, Mensaje> {
		@Override
		protected Mensaje doInBackground(String... params) {
			try{
				return enviarMensaje(params[0], params[1]);
			} catch (RestBlowUpException e) {
				Log.e("Error", "Rest blow up!", e);
				
			} catch (UnauthorizedException e) {
				Log.w("Warning", "Unautorized!", e);
				
			}catch(Exception ex){
				showToast("error al mandar el mensaje");
				
			}
			return null;
		}
	
	    @Override
	    protected void onPostExecute(Mensaje result) {
	    	if (result != null){
	    		showToast("mensaje enviado");
	    	}else{
	    		showToast("error al enviar el mensaje");
	    	}
	    	//((MensajeAdapter)listView1.getAdapter()).add(result);
	    	//((MensajeAdapter)listView1.getAdapter()).notifyDataSetChanged();
	    	progressBar.dismiss();
	    }
	}

	
/*
    private ListView listView1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Weather weather_data[] = new Weather[]
        {
            new Weather(R.drawable.weather_cloudy, "Cloudy"),
            new Weather(R.drawable.weather_showers, "Showers"),
            new Weather(R.drawable.weather_snow, "Snow"),
            new Weather(R.drawable.weather_storm, "Storm"),
            new Weather(R.drawable.weather_sunny, "Sunny")
        };
        
        WeatherAdapter adapter = new WeatherAdapter(this, 
                R.layout.listview_item_row, weather_data);
        
        
        listView1 = (ListView)findViewById(R.id.listView1);
         
        View header = (View)getLayoutInflater().inflate(R.layout.listview_header_row, null);
        listView1.addHeaderView(header);
        
        listView1.setAdapter(adapter);
	    
  */  
    
}
