package javaBB;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import persistencia.Categoria;
import persistencia.Empresa;
import persistencia.SitioInteres;

import negocios.GestionAutenticacion;
import negocios.GestionEmpresas;
import negocios.GestionSitioInteres;

@ManagedBean(name = "altaSitioInteres", eager = true)
@SessionScoped
public class AltaSitioInteresBB {
	
	private String nombre;
	private String calendario;
	
	
	private String descripcion;
	
	private float latitud;
	private float longitud;
	
	private Object[] logoData;
	
	private List<Categoria> categorias;
	private List<Categoria> categoriasSelected;
	private List<String> nombresCategoria;
	private List<String> nombresCategoriaSelected;
	
	private boolean exito;
	
	@EJB
	private GestionSitioInteres gs;
	
	@EJB
	private GestionEmpresas ge;
	
	
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
		this.nombresCategoriaSelected = new LinkedList<String>();
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

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public List<Categoria> getCategoriasSelected() {
		return categoriasSelected;
	}

	public void setCategoriasSelected(List<Categoria> categoriasSelected) {
		this.categoriasSelected = categoriasSelected;
	}
	
}