package javaBB;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import persistencia.Empresa;

import negocios.GestionAutenticacion;
import negocios.GestionEmpresas;
import negocios.GestionSitioInteres;

@ManagedBean(name = "altaLocal", eager = true)
public class AltaLocalBB {
	
	private String mail;
	private String nombre;
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
    
    public String altaEmpresa() {
    	String retorno = "";
    	
    	retorno = "exito";
    	/***** LOGICA    	
    	//chequeo de admin sistema
    	Empresa empresa = new Empresa();
    	empresa.setNombre(this.nombre);
    	empresa.setMailAdmin(this.mail);
    	ge.agregarEmpresa(empresa);    	
    	retorno = "exito";   	
    	//LOGICA *******/
    	this.setExito(true);    		
    	
        return retorno;
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
}