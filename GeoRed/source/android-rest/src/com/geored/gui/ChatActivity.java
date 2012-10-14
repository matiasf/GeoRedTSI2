package com.geored.gui;

import com.geored.rest.R;



import android.os.Bundle;
import android.widget.TextView;

public class ChatActivity extends GenericActivity {

	protected void loadVista() {
    	setContentView(R.layout.activity_chat);
    	
    	Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("user_id");
            
            TextView txtView = (TextView)findViewById(R.id.textView2);
            txtView.setText(value);
            
        }
    }
	    
    
    
}
