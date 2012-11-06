package com.geored.gui;

import java.util.ArrayList;
import java.util.Hashtable;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import com.geored.rest.data.Notificacion;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class EventosItemizedOverlay extends ItemizedOverlay {

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext = null;
	protected Hashtable<String, Notificacion>  hashNotificaciones = new Hashtable<String,Notificacion> ();
	
	public EventosItemizedOverlay(Drawable defaultMarker, Context context) {
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
      dialog.setPositiveButton("VER", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog,int which) {
          // Write your code here to invoke YES event
        	  hashNotificaciones.get(id);
        	  
        	  Intent intent = new Intent(mContext, EventosActivity.class);
        	  intent.putExtra("Eventos_id",id);
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
