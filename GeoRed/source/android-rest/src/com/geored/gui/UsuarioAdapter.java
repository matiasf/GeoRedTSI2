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
import com.geored.rest.data.Usuario;

public class UsuarioAdapter extends ArrayAdapter<Usuario> {
	
	Context context;
	int layoutResourceId;
	List<Usuario> data;

	public UsuarioAdapter(Context context, int layoutResourceId,
			List<Usuario> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	public void add(Usuario usuario) {
		data.add(usuario);
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		UsuarioHolder holder = null;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new UsuarioHolder();
			holder.txtNombre = (TextView) row.findViewById(R.id.txtNombre);
			row.setTag(holder);
		}
		else {
			holder = (UsuarioHolder) row.getTag();
		}
		Usuario usuario = data.get(position);
		holder.txtNombre.setText(usuario.getNombre());
		return row;
	}

	private static class UsuarioHolder {
		TextView txtNombre;
	}

}
