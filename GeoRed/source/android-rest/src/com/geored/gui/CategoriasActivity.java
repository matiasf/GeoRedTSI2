package com.geored.gui;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import com.geored.rest.R;
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
	private CategoriaAdapter adapter = null;
	
	protected void loadVista() {
    	setContentView(R.layout.activity_categorias);
    	
        data = new ArrayList<Categoria>();
        
        adapter = new CategoriaAdapter(this, 
                        R.layout.activity_categoria_item, data);
                
                
        listView1 = (ListView)findViewById(R.id.categoriaslistView);
                 
        View header = (View)getLayoutInflater().inflate(R.layout.activity_categorias_footer_row, null);
        listView1.addHeaderView(header);
        
        
        
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
        
        progressBar.show();
		GetCategoriasAsyncTask task = new GetCategoriasAsyncTask();
		task.execute(new String[] { "" });
        
		//data.add(new Categoria(1,"uno","uno"));
        //data.add(new Categoria(2,"dos","uno"));
        //data.add(new Categoria(3, "tres","uno"));
        //data.add(new Categoria(4, "cuatro","uno"));
    	
    }
	
	private void continuar() throws RestBlowUpException, UnauthorizedException {
		
		Iterator<Categoria> it = adapter.getSelected().values().iterator();
		List<Integer> categorias = new ArrayList<Integer>();
		while(it.hasNext()){
			Categoria cat  = (Categoria)it.next();
			
			categorias.add(cat.getId());
		}
		ServicioRestUsuarios.agregarCategorias(categorias);
		
}

private class AgregarAsyncTask extends AsyncTask<String, Void, String> {
	@Override
	protected String doInBackground(String... params) {
		try{
			continuar();
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
		return "Exito";
	}

    @Override
    protected void onPostExecute(String result) {
    	if (result != null){
    		showToast("categorias agregadas");
    	}else{
    		showToast("error al agregar las categorias");
    	}
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
	    	progressBar.dismiss();
	    	adapter.notifyDataSetChanged();
	    }
	  }
    
}
