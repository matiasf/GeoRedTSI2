package javaBB;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import persistencia.Empresa;
import persistencia.Evento;
import persistencia.Local;

import negocios.GestionAutenticacion;
import negocios.GestionEmpresas;
import negocios.GestionSitioInteres;
import negocios.excepciones.EntidadNoExiste;

@ManagedBean(name = "modificarLocal", eager = true)
public class ModificarLocalBB {
	
	private String mail;
	
	private String nombre;
	private String descripcion;
	private double latitud;
	private double longitud;
	
	
	private boolean exito;
 
	private String gmkey;
	private int zoom;
	private int currentPlace;
	private int currentId;
	
	private int objectSelected;
	private List<SelectItem> objects;
	
	@EJB
	private GestionEmpresas ge;
	
    public ModificarLocalBB() {
        System.out.println("modificarLocalBean instantiated");
        this.gmkey = "AIzaSyAuspGlgx94OHwLEhGPCETxd1I4v28blD0";
        this.exito = true;
    }
    
    /* logica y navegación*/
    
    public String modificar() {
    	String retorno = "";
    	
    	FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)context.getExternalContext().getSession(true);
    	Integer idEmpresa = (Integer) session.getAttribute("idEmpresa");
    	    	
    	
    	Local local = new Local();
    	local.setId(this.objectSelected);
    	local.setDescripcion(this.descripcion);
    	local.setNombre(this.nombre);
    	local.setLatitud(this.latitud);
    	local.setLongitud(this.longitud);
    	
		ge.modificarLocal(local);
		this.setExito(true);
		retorno = "exito";
		context.getExternalContext().getSessionMap().remove("modificarLocalBB");       
			
			
		 
    	
        return retorno;
    }
    
    public String selecciono() {    	
    	String retorno = "";
    	
    	System.out.println("local seleccionado" + this.objectSelected);
                    	    	
    	
        Local local = ge.obtenerLocal(this.objectSelected);
        this.nombre = local.getNombre();
        this.descripcion = local.getDescripcion();
        this.latitud = local.getLatitud();
        this.longitud = local.getLongitud();
    	
        this.exito = true;
        
        return "modificar";
    }
    
    public String finalizar() {
    	String retorno = "";
    	
    	FacesContext context = FacesContext.getCurrentInstance(); 
        context.getExternalContext().getSessionMap().remove("modificarLocalBB");        
    	retorno = "finalizar";   		
    	
        return retorno;
    }
    
    public String cancelar() {
    	String retorno = "";
    	FacesContext context = FacesContext.getCurrentInstance(); 
        context.getExternalContext().getSessionMap().remove("modificarLocalBB");
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

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public int getObjectSelected() {
		return objectSelected;
	}

	public void setObjectSelected(int objectSelected) {
		this.objectSelected = objectSelected;
	}

	public List<SelectItem> getObjects() {
		FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)context.getExternalContext().getSession(true);
    	Integer idEmpresa = (Integer) session.getAttribute("idEmpresa");
    	this.objects = new LinkedList<SelectItem>();
        List<Local> aux = new LinkedList<Local>();
		try {
			aux = ge.obtenerLocalesDeEmpresa(idEmpresa);
		} catch (EntidadNoExiste e) {			
			e.printStackTrace();
		}
        for (Local l : aux){
        	this.objects.add(new SelectItem(l.getId(), l.getNombre()));
        }		
		return objects;
	}

	public void setObjects(List<SelectItem> objects) {
		this.objects = objects;
	}
}