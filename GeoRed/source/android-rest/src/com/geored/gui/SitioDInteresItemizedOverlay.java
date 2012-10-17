package com.geored.gui;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.Toast;

import com.geored.rest.ServicioRestSitiosInteres;
import com.geored.rest.ServicioRestUsuarios;
import com.geored.rest.data.CheckIn;
import com.geored.rest.data.Notificacion;
import com.geored.rest.data.Posicion;
import com.geored.rest.exception.NotFoundException;
import com.geored.rest.exception.RestBlowUpException;
import com.geored.rest.exception.UnauthorizedException;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class SitioDInteresItemizedOverlay extends ItemizedOverlay {

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext = null;
	protected Hashtable<String, Notificacion>  hashNotificaciones = new Hashtable<String,Notificacion> ();
	
	public SitioDInteresItemizedOverlay(Drawable defaultMarker, Context context) {
		  super(boundCenterBottom(defaultMarker));
		  mContext = context;
		}
	
    public void addOverlay(OverlayItem overlay) {
        mOverlays.add(overlay);
        populate();
    }
    
    public void clear() {
        mOverlays.clear();
        populate();
    }
	
    @Override
    protected OverlayItem createItem(int i) {
      return mOverlays.get(i);
    }
	
    @Override
    public int size() {
      return mOverlays.size();
    }
    
   
    @Override
    protected boolean onTap(int index) {
      
      OverlayItem item = mOverlays.get(index);
      final String id = item.getSnippet();
      AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
      dialog.setTitle(item.getTitle());
      //dialog.setMessage(item.getSnippet());
      
      // Setting Positive "Yes" Button
      dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog,int which) {
          // Write your code here to invoke YES event
        	  hashNotificaciones.get(id);
        	  
        	  Intent intent = new Intent(mContext, CheckInActivity.class);
        	  intent.putExtra("sitioDinteres_id",id);
              mContext.startActivity(intent);


          }
      });

      // Setting Negative "NO" Button
      dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
    	  
          public void onClick(DialogInterface dialog, int which) {
          // Write your code here to invoke NO event
        	  
          dialog.cancel();
          }
      });

      dialog.show();
      return true;
    }

	
}
