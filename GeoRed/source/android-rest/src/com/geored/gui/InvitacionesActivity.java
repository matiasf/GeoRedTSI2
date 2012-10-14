package com.geored.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.geored.rest.R;
import com.geored.rest.ServicioRestUsuarios;
import com.geored.rest.data.Invitacion;
import com.geored.rest.exception.ConflictException;
import com.geored.rest.exception.NotFoundException;
import com.geored.rest.exception.RestBlowUpException;
import com.geored.rest.exception.UnauthorizedException;

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



public class InvitacionesActivity extends GenericActivity {
	
	protected void loadVista() {
		setContentView(R.layout.activity_invitaciones);
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
        inflater.inflate(R.menu.activity_invitaciones, menu);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    	Object itemList = getListAdapter().getItem(info.position);
        switch (item.getItemId()) {
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

	
	private void loadListView() {
    	try{    		
    		
    		List<String> strs = new ArrayList<String>(); 
    		List<Invitacion> invitaciones = ServicioRestUsuarios.getInvitaciones();
    		if (invitaciones != null){
    			showToast(Integer.toString(invitaciones.size()));
        		Iterator<Invitacion> it = invitaciones.iterator();
        		while(it.hasNext()){
        			Invitacion invitacion  = (Invitacion)it.next();
        			strs.add("id:<"+invitacion.getId()+"> remitente:<"+invitacion.getRemitente().getNombre()+">");
        		}
        		
            	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, strs);
                
    	    	setListAdapter(adapter);
    		}else{
    			showToast("invitaciones == null");
    		}    		
    	}catch(Exception ex){
    		showToast(ex.getMessage());    		
    	}
    }
    
    /*
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
    */


}
