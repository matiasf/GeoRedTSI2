package com.geored.gui;

import java.util.ArrayList;
import java.util.List;

import com.geored.rest.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;


public class NotificacionesActivity extends MapActivity {

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones);
        
        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.androidmarker);
        NotificacionesItemizedOverlay itemizedoverlay = new NotificacionesItemizedOverlay(drawable, this);
        
        GeoPoint point = new GeoPoint(-34000000,-56000000);
        OverlayItem overlayitem = new OverlayItem(point, "Hola, Mundo!", "I'm in Uruguay!");
        
        itemizedoverlay.addOverlay(overlayitem);
        mapOverlays.add(itemizedoverlay);
    }


    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
    protected void goToActivity(Class<? extends Activity> activityClass) {
        Intent newActivity = new Intent(this, activityClass);
        
        
        startActivity(newActivity);
    }
    
    protected void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
    
    

}
