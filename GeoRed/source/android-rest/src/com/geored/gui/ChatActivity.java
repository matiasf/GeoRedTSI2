package com.geored.gui;

import com.geored.rest.R;
import com.geored.rest.ServicioRestAutenticacion;
import com.geored.rest.ServicioRestGCM;
import com.geored.rest.data.Mensaje;
import com.geored.rest.exception.RestBlowUpException;
import com.geored.rest.exception.UnauthorizedException;



import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ChatActivity extends GenericActivity {

	private ListView listView1;
	private Button enviarButton;
	
	protected void loadVista() {
    	setContentView(R.layout.activity_chat);
    	
    	Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("user_id");
            
            TextView txtView = (TextView)findViewById(R.id.textView2);
            txtView.setText(value);
            
        }
        
        Mensaje mensaje_data[] = new Mensaje[]
                {
                //  new Mensaje(1,"uno"),
        		//  new Mensaje(2,"dos"),
        		//  new Mensaje(3, "tres"),
        		//  new Mensaje(4, "cuatro")
                    
                };
                
        MensajeAdapter adapter = new MensajeAdapter(this, 
                        R.layout.activity_chat_item, mensaje_data);
                
                
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
        			task.execute(new String[] { text, "10" });
        			//enviarMensaje(); 
        		}
        	});
                
        listView1.setAdapter(adapter);
    }
	
	private String enviarMensaje(String mensaje, String id) throws RestBlowUpException, UnauthorizedException{
		
			Mensaje msj = new Mensaje(Integer.parseInt(id), mensaje);
			ServicioRestGCM.enviarMensaje(msj);
			
			return "Exito";
	}
	
	private class EnviarAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			try{
				return enviarMensaje(params[0], params[1]);
			} catch (RestBlowUpException e) {
				Log.e("Error", "Rest blow up!", e);
				return "Rest blow up!";
			} catch (UnauthorizedException e) {
				Log.w("Warning", "Unautorized!", e);
				return "Unautorized!";
			}catch(Exception ex){
				showToast("error al mandar el mensaje");
				return "Error";
			}
		}
	
	    @Override
	    protected void onPostExecute(String result) {
	    	if (result.equals("Exito")){
	    		showToast("mensaje enviado");
	    	}else{
	    		showToast(result);
	    	}
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
