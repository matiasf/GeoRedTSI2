package com.geored.gui;

import com.geored.rest.Main;
import com.geored.rest.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class LoginActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    /** Switches to the given class (which must be a subclass of Activity). 
     *  You must also register the new Activity in AndroidManifest.xml.
     */
    private void goToActivity(Class<? extends Activity> activityClass) {
        Intent newActivity = new Intent(this, activityClass);
        startActivity(newActivity);
    }
    
    public void showLogin(View clickedButton) {
    	if (doSomething()){
    		goToActivity(Main.class);
    	}else{
    		showToast("please enter the correct information");
    	}        
    }
    
    private boolean doSomething(){
    	return true;
    }
    
    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
