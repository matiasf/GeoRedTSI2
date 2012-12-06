package com.geored.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.geored.gui.utils.Constantes;
import com.geored.rest.R;
import com.geored.rest.data.Notificacion;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;
import android.graphics.drawable.Drawable;

public class NotificacionesActivity extends GenericNotificacionesActivity {

		protected void loadNotifications(List<Notificacion> result) {
			try {
				Drawable drawable = NotificacionesActivity.this.getResources()
						.getDrawable(R.drawable.marker);
				SitioDInteresItemizedOverlay itemizedoverlay = new SitioDInteresItemizedOverlay(
						drawable, NotificacionesActivity.this);
				itemizedoverlay.hashNotificaciones.clear();
				
				Drawable drawableyIntegracion = NotificacionesActivity.this.getResources()
						.getDrawable(R.drawable.markeryellow);
				SitioDInteresItemizedOverlay itemizedoverlayIntegracion = new SitioDInteresItemizedOverlay(
						drawableyIntegracion, NotificacionesActivity.this);
				itemizedoverlayIntegracion.hashNotificaciones.clear();
				
				List<String> strs = new ArrayList<String>();
				if (result != null) {
					showToast(Integer.toString(result.size()));
					Iterator<Notificacion> it = result.iterator();
					while (it.hasNext()) {
						Notificacion noty = (Notificacion) it.next();
						if (noty.getPosicion() != null && 
								(noty.getTipo().equalsIgnoreCase(Constantes.TipoNotifiacion.SITIO_DE_INTERES.toString())
									|| noty.getTipo().equalsIgnoreCase(Constantes.TipoNotifiacion.SITIO_DE_INTERES_INTEGRACION.toString()))) {
							
							
							
							strs.add(noty.getId());
							if (noty.getTipo().equalsIgnoreCase(Constantes.TipoNotifiacion.SITIO_DE_INTERES.toString())){
								itemizedoverlay.hashNotificaciones.put(noty.getId(), noty);
								GenericActivity.hashNotificaciones.put(noty.getId()+Constantes.TipoNotifiacion.SITIO_DE_INTERES.toString(), noty);
							}else{
								itemizedoverlayIntegracion.hashNotificaciones.put(noty.getId(), noty);	
								GenericActivity.hashNotificaciones.put(noty.getId()+Constantes.TipoNotifiacion.SITIO_DE_INTERES_INTEGRACION.toString(), noty);
							}
							

							GeoPoint point = new GeoPoint(
									(int) (noty.getPosicion().getLatitud() * 1E6),
									(int) (noty.getPosicion().getLongitud() * 1E6));
							OverlayItem o = new OverlayItem(point,
									noty.getNombre(), noty.getId());

							if (noty.getTipo().equalsIgnoreCase(Constantes.TipoNotifiacion.SITIO_DE_INTERES.toString())) {
								itemizedoverlay.addOverlay(o);	
								mapView.getOverlays().add(itemizedoverlay);
							}else{
								itemizedoverlayIntegracion.addOverlay(o);
								mapView.getOverlays().add(itemizedoverlayIntegracion);
							}								
							
							mapView.invalidate();
						}
					}

				} else {
					showToast("usuarios == null");
				}
			} catch (Exception ex) {
				showToast(ex.getMessage());
			}

		}
	

}
