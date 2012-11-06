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

@ManagedBean(name = "modificarSitioInteres")
@SessionScoped
public class ModificarSitioInteresBB {
	
	private boolean exito;
	
	private String nombre;
	private String calendario;
	private String descripcion;
	private double latitud;
	private double longitud;
	
	
	private Object[] logoData;
	
	private List<Categoria> categorias;
	private List<Categoria> categoriasSelected;
	private List<String> nombresCategoria;
	private List<String> nombresCategoriaSelected;
	
	private int sitioSelected;
	private List<SelectItem> sitios;
	
	@EJB
	private GestionSitioInteres gs;
	
	@EJB
	private GestionEmpresas ge;
	
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
                    	    	
        SitioInteres sitioInteres = gs.obtenerSitioInteres(this.sitioSelected);//??
    	this.setNombre(sitioInteres.getNombre());
    	this.setCalendario(sitioInteres.getGoogleCalendarId());
    	this.setLatitud(sitioInteres.getLatitud());
    	this.setLongitud(sitioInteres.getLongitud());
    	this.setDescripcion(sitioInteres.getDescripcion());
    	
    	this.categoriasSelected = new LinkedList<Categoria>();
    	
    	List<Categoria> aux = ge.obtenerCategoriasDeSitioInteres(sitioInteres.getId());
    	this.categorias = ge.obtenerCategorias();
    	
    	this.nombresCategoriaSelected = new LinkedList<String>();
    	
    	for(Categoria c : aux){
    		for(Categoria cate : this.categorias){
    			if (c.equals(cate.getNombre())){    				
    				this.nombresCategoriaSelected.add(cate.getNombre());
    			}
    		}
    	}  	
    	
        this.exito = true;
        
        return "modificar";
    }
    
    public String modificarSitioInteres() {
    	String retorno = "";
	
    	SitioInteres sitioInteres = new SitioInteres();
    	sitioInteres.setId(this.sitioSelected);
    	sitioInteres.setNombre(this.nombre);
    	sitioInteres.setGoogleCalendarId(this.calendario);
    	sitioInteres.setDescripcion(this.descripcion);
    	sitioInteres.setLatitud(this.latitud);
    	sitioInteres.setLongitud(this.longitud);
    	this.categoriasSelected = new LinkedList<Categoria>();
    	for(String s : this.nombresCategoriaSelected){
    		for(Categoria cate : this.categorias){
    			if (s.equals(cate.getNombre())){
    				this.categoriasSelected.add(cate);
    			}
    		}
    	}
    	sitioInteres.setCategorias(this.categoriasSelected);
    	gs.modifciarSitioInteres(sitioInteres);
    	retorno = "exito";   	    		
    	
    	FacesContext context = FacesContext.getCurrentInstance(); 
        context.getExternalContext().getSessionMap().remove("modificarSitioInteresBB");
    	
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
		sitios = new LinkedList<SelectItem>();
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
	
	public List<String> getNombresCategoria() {
		this.nombresCategoria = new LinkedList<String>();
		this.categorias = ge.obtenerCategorias();
		for(Categoria cate : this.categorias){
			nombresCategoria.add(cate.getNombre());
		}
		return nombresCategoria;
	}

	public void setNombresCategoria(List<String> nombresCategoria) {
		this.nombresCategoria = nombresCategoria;
	}

	public List<String> getNombresCategoriaSelected() {		
		return nombresCategoriaSelected;
	}

	public void setNombresCategoriaSelected(List<String> nombresCategoriaSelected) {
		this.nombresCategoriaSelected = nombresCategoriaSelected;
	}


	public String getCalendario() {
		return calendario;
	}


	public void setCalendario(String calendario) {
		this.calendario = calendario;
	}
}