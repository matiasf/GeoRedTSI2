package com.geored.gui;

import java.util.List;

import com.geored.rest.data.Categoria;
import com.geored.rest.data.Mensaje;

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
	    //Categoria data[] = null;
	    List<Categoria>  data = null;
	    
	    public void add(Categoria xxx)
	    {
	        data.add(xxx);
	        notifyDataSetChanged();
	    }
	    
	    public Categoria get(int pos){
	    	return data.get(pos);
	    }
	    
	    public CategoriaAdapter(Context context, int layoutResourceId, List<Categoria> data) {
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
	            holder.chk= (CheckBox)row.findViewById(R.id.categoriacheckBox);
	            holder.txt= (TextView)row.findViewById(R.id.txtNombreCategoria);
	            
	            row.setTag(holder);
	        }
	        else
	        {
	            holder = (CategoriaHolder)row.getTag();
	        }
	        
	        Categoria categoria = data.get(position);
	        holder.chk.setText(categoria.getNombre());
	        holder.txt.setText(categoria.getDescripcion());
	        
	        return row;
	    }
	    
	    static class CategoriaHolder
	    {
	    	CheckBox chk;
	        TextView txt;
	    }
	

}
