package javaBB;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import persistencia.Categoria;
import persistencia.Empresa;
import persistencia.SitioInteres;

import negocios.GestionAutenticacion;
import negocios.GestionEmpresas;
import negocios.GestionSitioInteres;

@ManagedBean(name = "modificarCategoria")
@SessionScoped
public class ModificarCategoriaBB {
	
	private boolean exito;
	
	private String nombre;
	private String descripcion;
	
	private int objectSelected;
	private List<SelectItem> objects;
	
	@EJB
	private GestionEmpresas ge;
	
    public ModificarCategoriaBB() {    	
        System.out.println("modificarCategoriaBean instantiated");     
        
        this.objects = new LinkedList<SelectItem>();
        
        this.objectSelected = -1;
        this.exito = true;
    }  
    
    
    /* logica y navegación*/
    
    public String selecciono() {    	
    	String retorno = "";
    	
    	/*
    	Categoria categoria = ge.obtenerCategoria(this.objectSelected);    	
    	this.nombre = categoria.getNombre();
    	this.descripcion = categoria.getDescripcion();
    	*/
    	
        this.exito = true;
        
        return "modificar";
    }
    
    public String modificar() {
    	String retorno = "";
	
    	/*
    	Categoria categoria = new Categoria();
    	categoria.setId(this.objectSelected);
    	categoria.setNombre(this.nombre);
    	categoria.setDescripcion(this.descripcion);    
    	ge.modificiarSitioInteres(categoria);
    	*/
    	
    	retorno = "exito";   	    		
    	
    	FacesContext context = FacesContext.getCurrentInstance(); 
        context.getExternalContext().getSessionMap().remove("modificarCategoriaBB");
    	
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


	public int getObjectSelected() {
		return objectSelected;
	}


	public void setObjectSelected(int objectSelected) {
		this.objectSelected = objectSelected;
	}


	public List<SelectItem> getObjects() {
		this.objects = new LinkedList<SelectItem>();
        List<Categoria> categorias = ge.obtenerCategorias();
        for (Categoria c : categorias){
        	this.objects.add(new SelectItem(c.getId(), c.getNombre()));
        }
		return objects;
	}


	public void setObjects(List<SelectItem> objects) {
		this.objects = objects;
	}

	

	
}