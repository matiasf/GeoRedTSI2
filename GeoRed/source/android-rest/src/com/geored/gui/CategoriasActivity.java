package com.geored.gui;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import com.geored.rest.R;
import com.geored.rest.ServicioRestGCM;
import com.geored.rest.ServicioRestUsuarios;
import com.geored.rest.data.Categoria;
import com.geored.rest.data.Mensaje;
import com.geored.rest.exception.NotFoundException;
import com.geored.rest.exception.RestBlowUpException;
import com.geored.rest.exception.UnauthorizedException;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class CategoriasActivity extends GenericActivity {

	private ListView listView1;
	
	private List<Categoria> data = null;
	private Button continuarButton = null; 
	
	protected void loadVista() {
    	setContentView(R.layout.activity_categorias);
    	
        data = new ArrayList<Categoria>();
        
        CategoriaAdapter adapter = new CategoriaAdapter(this, 
                        R.layout.activity_categoria_item, data);
                
                
        listView1 = (ListView)findViewById(R.id.categoriaslistView);
                 
        //View header = (View)getLayoutInflater().inflate(R.layout.activity_chat_header_row, null);
        //listView1.addHeaderView(header);
                
        View footer = (View)getLayoutInflater().inflate(R.layout.activity_categorias_footer_row, null);
        listView1.addFooterView(footer);
        
        continuarButton = (Button)findViewById(R.id.continuarButton);
        continuarButton.setOnClickListener(new Button.OnClickListener() { 
        	public void onClick (View v){
        			progressBar.show();
        			AgregarAsyncTask task = new AgregarAsyncTask();
        			task.execute(new String[] { "" });
        			//enviarMensaje(); 
        		}
        	});
        
        listView1.setAdapter(adapter);
        

		GetCategoriasAsyncTask task = new GetCategoriasAsyncTask();
		task.execute(new String[] { "" });
        
		//data.add(new Categoria(1,"uno","uno"));
        //data.add(new Categoria(2,"dos","uno"));
        //data.add(new Categoria(3, "tres","uno"));
        //data.add(new Categoria(4, "cuatro","uno"));
    	
    }
	
	private Mensaje continuar(String mensaje, String id) throws RestBlowUpException, UnauthorizedException{
		
		//Mensaje msj = new Mensaje(Integer.parseInt(id), mensaje);
		//ServicioRestGCM.enviarMensaje(msj);
		
		return null;
}

private class AgregarAsyncTask extends AsyncTask<String, Void, Mensaje> {
	@Override
	protected Mensaje doInBackground(String... params) {
		try{
			return continuar(params[0], params[1]);
		} catch (RestBlowUpException e) {
			Log.e("Error", "Rest blow up!", e);
			return null;
		} catch (UnauthorizedException e) {
			Log.w("Warning", "Unautorized!", e);
			return null;
		}catch(Exception ex){
			showToast("error al mandar el mensaje");
			return null;
		}
	}

    @Override
    protected void onPostExecute(Mensaje result) {
    	if (result != null){
    		showToast("mensaje enviado");
    	}else{
    		showToast("error al enviar el mensaje");
    	}
    	//((MensajeAdapter)listView1.getAdapter()).add(result);
    	//((MensajeAdapter)listView1.getAdapter()).notifyDataSetChanged();
    	progressBar.dismiss();
    }
}
	
protected List<Categoria> GetCategorias() throws RestBlowUpException, UnauthorizedException, NotFoundException{
    	
    	List<Categoria> cat = ServicioRestUsuarios.getCategorias();
    	
    	return cat;
    }
	    
	protected Hashtable<String, Categoria>  hashCategorias = new Hashtable<String,Categoria> ();

	private void loadListView(List<Categoria> categorias) {
		try{    		
			hashUsuarios.clear();
			List<String> strs = new ArrayList<String>(); 
			if (categorias != null){
				showToast(Integer.toString(categorias.size()));
	    		Iterator<Categoria> it = categorias.iterator();
	    		while(it.hasNext()){
	    			Categoria cat  = (Categoria)it.next();
	    			strs.add(cat.getNombre());
	    			
	    			hashCategorias.put(Integer.toString(cat.getId()), cat);
	    			
	    			data.add(cat);
	    		}
			}else{
				showToast("usuarios == null");
			}    		
		}catch(Exception ex){
			showToast(ex.getMessage());    		
		}
	}
			
	private class GetCategoriasAsyncTask extends AsyncTask<String, Void, List<Categoria>> {
	    @Override
	    protected List<Categoria> doInBackground(String... params) {
	  	  List<Categoria> cat = null;
	    	try {
	    		
		    	 cat = GetCategorias();

	    	} catch (RestBlowUpException e) {
				e.printStackTrace();
				return null;
			} catch (UnauthorizedException e) {
				e.printStackTrace();
				return null;
			} catch (NotFoundException e) {
				
				e.printStackTrace();
				return null;
			}
	   		return cat;
	    }
	
	    @Override
	    protected void onPostExecute( List<Categoria>  result) {
	    	if (result != null){
	    		showToast(Integer.toString(result.size()));
	    		/*
	    		data.add(new Categoria(1,"uno","uno"));
	            data.add(new Categoria(2,"dos","uno"));
	            data.add(new Categoria(3, "tres","uno"));
	            data.add(new Categoria(4, "cuatro","uno"));
	            */
	            loadListView(result);
	    	}else{
	    		showToast("las categorias no se pudieron cargar");
	    	}    	
	    	
	    }
	  }
    
}
