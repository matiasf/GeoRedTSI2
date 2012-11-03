package com.geored.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.geored.rest.R;
import com.geored.rest.ServicioRestUsuarios;
import com.geored.rest.data.Usuario;
import com.geored.rest.exception.NotFoundException;
import com.geored.rest.exception.RestBlowUpException;
import com.geored.rest.exception.UnauthorizedException;
import android.content.Intent;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.os.AsyncTask;
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

	private void loadListView() {
		progressBar.show();
		RegistryAsyncTask task = new RegistryAsyncTask();
		task.execute();
	}
	    
	private void loadListView(List<Usuario> usuarios) {
    	try{    		
    		
    		List<String> strs = new ArrayList<String>(); 
    		//List<Usuario> usuarios = ServicioRestUsuarios.getContactos();
    		if (usuarios != null){
    			showToast(Integer.toString(usuarios.size()));
        		Iterator<Usuario> it = usuarios.iterator();
        		while(it.hasNext()){
        			Usuario usuario  = (Usuario)it.next();
        			strs.add(usuario.getNombre());
        		}
        		
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ContactosActivity.this,
                        android.R.layout.simple_list_item_1, strs);
                
                setListAdapter(adapter);
    		}else{
    			showToast("usuarios == null");
    		}    		
    	}catch(Exception ex){
    		showToast(ex.getMessage());    		
    	}
    }
    
	private class RegistryAsyncTask extends AsyncTask<String, Void, List<Usuario>> {
	    @Override
	    protected List<Usuario> doInBackground(String... params) {
	      List<Usuario> usuarios;
			try {
				usuarios = ServicioRestUsuarios.getContactos();
			} catch (RestBlowUpException e) {
				e.printStackTrace();
				return null;
			} catch (NotFoundException e) {
				e.printStackTrace();
				return null;
			} catch (UnauthorizedException e) {
				e.printStackTrace();
				return null;
			}
			
			return usuarios;
	    }
	
	    @Override
	    protected void onPostExecute(List<Usuario> result) {
	    	if (result != null){
	    		loadListView(result);
	    		//goToActivity(UsuarioActivity.class);
	    	}else{
	    		showToast("error");
	    	}	
	    	progressBar.dismiss();
	    }
	  }
}
