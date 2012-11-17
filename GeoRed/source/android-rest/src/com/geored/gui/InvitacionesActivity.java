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
import android.os.AsyncTask;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;



public class InvitacionesActivity extends GenericActivity {
	
	@Override
	protected void goToPreviousActivity(){	
        Intent setIntent = new Intent(this,UsuarioActivity.class);
        startActivity(setIntent); 
	}
	
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
            case R.id.menu_aceptar_invitacion:
                showInvitacionAceptada(itemList.toString());
            	//showToast("Chat: pos="+info.position + " , usr="+itemList.toString());
                showToast("invitacion del contacto <"+itemList.toString()+"> aceptada");
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    
    private void showInvitacionAceptada(String idItem) {
    	if (hashUsuarios.containsKey(idItem)){
    		progressBar.show();
			String id = hashUsuarios.get(idItem).getId();
			AceptarInvitacionAsyncTask task = new AceptarInvitacionAsyncTask();
			task.execute(new String[] { id });
    	}else{
    		showToast("error antes de llamar al invitacion Aceptada");
    	}
    	
    	
	}

    private void loadListView() {
    	progressBar.show();
		RegistryAsyncTask task = new RegistryAsyncTask();
		task.execute();
    }
    
	private void loadListView(List<Invitacion> invitaciones) {
    	try{    		
    		hashUsuarios.clear();
    		List<String> strs = new ArrayList<String>(); 
    		//List<Invitacion> invitaciones = ServicioRestUsuarios.getInvitaciones();
    		if (invitaciones != null){
    			showToast(Integer.toString(invitaciones.size()));
        		Iterator<Invitacion> it = invitaciones.iterator();
        		while(it.hasNext()){
        			Invitacion invitacion  = (Invitacion)it.next();
        			
        			String valueToShow = "id:<"+invitacion.getId()+"> remitente:<"+invitacion.getRemitente().getNombre()+">";
        			strs.add(valueToShow);
        			
        			hashUsuarios.put(valueToShow, invitacion.getRemitente());
        		}
        		
            	ArrayAdapter<String> adapter = new ArrayAdapter<String>(InvitacionesActivity.this,
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
	private class RegistryAsyncTask extends AsyncTask<String, Void, List<Invitacion> > {
	    @Override
	    protected List<Invitacion>  doInBackground(String... params) {
	    	List<Invitacion> invitaciones;
			try {
				invitaciones = ServicioRestUsuarios.getInvitaciones();
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
			
			return invitaciones;
	    }
	
	    @Override
	    protected void onPostExecute(List<Invitacion> result) {
	    	if (result != null){
	    		loadListView(result);
	    		//goToActivity(UsuarioActivity.class);
	    	}else{
	    		showToast("error");
	    	}	    	
	    	progressBar.dismiss();
	    }
	  }
	private class AceptarInvitacionAsyncTask extends AsyncTask<String, Void, String> {
	    @Override
	    protected String  doInBackground(String... params) {
	    	try {
				ServicioRestUsuarios.aceptarInvitacion(params[0]);
			} catch (RestBlowUpException e) {
				e.printStackTrace();
				return null;
			} catch (NotFoundException e) {
				e.printStackTrace();
				return null;
			} catch (UnauthorizedException e) {
				e.printStackTrace();
				return null;
			} catch (ConflictException e) {
				e.printStackTrace();
				return null;
			}
			
			return "Exito";
	    }
	
	    @Override
	    protected void onPostExecute(String result) {
	    	if (result != null){
	    		Intent i = new Intent(getApplicationContext(), UsuarioActivity.class);
		    	startActivity(i);
	    		//loadListView(result);
	    		//goToActivity(UsuarioActivity.class);
	    	}else{
	    		showToast("error");
	    	}	 
	    	progressBar.dismiss();
	    }
	  }

}
