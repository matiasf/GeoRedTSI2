package com.geored.gui;

import com.geored.rest.data.Categoria;

import com.geored.rest.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class CategoriaAdapter extends ArrayAdapter<Categoria>{

	 Context context; 
	    int layoutResourceId;    
	    Categoria data[] = null;
	    
	    public CategoriaAdapter(Context context, int layoutResourceId, Categoria[] data) {
	        super(context, layoutResourceId, data);
	        this.layoutResourceId = layoutResourceId;
	        this.context = context;
	        this.data = data;
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View row = convertView;
	        CategoriaHolder holder = null;
	        
	        if(row == null)
	        {
	            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
	            row = inflater.inflate(layoutResourceId, parent, false);
	            
	            holder = new CategoriaHolder();
	            holder.txtNombre = (CheckBox)row.findViewById(R.id.categoriacheckBox);
	            holder.txtTexto = (TextView)row.findViewById(R.id.txtTexto);
	            
	            row.setTag(holder);
	        }
	        else
	        {
	            holder = (CategoriaHolder)row.getTag();
	        }
	        
	        Categoria categoria = data[position];
	        holder.txtNombre.setText(categoria.getNombre());
	        holder.txtTexto.setText(categoria.getDescripcion());
	        
	        return row;
	    }
	    
	    static class CategoriaHolder
	    {
	    	CheckBox txtNombre;
	        TextView txtTexto;
	    }
	

}
