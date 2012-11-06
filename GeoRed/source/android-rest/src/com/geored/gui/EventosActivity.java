package com.geored.gui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.geored.rest.R;
import com.geored.rest.ServicioRestImagenes;
import com.geored.rest.ServicioRestSitiosInteres;
import com.geored.rest.data.CheckIn;
import com.geored.rest.data.Imagen;
import com.geored.rest.exception.NotFoundException;
import com.geored.rest.exception.RestBlowUpException;
import com.geored.rest.exception.UnauthorizedException;


public class EventosActivity extends GenericActivity {

	
	protected void loadVista() {
		setContentView(R.layout.activity_eventos);   
		
		Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("Eventos_id");
            
            TextView txtView = (TextView)findViewById(R.id.textView2);
            txtView.setText(value);
            
            
        }
        
	}    


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    
	}
	
	
}
