package javaBB;

import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;

import persistencia.Empresa;
import persistencia.SitioInteres;

import negocios.GestionAutenticacion;
import negocios.GestionEmpresas;
import negocios.GestionSitioInteres;

@ManagedBean(name = "modificarSitioInteres", eager = true)
public class ModificarSitioInteresBB {
	
	private String nombre;
	private boolean exito;
	
	private String descripcion;
	private Object[] logoData;
	
	private int sitioSelected;
	private List<SelectItem> sitios;
	
    public ModificarSitioInteresBB() {    	
        System.out.println("altaSIBean instantiated");        
        
        /* SitioInteres sit = new SitioInteres();
        sit.setId(1);
        sit.setNombre("nombre");*/
        
        sitios = new LinkedList<SelectItem>();
        sitios.add(new SelectItem(1, "Sitio 1"));
        sitios.add(new SelectItem(2, "Sitio 2"));
        sitios.add(new SelectItem(3, "Sitio 3"));
        
        
        /**** LOGICA
        GestionSitioInteres gs = null;    	    	
        List<SitioInteres> sitiosInteres = gs.obtenerTodosSitiosInteres();
        for (SitioInteres s : sitiosInteres){
        	sitios.add(new SelectItem(s.getId(), s.getNombre()));
        }
        LOGICA *******/     
        
        this.sitioSelected = -1;
        this.exito = true;
    }  
    
    
    /* logica y navegación*/
    
    public String seleccionoSitioInteres() {    	
    	String retorno = "";
    	
    	System.out.println("sitio seleccionado" + this.sitioSelected);
        /**** LOGICA
        GestionSitioInteres gs = null;    	    	
        SitioInteres sitioInteres = gs.obtenerSitioInters(id);//??
    	this.setNombre(sitioInteres.nombre);
    	this.setDescripcion(sitioInteres.descripcion);   	    	
    	LOGICA *******/
        this.exito = true;
        return "modificar";
    }
    
    public void modificarSitioInteres() {
    	String retorno = "";
    	
    	retorno = "exito";
    	/***** LOGICA
    	GestionSitioInteres gs = null;
    	//chequeo de admin sistema
    	SitioInteres sitioInteres = new SitioInteres();
    	sitioInteres.setNombre(this.nombre);
    	sitioInteres.setDescripcion(this.descripcion);
    	gs.modifciarSitioInteres(sitioInteres);
    	retorno = "exito";   	
    	LOGICA *******/
    	this.setExito(true);    		
    	
        //return retorno;
    }
    
    public void logoListener() {
    	
    }
    
    public String finalizar() {
    	String retorno = "";
    	
    	//removerBB
    	retorno = "finalizar";   		
    	
        return retorno;
    }
    
    public String cancelar() {
    	String retorno = "";
    	
    	//removerBB
    	retorno = "cancelar";   		
    	
        return retorno;
    }
    
    
    
    
    /* setters y getters */

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isExito() {
		return exito;
	}

	public void setExito(boolean exito) {
		this.exito = exito;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Object[] getLogoData() {
		return logoData;
	}

	public void setLogoData(Object[] logoData) {
		this.logoData = logoData;
	}


	public int getSitioSelected() {
		return sitioSelected;
	}


	public void setSitioSelected(int sitioSelected) {
		this.sitioSelected = sitioSelected;
	}


	public List<SelectItem> getSitios() {
		return sitios;
	}


	public void setSitios(List<SelectItem> sitios) {
		this.sitios = sitios;
	}
}