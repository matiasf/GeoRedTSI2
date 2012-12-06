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

public class NotificacionesEventosActivity extends GenericNotificacionesActivity {

	protected void loadNotifications(List<Notificacion> result) {
			try {
				Drawable drawable = NotificacionesEventosActivity.this.getResources()
						.getDrawable(R.drawable.markergreen);
				EventosItemizedOverlay itemizedoverlay = new EventosItemizedOverlay(
						drawable, NotificacionesEventosActivity.this);
				itemizedoverlay.hashNotificaciones.clear();

				
				List<String> strs = new ArrayList<String>();
				if (result != null) {
					showToast(Integer.toString(result.size()));
					Iterator<Notificacion> it = result.iterator();
					while (it.hasNext()) {
						Notificacion noty = (Notificacion) it.next();
						if (noty.getPosicion() != null && noty.getTipo().equalsIgnoreCase(Constantes.TipoNotifiacion.EVENTO.toString())) {
							
							GenericActivity.hashNotificaciones.put(noty.getId()+Constantes.TipoNotifiacion.EVENTO.toString(), noty);
							
							strs.add(noty.getId());

							itemizedoverlay.hashNotificaciones.put(
									noty.getId(), noty);

							GeoPoint point = new GeoPoint(
									(int) (noty.getPosicion().getLatitud() * 1E6),
									(int) (noty.getPosicion().getLongitud() * 1E6));
							OverlayItem o = new OverlayItem(point,
									noty.getNombre(), noty.getId());

							// itemizedoverlay.clear();
							itemizedoverlay.addOverlay(o);

							// add the overlay item
							// mapView.getOverlays().clear();
							mapView.getOverlays().add(itemizedoverlay);
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
