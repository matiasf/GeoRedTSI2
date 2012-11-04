package com.geored.gui;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.geored.rest.R;
import com.geored.rest.data.Oferta;

public class OfertaAdapter extends ArrayAdapter<Oferta> {

	Context context;
	int layoutResourceId;
	List<Oferta> data;

	public OfertaAdapter(Context context, int layoutResourceId, List<Oferta> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	public void add(Oferta oferta) {
		data.add(oferta);
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		OfertaHolder holder = null;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new OfertaHolder();
			holder.txtNombre = (TextView) row.findViewById(R.id.txtNombre);
			holder.txtDescripcion = (TextView) row.findViewById(R.id.txtDescripcion);
			holder.txtCosto = (TextView) row.findViewById(R.id.txtCosto);
			row.setTag(holder);
		}
		else {
			holder = (OfertaHolder) row.getTag();
		}
		Oferta oferta = data.get(position);
		holder.txtNombre.setText(oferta.getNombre());
		holder.txtDescripcion.setText(oferta.getDescripcion());
		holder.txtCosto.setText(oferta.getCosto().toString());
		return row;
	}

	private static class OfertaHolder {
		TextView txtNombre;
		TextView txtDescripcion;
		TextView txtCosto;
	}

}
