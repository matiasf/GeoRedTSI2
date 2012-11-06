package javaBB;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.validation.constraints.Size;

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
	
	private HashMap<String, Categoria> categorias;
	private List<String> nombresCategoria;
	private List<String> nombresCategoriaSelected;
	
	private HashMap<String, Categoria> complementoCats;
	private HashMap<String, Categoria> todasCategorias;

	@Size(min=1, message = "Debe seleccionar un sitio")
	private int sitioSelected;
	private List<SelectItem> sitios;
	
	@EJB
	private GestionSitioInteres gs;
	
	@EJB
	private GestionEmpresas ge;
	
    public ModificarSitioInteresBB() {    	
        System.out.println("altaSIBean instantiated");        
        
        
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
    	
		this.categorias = new HashMap<String, Categoria>();
		this.todasCategorias = new HashMap<String, Categoria>();
		List<Categoria> todas = ge.obtenerCategorias();
		List<Categoria> pertenecen = ge.obtenerCategoriasDeSitioInteres(sitioSelected);
		this.complementoCats = new HashMap<String, Categoria>();
		this.nombresCategoria = new LinkedList<String>();
		this.nombresCategoriaSelected = new LinkedList<String>();

		for (Categoria categoria : pertenecen) {
			this.categorias.put(categoria.getNombre(), categoria);
			this.nombresCategoriaSelected.add(categoria.getNombre());
		}
		
		for (Categoria categoria : todas) {
			this.todasCategorias.put(categoria.getNombre(), categoria);
			this.nombresCategoria.add(categoria.getNombre());
			if (!this.categorias.containsKey(categoria.getNombre())) {
				this.complementoCats.put(categoria.getNombre(), categoria);
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
    	
    	gs.modifciarSitioInteres(sitioInteres);
    	
    	Collection<Integer> aLevantar = new ArrayList<Integer>();
		Collection<Integer> aBorrar = new ArrayList<Integer>();
    	
    	for (String nombre : nombresCategoriaSelected) {
			aLevantar.add(todasCategorias.get(nombre).getId());
		}
		
		for (String nombre : nombresCategoria) {
			if (categorias.containsKey(nombre)) {
				aBorrar.add(categorias.get(nombre).getId());
			}
		}
		
		gs.borrarCategoriasSitio(sitioInteres.getId(), aBorrar);
		gs.agregarCategoriaSitio(sitioInteres.getId(), aLevantar);
    	
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


	public HashMap<String, Categoria> getCategorias() {
		return categorias;
	}


	public void setCategorias(HashMap<String, Categoria> categorias) {
		this.categorias = categorias;
	}


	public HashMap<String, Categoria> getComplementoCats() {
		return complementoCats;
	}


	public void setComplementoCats(HashMap<String, Categoria> complementoCats) {
		this.complementoCats = complementoCats;
	}


	public HashMap<String, Categoria> getTodasCategorias() {
		return todasCategorias;
	}


	public void setTodasCategorias(HashMap<String, Categoria> todasCategorias) {
		this.todasCategorias = todasCategorias;
	}
	
	
}