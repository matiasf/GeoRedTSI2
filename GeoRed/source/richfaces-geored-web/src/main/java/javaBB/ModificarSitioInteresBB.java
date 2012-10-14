package javaBB;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import persistencia.Empresa;
import persistencia.SitioInteres;

import negocios.GestionAutenticacion;
import negocios.GestionEmpresas;
import negocios.GestionSitioInteres;

@ManagedBean(name = "modificarSitioInteres")
@SessionScoped
public class ModificarSitioInteresBB {
	
	private String nombre;
	private boolean exito;
	
	private String descripcion;
	private Object[] logoData;
	
	private int sitioSelected;
	private List<SelectItem> sitios;
	
	@EJB
	private GestionSitioInteres gs;
	
    public ModificarSitioInteresBB() {    	
        System.out.println("altaSIBean instantiated");        
        
        /*SitioInteres sit = new SitioInteres();
        sit.setId(1);
        sit.setNombre("nombre");
        
        sitios = new LinkedList<SelectItem>();
        sitios.add(new SelectItem(1, "Sitio 1"));
        sitios.add(new SelectItem(2, "Sitio 2"));
        sitios.add(new SelectItem(3, "Sitio 3"));*/    
        
        sitios = new LinkedList<SelectItem>();
        
        this.sitioSelected = -1;
        this.exito = true;
    }  
    
    
    /* logica y navegación*/
    
    public String seleccionoSitioInteres() {    	
    	String retorno = "";
    	
    	System.out.println("sitio seleccionado" + this.sitioSelected);
        //**** LOGICA            	    	
        SitioInteres sitioInteres = gs.obtenerSitioInteres(this.sitioSelected);//??
    	this.setNombre(sitioInteres.getNombre());
    	this.setDescripcion(sitioInteres.getDescripcion());   	    	
    	//LOGICA *******/
        this.exito = true;
        return "modificar";
    }
    
    public String modificarSitioInteres() {
    	String retorno = "";
	
    	SitioInteres sitioInteres = new SitioInteres();
    	sitioInteres.setNombre(this.nombre);
    	sitioInteres.setDescripcion(this.descripcion);
    	gs.modifciarSitioInteres(sitioInteres);
    	retorno = "exito";   	    		
    	
        return retorno;
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
		//*** LOGICA            	    	
        List<SitioInteres> sitiosInteres = gs.obtenerTodosSitiosInteres();
        for (SitioInteres s : sitiosInteres){
        	sitios.add(new SelectItem(s.getId(), s.getNombre()));
        }
        //LOGICA *******/     
		return sitios;
	}


	public void setSitios(List<SelectItem> sitios) {
		this.sitios = sitios;
	}
}