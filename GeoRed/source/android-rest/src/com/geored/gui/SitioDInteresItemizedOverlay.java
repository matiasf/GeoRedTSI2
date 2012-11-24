package com.geored.gui;

import java.util.ArrayList;
import java.util.Hashtable;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.geored.gui.utils.Constantes;
import com.geored.rest.data.Notificacion;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

@SuppressWarnings("rawtypes")
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
      Notificacion notifiacion = hashNotificaciones.get(id);
      dialog.setTitle(notifiacion.getNombre());
      dialog.setMessage(notifiacion.getDescripcion());
      if (notifiacion.getTipo().equalsIgnoreCase(Constantes.TipoNotifiacion.SITIO_DE_INTERES.toString())) {
    	  // Setting Positive "Yes" Button
          dialog.setPositiveButton("CHECKIN", new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog,int which) {
            	  // Write your code here to invoke YES event        	  
            	  Intent intent = new Intent(mContext, CheckInActivity.class);
            	  intent.putExtra("sitioDinteres_id", id);
                  mContext.startActivity(intent);
              }
          });
          // Setting Negative "NO" Button
          dialog.setNegativeButton("VER", new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int which) {
            	  // Write your code here to invoke NO event
            	  Intent intent = new Intent(mContext, CheckInsInfoActivity.class);
            	  intent.putExtra("sitioDinteres_id", id);
                  mContext.startActivity(intent);
              }
          });
      }
      dialog.show();
      return true;
    }

	
}
