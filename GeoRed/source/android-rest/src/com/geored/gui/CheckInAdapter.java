package com.geored.gui;

import java.io.InputStream;
import java.util.Hashtable;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.geored.rest.R;
import com.geored.rest.ServicioRestImagenes;
import com.geored.rest.data.CheckIn;
import com.geored.rest.exception.RestBlowUpException;

public class CheckInAdapter extends ArrayAdapter<CheckIn> {

	Context context;
	int layoutResourceId;
	List<CheckIn> data;
	Hashtable<String, CheckIn> cata = new Hashtable<String, CheckIn>();

	public CheckInAdapter(Context context, int layoutResourceId, List<CheckIn> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	@Override
	public void add(CheckIn chk) {
		data.add(chk);
		notifyDataSetChanged();
	}

	public CheckIn get(int pos) {
		return data.get(pos);
	}

	public Hashtable<String, CheckIn> getSelected() {
		return cata;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View row = convertView;
		final CheckInHolder holder;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new CheckInHolder();
			holder.imagen = (ImageView) row.findViewById(R.id.imageViewCheckIn);
			holder.usuario = (TextView) row.findViewById(R.id.textViewUsuario);
			holder.comentario = (TextView) row.findViewById(R.id.textViewComentario);
			row.setTag(holder);
		} 
		else {
			holder = (CheckInHolder) row.getTag();
		}
		final CheckIn chk = data.get(position);
		holder.usuario.setText(chk.getUserName());
		holder.comentario.setText(chk.getComentario());
		if (chk.getIdImagen() != null) {
			AsyncTask<String, Void, Bitmap> asyncTask = new AsyncTask<String, Void, Bitmap>() {
				
				@Override
				protected Bitmap doInBackground(String... params) {
					Bitmap bitmap = null;
					try {
						InputStream imagen = ServicioRestImagenes.bajarImagen(Integer.valueOf(params[0]));
						bitmap = BitmapFactory.decodeStream(imagen);
					} 
					catch (RestBlowUpException e) {
						Log.e("ERROR", e.getMessage(), e);
					}
					return bitmap;
				}
				
				@Override
				protected void onPostExecute(Bitmap bitmap) {
					holder.imagen.setImageBitmap(bitmap);
				}
				
			};
			asyncTask.execute(new String[]{chk.getIdImagen().toString()});
		}
		return row;
	}

	private static class CheckInHolder {
		ImageView imagen;
		TextView usuario;
		TextView comentario;
	}

}
