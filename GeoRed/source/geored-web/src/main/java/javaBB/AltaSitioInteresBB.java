package javaBB;

import javax.faces.bean.ManagedBean;

import persistencia.Empresa;
import persistencia.SitioInteres;

import negocios.GestionAutenticacion;
import negocios.GestionEmpresas;
import negocios.GestionSitioInteres;

@ManagedBean(name = "altaSitioInteres", eager = true)
public class AltaSitioInteresBB {
	
	private String nombre;
	private boolean exito;
	
	private String descripcion;
	private Object[] logoData;
	
	
    public AltaSitioInteresBB() {    	
        System.out.println("altaSIBean instantiated");        
        this.exito = true;
    }
    
    /* logica y navegación*/
    
    public void altaSitioInteres() {
    	String retorno = "";
    	
    	retorno = "exito";
    	/***** LOGICA
    	GestionSitioInteres gs = null;
    	//chequeo de admin sistema
    	SitioInteres sitioInteres = new SitioInteres();
    	sitioInteres.setNombre(this.nombre);
    	sitioInteres.setDescripcion(this.descripcion);
    	gs.agregarSitioInteres(sitioInteres);
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
}