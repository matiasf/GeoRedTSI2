package com.geored.gui;

import com.geored.rest.R;
import com.geored.rest.ServicioRestUsuarios;
import com.geored.rest.data.Usuario;



import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class ChatActivity extends Activity {

	private Usuario usuario = null;
	private String usuarioId;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("user_id");
            
            TextView txtView = (TextView)findViewById(R.id.textView2);
            txtView.setText(value);
            setUsuario(value);
        }
    }

    private void setUsuario(String usuarioId){
    	try{
    		this.usuario = ServicioRestUsuarios.getContacto(usuarioId);
    	}catch(Exception ex){
    		showToast(ex.getMessage());
    	}
    }
    
    private String getUsuario(){
    	if (usuario == null){
    		return usuarioId;    		
    	}else{
    		return usuario.getNombre();
    	}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

}
