package com.geored.gui;

import java.util.Hashtable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.LoggingBehaviors;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.geored.rest.R;
import com.geored.rest.data.Usuario;

public class GenericActivity extends Activity {

	protected String usuarioId;
	protected Hashtable<String, Usuario>  hashUsuarios = new Hashtable<String,Usuario> ();
    	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //este metodo tiene que ejecutar el setContentView(R.layout.activity_ 
        //y algo mas particular de cada vista hija/o
        loadVista();
        
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
        	usuarioId = extras.getString("user_id");
        
        }
    }
    
    protected void loadVista() {	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    protected void goToActivity(Class<? extends Activity> activityClass) {
        Intent newActivity = new Intent(this, activityClass);
        
        newActivity.putExtra("user_id",usuarioId);
        
        startActivity(newActivity);
    }
    
    protected void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
