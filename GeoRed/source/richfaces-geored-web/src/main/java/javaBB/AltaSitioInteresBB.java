package javaBB;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import persistencia.Empresa;
import persistencia.SitioInteres;

import negocios.GestionAutenticacion;
import negocios.GestionEmpresas;
import negocios.GestionSitioInteres;

@ManagedBean(name = "altaSitioInteres", eager = true)
@SessionScoped
public class AltaSitioInteresBB {
	
	private String nombre;
	
	
	private String descripcion;
	
	private float latitud;
	private float longitud;
	
	private Object[] logoData;
	
	private boolean exito;
	
	@EJB
	private GestionSitioInteres gs;
	
	
    public AltaSitioInteresBB() {    	
        System.out.println("altaSIBean instantiated");        
        this.exito = true;
    }
    
    /* logica y navegación*/
    
    public String altaSitioInteres() {
    	String retorno = "";
    	
    	retorno = "exito";

    	SitioInteres sitioInteres = new SitioInteres();
    	sitioInteres.setNombre(this.nombre);
    	sitioInteres.setDescripcion(this.descripcion);
    	sitioInteres.setLatitud(this.latitud);
    	sitioInteres.setLongitud(this.longitud);    	
    	gs.agregarSitioInteres(sitioInteres);
    	retorno = "exito";   	

    	this.setExito(true);
    	FacesContext context = FacesContext.getCurrentInstance(); 
        context.getExternalContext().getSessionMap().remove("ModificarSitioInteresBB");
    	
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

	public float getLatitud() {
		return latitud;
	}

	public void setLatitud(float latitud) {
		this.latitud = latitud;
	}

	public float getLongitud() {
		return longitud;
	}

	public void setLongitud(float longitud) {
		this.longitud = longitud;
	}
}