package com.geored.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.geored.rest.R;
import com.geored.rest.ServicioRestUsuarios;
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
import android.widget.Toast;

import android.view.ActionMode;

import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;



public class ContactosActivity extends ListActivity {
	
	private Usuario usuario = null;
	private String usuarioId;

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
    
    private String getUsuario(){
    	if (usuario == null){
    		return usuarioId;    		
    	}else{
    		return usuario.getNombre();
    	}
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
     
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_contactos, menu);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    	Object itemList = getListAdapter().getItem(info.position);
        switch (item.getItemId()) {
            case R.id.menu_invitar_contacto:
                //showToast("Invitar: pos="+info.position + " , usr="+itemList.toString());
                showInvitar(itemList.toString());
                showToast("Contacto <"+itemList.toString()+"> invitado");
            	return true;
            case R.id.menu_iniciar_chat:
                showChat(itemList.toString());
            	//showToast("Chat: pos="+info.position + " , usr="+itemList.toString());
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    
    private void showChat(String id) {
    	Intent i = new Intent(getApplicationContext(), ChatActivity.class);
    	i.putExtra("user_id",id);
    	startActivity(i);   	   
		
	}

	private void showInvitar(String id) {
		try{
			ServicioRestUsuarios.invitarContacto(id);
			Intent i = new Intent(getApplicationContext(), UsuarioActivity.class);
			i.putExtra("user_id",getUsuario()); 
	    	startActivity(i);
		}catch(Exception ex){
			showToast(ex.getMessage());
		}
	}

	private void loadListView() {
    	try{    		
    		//ListView listView = (ListView) findViewById(R.id.contactosListView);
    		
    		List<String> strs = new ArrayList<String>(); 
    		List<Usuario> usuarios = ServicioRestUsuarios.getContactos();
    		if (usuarios != null){
    			showToast(Integer.toString(usuarios.size()));
        		Iterator<Usuario> it = usuarios.iterator();
        		while(it.hasNext()){
        			Usuario usuario  = (Usuario)it.next();
        			strs.add(usuario.getNombre());
        		}
        		
            	//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
            	//    	  android.R.layout.simple_list_item_1, android.R.id.text1, strs);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, strs);
                
    	    	// Assign adapter to ListView
    	    	//listView.setAdapter(adapter); 
                setListAdapter(adapter);
    		}else{
    			showToast("usuarios == null");
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
			strs.add("Usuario "+Integer.toString(i));
		}
		
    	//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
    	//    	  android.R.layout.simple_list_item_1, android.R.id.text1, strs);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, strs);
        
    	// Assign adapter to ListView
    	//listView.setAdapter(adapter);
        setListAdapter(adapter);
    }
    
    private void loadListViewHardCoreData() {
    	//ListView listView = (ListView) findViewById(R.id.contactosListView);
    	String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
    	  "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
    	  "Linux", "OS/2" };

    	// First paramenter - Context
    	// Second parameter - Layout for the row
    	// Third parameter - ID of the TextView to which the data is written
    	// Forth - the Array of data
    	//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
    	//  android.R.layout.simple_list_item_1, android.R.id.text1, values);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);
    	
    	// Assign adapter to ListView
    	//listView.setAdapter(adapter); 
        setListAdapter(adapter);
	}


}
