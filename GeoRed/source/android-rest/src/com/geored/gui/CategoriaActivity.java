package com.geored.gui;

import com.geored.rest.R;
import com.geored.rest.data.Categoria;

import android.view.View;
import android.widget.ListView;

public class CategoriaActivity extends GenericActivity {

	private ListView listView1;
	
	protected void loadVista() {
    	setContentView(R.layout.activity_categorias);
    	
        
        Categoria mensaje_data[] = new Categoria[]
                {
                    new Categoria(1,"uno","uno"),
                    new Categoria(2,"dos","uno"),
                    new Categoria(3, "tres","uno"),
                    new Categoria(4, "cuatro","uno")
                    
                };
                
        CategoriaAdapter adapter = new CategoriaAdapter(this, 
                        R.layout.activity_chat_item, mensaje_data);
                
                
        listView1 = (ListView)findViewById(R.id.categoriaslistView);
                 
        //View header = (View)getLayoutInflater().inflate(R.layout.activity_chat_header_row, null);
        //listView1.addHeaderView(header);
                
        
        listView1.setAdapter(adapter);
    }
	
/*
    private ListView listView1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Weather weather_data[] = new Weather[]
        {
            new Weather(R.drawable.weather_cloudy, "Cloudy"),
            new Weather(R.drawable.weather_showers, "Showers"),
            new Weather(R.drawable.weather_snow, "Snow"),
            new Weather(R.drawable.weather_storm, "Storm"),
            new Weather(R.drawable.weather_sunny, "Sunny")
        };
        
        WeatherAdapter adapter = new WeatherAdapter(this, 
                R.layout.listview_item_row, weather_data);
        
        
        listView1 = (ListView)findViewById(R.id.listView1);
         
        View header = (View)getLayoutInflater().inflate(R.layout.listview_header_row, null);
        listView1.addHeaderView(header);
        
        listView1.setAdapter(adapter);
	    
  */  
    
}
