package com.geored.gui;

import java.util.List;

import com.geored.rest.data.Mensaje;

import com.geored.rest.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MensajeAdapter extends ArrayAdapter<Mensaje>{

	 Context context; 
	    int layoutResourceId;    
	    List<Mensaje>  data = null;
	    
	    public MensajeAdapter(Context context, int layoutResourceId, List<Mensaje> data) {
	        super(context, layoutResourceId, data);
	        this.layoutResourceId = layoutResourceId;
	        this.context = context;
	        this.data = data;
	    }

	    public void add(Mensaje xxx)
	    {
	        data.add(xxx);
	    }
	    
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View row = convertView;
	        MensajeHolder holder = null;
	        
	        if(row == null)
	        {
	            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
	            row = inflater.inflate(layoutResourceId, parent, false);
	            
	            holder = new MensajeHolder();
	            holder.txtNombre = (TextView)row.findViewById(R.id.txtNombre);
	            holder.txtTexto = (TextView)row.findViewById(R.id.txtTexto);
	            
	            row.setTag(holder);
	        }
	        else
	        {
	            holder = (MensajeHolder)row.getTag();
	        }
	        
	        Mensaje mensaje = data.get(position);
	        holder.txtNombre.setText(mensaje.getIdUsuario().toString());
	        holder.txtTexto.setText(mensaje.getMessage());
	        
	        return row;
	    }
	    
	    static class MensajeHolder
	    {
	    	TextView txtNombre;
	        TextView txtTexto;
	    }
	

}
