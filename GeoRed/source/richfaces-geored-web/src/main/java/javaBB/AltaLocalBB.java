package javaBB;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import persistencia.Empresa;
import persistencia.Local;

import negocios.GestionAutenticacion;
import negocios.GestionEmpresas;
import negocios.GestionSitioInteres;
import negocios.excepciones.EntidadNoExiste;

@ManagedBean(name = "altaLocal", eager = true)
public class AltaLocalBB {
	
	private String mail;
	
	private String nombre;
	private String descripcion;
	private float latitud;
	private float longitud;
	
	
	private boolean exito;
 
	private String gmkey;
	private int zoom;
	private int currentPlace;
	private int currentId;
	
	
	@EJB
	private GestionEmpresas ge;
	
    public AltaLocalBB() {
        System.out.println("altaLocalBean instantiated");
        this.gmkey = "AIzaSyAuspGlgx94OHwLEhGPCETxd1I4v28blD0";
        this.exito = true;
    }
    
    /* logica y navegación*/
    
    public String altaLocal() {
    	String retorno = "";
    	
    	FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)context.getExternalContext().getSession(true);
    	Integer idEmpresa = (Integer) session.getAttribute("idEmpresa");
    	    	
    	try {
	    	Local local = new Local();
	    	local.setDescripcion(this.descripcion);
	    	local.setNombre(this.nombre);
	    	local.setLatitud(this.latitud);
	    	local.setLongitud(this.longitud);
			ge.agregarLocal(idEmpresa, local);
			this.setExito(true);
			retorno = "exito";
			context.getExternalContext().getSessionMap().remove("altaLocalBB");        
			
			
		} catch (EntidadNoExiste e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   		
    	
        return retorno;
    }
    
    public String finalizar() {
    	String retorno = "";
    	
    	FacesContext context = FacesContext.getCurrentInstance(); 
        context.getExternalContext().getSessionMap().remove("altaLocalBB");        
    	retorno = "finalizar";   		
    	
        return retorno;
    }
    
    public String cancelar() {
    	String retorno = "";
    	FacesContext context = FacesContext.getCurrentInstance(); 
        context.getExternalContext().getSessionMap().remove("altaLocalBB");
    	retorno = "cancelar";   		
    	
        return retorno;
    }
    
    
    
    
    /* setters y getters */
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
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

	public String getGmkey() {
		return gmkey;
	}

	public void setGmkey(String gmkey) {
		this.gmkey = gmkey;
	}

	public int getZoom() {
		return zoom;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	public int getCurrentPlace() {
		return currentPlace;
	}

	public void setCurrentPlace(int currentPlace) {
		this.currentPlace = currentPlace;
	}

	public int getCurrentId() {
		return currentId;
	}

	public void setCurrentId(int currentId) {
		this.currentId = currentId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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