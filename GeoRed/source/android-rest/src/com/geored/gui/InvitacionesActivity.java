package com.geored.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.geored.rest.R;
import com.geored.rest.ServicioRestUsuarios;
import com.geored.rest.data.Invitacion;
import com.geored.rest.data.Usuario;



import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import android.view.ActionMode;

import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;



public class InvitacionesActivity extends ListActivity {
	
	private Usuario usuario = null;

    @Override
    public void onCreate(Bundle icicle) {
    	super.onCreate(icicle);
        
        loadListViewHardCoreData2();
        registerForContextMenu(getListView());
        
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("user_id");
            
            setUsuario(value);
        }
    }
    
    private void setUsuario(String usuarioId){
    	try{
    		this.usuario = ServicioRestUsuarios.getContacto(usuarioId);
    	}catch(Exception ex){
    		showToast(ex.getMessage());
    	}
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
     
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_invitaciones, menu);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    	Object itemList = getListAdapter().getItem(info.position);
        switch (item.getItemId()) {
            case R.id.menu_rechazar_invitacion:
                //showToast("Invitar: pos="+info.position + " , usr="+itemList.toString());
                showInvitacionRechazada(itemList.toString());
                showToast("invitacion del contacto <"+itemList.toString()+"> rechazada");
            	return true;
            case R.id.menu_invitar_contacto:
                showInvitacionAceptada(itemList.toString());
            	//showToast("Chat: pos="+info.position + " , usr="+itemList.toString());
                showToast("invitacion del contacto <"+itemList.toString()+"> aceptada");
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    
    private void showInvitacionAceptada(String idContacto) {
		try{
			ServicioRestUsuarios.aceptarInvitacion(idContacto);
			Intent i = new Intent(getApplicationContext(), UsuarioActivity.class);
	    	startActivity(i);
		}catch(Exception ex){
			showToast(ex.getMessage());
		}
		
	}

	private void showInvitacionRechazada(String idContacto) {
		try{
			//ServicioRestUsuarios.aceptarInvitacion(idContacto);
			Intent i = new Intent(getApplicationContext(), UsuarioActivity.class);
	    	startActivity(i);
		}catch(Exception ex){
			showToast(ex.getMessage());
		}
		
	}



	private void loadListView() {
    	try{    		
    		//ListView listView = (ListView) findViewById(R.id.contactosListView);
    		
    		List<String> strs = new ArrayList<String>(); 
    		List<Invitacion> invitaciones = ServicioRestUsuarios.getInvitaciones(usuario.getId());
    		if (invitaciones != null){
    			showToast(Integer.toString(invitaciones.size()));
        		Iterator<Invitacion> it = invitaciones.iterator();
        		while(it.hasNext()){
        			Invitacion invitacion  = (Invitacion)it.next();
        			strs.add("id:<"+invitacion.getId()+"> remitente:<"+invitacion.getRemitente().getNombre()+">");
        		}
        		
            	//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
            	//    	  android.R.layout.simple_list_item_1, android.R.id.text1, strs);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, strs);
                
    	    	// Assign adapter to ListView
    	    	//listView.setAdapter(adapter); 
                setListAdapter(adapter);
    		}else{
    			showToast("invitaciones == null");
    		}    		
    	}catch(Exception ex){
    		showToast(ex.getMessage());    		
    	}
    }
    
    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
    
    private void loadListViewHardCoreData2() {
    	//ListView listView = (ListView) findViewById(R.id.contactosListView);
		
		List<String> strs = new ArrayList<String>(); 
		
		for(int i=0; i < 15;i++){
			//strs.add("Usuario "+Integer.toString(i));
			strs.add("id:<"+Integer.toString(i)+"> remitente:<Usuario "+Integer.toString(i)+">");
		}
		
    	//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
    	//    	  android.R.layout.simple_list_item_1, android.R.id.text1, strs);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, strs);
        
    	// Assign adapter to ListView
    	//listView.setAdapter(adapter);
        setListAdapter(adapter);
    }


}
