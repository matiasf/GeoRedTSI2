package com.geored.gui;

import java.util.Hashtable;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.geored.gui.CategoriaAdapter.CategoriaHolder;
import com.geored.rest.R;
import com.geored.rest.data.Categoria;
import com.geored.rest.data.CheckIn;

public class CheckInAdapter  extends ArrayAdapter<CheckIn> {

	Context context;
	int layoutResourceId;
	List<CheckIn> data;
	Hashtable<String, CheckIn> cata = new Hashtable<String, CheckIn>();

	public CheckInAdapter(Context context, int layoutResourceId,
			List<CheckIn> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}
	
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
		CategoriaHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new CategoriaHolder();
			holder.chk = (CheckBox) row.findViewById(R.id.categoriacheckBox);
			holder.txt = (TextView) row.findViewById(R.id.txtNombreCategoria);
			row.setTag(holder);
		} 
		else {
			holder = (CategoriaHolder) row.getTag();
		}

		final CheckIn chk = data.get(position);
		holder.chk.setText(chk.getId());
		holder.txt.setText(chk.getComentario());
		holder.chk.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String pos = Integer.toString(position);
				if (cata.containsKey(pos)) {
					cata.remove(pos);
				} 
				else {
					cata.put(pos, chk);
				}
			}
		});
		return row;
	}

	static class CategoriaHolder {
		CheckBox chk;
		TextView txt;
	}

}
