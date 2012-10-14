package com.geored.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.geored.rest.R;
import com.geored.rest.ServicioRestUsuarios;
import com.geored.rest.data.Usuario;
//import com.geored.rest.exception.ConflictException;
//import com.geored.rest.exception.NotFoundException;
//import com.geored.rest.exception.RestBlowUpException;
//import com.geored.rest.exception.UnauthorizedException;

import android.content.Intent;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;



public class ContactosActivity extends GenericActivity {
	
	protected void loadVista() {
		setContentView(R.layout.activity_contactos);
		loadListView();
        registerForContextMenu(getListView());
	}    

	private void setListAdapter(ArrayAdapter<String> adapter) {
		getListView().setAdapter(adapter);
	}

    private ListAdapter getListAdapter() {
		return getListView().getAdapter();
	}
    
    private ListView getListView() {
    	return  ((ListView)findViewById(R.id.listView));    	
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
            //case R.id.menu_invitar_contacto:
            //    //showToast("Invitar: pos="+info.position + " , usr="+itemList.toString());
            //    showInvitar(itemList.toString());
            //    showToast("Contacto <"+itemList.toString()+"> invitado");
            //	return true;
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
/*
	private void showInvitar(String id) {
		try{
			ServicioRestUsuarios.invitarContacto(id);
			Intent i = new Intent(getApplicationContext(), UsuarioActivity.class);
			i.putExtra("user_id",usuarioId); 
	    	startActivity(i);
		}catch(NotFoundException nfbu){
    		
    		showToast("No se encontro el contacto");
        	
    	}catch(RestBlowUpException exbu){
    		
    		showToast("El servicio no responde");
        	
    	}catch(ConflictException cex){
    		
    		showToast("conflicto en los servicios");
        	
    	}catch(UnauthorizedException exu){
    		
    		showToast("El usuario no esta autorizado");
    		
    	}catch(Exception ex){    		
    		showToast(ex.getMessage());
    	}
	}
*/
	private void loadListView() {
    	try{    		
    		
    		List<String> strs = new ArrayList<String>(); 
    		List<Usuario> usuarios = ServicioRestUsuarios.getContactos();
    		if (usuarios != null){
    			showToast(Integer.toString(usuarios.size()));
        		Iterator<Usuario> it = usuarios.iterator();
        		while(it.hasNext()){
        			Usuario usuario  = (Usuario)it.next();
        			strs.add(usuario.getNombre());
        		}
        		
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, strs);
                
                setListAdapter(adapter);
    		}else{
    			showToast("usuarios == null");
    		}    		
    	}catch(Exception ex){
    		showToast(ex.getMessage());    		
    	}
    }
    
    
    
    
/*
	private void loadListViewHardCoreData2() {
    	
		List<String> strs = new ArrayList<String>(); 
		
		for(int i=0; i < 15;i++){
			strs.add("Usuario "+Integer.toString(i));
		}
		
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, strs);
        
        setListAdapter(adapter);
    }
  */  
}
