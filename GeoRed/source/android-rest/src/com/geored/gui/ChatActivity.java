package com.geored.gui;

import java.util.ArrayList;
import java.util.List;

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

import com.geored.rest.R;
import com.geored.rest.ServicioRestGCM;
import com.geored.rest.data.Mensaje;
import com.geored.rest.exception.NotFoundException;
import com.geored.rest.exception.RestBlowUpException;
import com.geored.rest.exception.UnauthorizedException;


public class ChatActivity extends GenericActivity {
	
	private ListView listView;
	private Button enviarButton;
	private List<Mensaje> data; 
	private MensajeAdapter adapter;
	
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Mensaje mensaje = new Mensaje();
			mensaje.setMessage(intent.getExtras().getString("mensaje"));
			mensaje.setIdUsuario(intent.getExtras().getInt("idUsuario"));
			data.add(mensaje);
			adapter.notifyDataSetChanged();
		}
	};
	
	protected void loadVista() {
    	setContentView(R.layout.activity_chat);    	
    	Bundle extras = getIntent().getExtras();
        final String value = extras.getString("user_id");            
        TextView txtView = (TextView)findViewById(R.id.textView2);
        txtView.setText(value);        
        data = new ArrayList<Mensaje>();        
        adapter = new MensajeAdapter(this, R.layout.activity_chat_item, data);
        listView = (ListView)findViewById(R.id.chatlistView);
        View footer = (View) getLayoutInflater().inflate(R.layout.activity_chat_footer_row, null);
        listView.addFooterView(footer);
        enviarButton = (Button)findViewById(R.id.enviarButton);
        enviarButton.setOnClickListener(new Button.OnClickListener() { 
        	public void onClick (View v) {
    			progressBar.show();
    			String text = ((EditText) findViewById(R.id.txtTextoEnviar)).getText().toString();
				Mensaje mensaje = new Mensaje();
				mensaje.setIdUsuario(0);
				mensaje.setMessage(text);
				data.add(mensaje);
				adapter.notifyDataSetChanged();
    			EnviarAsyncTask task = new EnviarAsyncTask();
    			task.execute(new String[] { text, value }); 
        	}
        });
        listView.setAdapter(adapter);
		registerReceiver(mHandleMessageReceiver, new IntentFilter("com.google.android.gcm.demo.app.DISPLAY_MESSAGE"));
    }
	
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
			try {
				return enviarMensaje(params[0], params[1]);
			}
			catch (RestBlowUpException e) {
				Log.e("Error", "Rest blow up!", e);
			}
			catch (UnauthorizedException e) {
				Log.w("Warning", "Unautorized!", e);		
			}
			catch(Exception ex){
				showToast("error al mandar el mensaje");
			}
			return null;
		}
	
	    @Override
	    protected void onPostExecute(Mensaje result) {
	    	if (result != null){
	    		showToast("Mensaje enviado");
	    	}
	    	else{
	    		showToast("Error al enviar el mensaje");
	    	}
	    	progressBar.dismiss();
	    }
	    
	}
    
}
