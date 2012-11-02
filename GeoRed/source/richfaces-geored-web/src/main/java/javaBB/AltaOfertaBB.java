package javaBB;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import persistencia.Categoria;
import persistencia.Empresa;
import persistencia.Oferta;
import persistencia.SitioInteres;

import negocios.GestionAutenticacion;
import negocios.GestionEmpresas;
import negocios.GestionSitioInteres;

@ManagedBean(name = "altaOferta", eager = true)
@SessionScoped
public class AltaOfertaBB {
	
	private String nombre;
	private String descripcion;
	
	private boolean exito;
	
	@EJB
	private GestionEmpresas ge;
	
	
    public AltaOfertaBB() {    	
        System.out.println("altaOfertaBean instantiated");        
        this.exito = true;        
    }
    
    /* logica y navegación*/
    
    public String altaOferta() {
    	String retorno = "";
    	/*
    	Oferta oferta = new Oferta();
    	oferta.s
    	
    	Categoria categoria = new Categoria();
    	categoria.setNombre(this.nombre);
    	categoria.setDescripcion(this.descripcion);
    	    	
    	try {
    		ge.altaCategoria(categoria);    		
    		this.setExito(true);
        	FacesContext context = FacesContext.getCurrentInstance(); 
            context.getExternalContext().getSessionMap().remove("altaCategoriaBB");
            retorno = "exito";
    	} catch (Exception e){
    		retorno = "revento";
    	}
    	*/
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

}