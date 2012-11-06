package com.geored.gui;

import java.util.Hashtable;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.Toast;
import com.geored.rest.R;
import com.geored.rest.data.Usuario;

public class GenericActivity extends Activity {
	
	protected ProgressDialog progressBar;
	
	protected String usuarioId;
	protected Hashtable<String, Usuario>  hashUsuarios = new Hashtable<String,Usuario> ();
    	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressBar = new ProgressDialog(this);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setMessage("Por favor espere...");

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
        
        newActivity.putExtra("user_id", usuarioId);
        
        startActivity(newActivity);
    }
    
    protected void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
    
	protected void blockGUI(int id) {
		Button b = (Button)findViewById(id);
		b.setClickable(false);
		
		progressBar.show();
	}
	
	protected void unBlockGUI(int id) {
		Button b = (Button)findViewById(id);
		b.setClickable(true);
		
		progressBar.dismiss();
	}
    
}
