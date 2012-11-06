package javaBB;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

import persistencia.Categoria;
import persistencia.Empresa;
import persistencia.Evento;
import persistencia.Imagen;
import persistencia.Local;
import persistencia.Oferta;
import persistencia.SitioInteres;

import negocios.GestionAutenticacion;
import negocios.GestionEmpresas;
import negocios.GestionSitioInteres;
import negocios.excepciones.EntidadNoExiste;

@ManagedBean(name = "altaEvento", eager = true)
@SessionScoped
public class ModificarEventoBB {
	
	private String nombre;
	private String descripcion;
	
	private double latitud;
	private double longitud;
	
	private Date fechaComienzo;
	private Date fechaFin;
	
	private Imagen imagen;	
	
	private HashMap<String, Categoria> categorias;
	private List<String> nombresCategoria;
	private List<String> nombresCategoriaSelected;
	
	private HashMap<String, Categoria> complementoCats;
	private HashMap<String, Categoria> todasCategorias;

	
	
	private List<SitioInteres> sitios;
	
	private int objectSelected;
	private List<SelectItem> objects;
	
	private int eventoSelected;
	private List<SelectItem> eventos;
	
	private boolean exito;
	
	@EJB
	private GestionSitioInteres gs;
	
	@EJB
	private GestionEmpresas ge;
	
	
    public ModificarEventoBB() {    	
        System.out.println("modificarEventoBean instantiated");        
        this.exito = true;        
    }
    
    /* logica y navegación*/
    
    public String modificar() {
    	String retorno = "";
    	
    	Evento evento = new Evento();
    	evento.setId(this.eventoSelected);
    	evento.setNombre(this.nombre);
    	evento.setDescripcion(this.descripcion);
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(this.fechaComienzo);
    	evento.setInicio(cal);    	
    	Calendar cal2 = Calendar.getInstance();
    	cal2.setTime(this.fechaFin);    	
    	evento.setFin(cal2);
    	
    	if (this.objectSelected == -1){
    		evento.setLatitud(this.latitud);
    		evento.setLongitud(this.longitud);
    	} else {
    		SitioInteres sitio = gs.obtenerSitioInteres(this.objectSelected);
        	evento.setLatitud(sitio.getLatitud());
        	evento.setLongitud(sitio.getLongitud());	
    	}
    	
    	evento.setFoto(this.imagen);
    	
    	
    	try {
    		ge.modificarEvento(evento);    	
    		
    		
    		
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
    		
    		ge.borrarCategoriasEvento(evento.getId(), aBorrar);
    		ge.agregarCategoriasEvento(evento.getId(), aLevantar);
    		
    		
    		this.setExito(true); 
    		
    		FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getSessionMap().remove("modificarEventoBB");
            retorno = "exito";
    	} catch (Exception e){
    		retorno = "revento";
    	} 
    	
    	return retorno;
    }
    
    public String selecciono() {    	
    	String retorno = "";
    	
    	System.out.println("evento seleccionado" + this.eventoSelected);
                    	    	
        Evento evento = ge.obtenerEvento(this.eventoSelected);
        
    	this.setNombre(evento.getNombre());
    	this.setDescripcion(evento.getDescripcion());
    	this.fechaComienzo = evento.getInicio().getTime();
    	this.fechaFin = evento.getFin().getTime();
    	this.latitud = (float) evento.getLatitud();
    	this.longitud = (float) evento.getLongitud();
    	this.imagen = evento.getFoto();
    	
    	this.categorias = new HashMap<String, Categoria>();
		this.todasCategorias = new HashMap<String, Categoria>();
    	List<Categoria> todas = ge.obtenerCategorias();
		List<Categoria> pertenecen = ge.obtenerCategoriasEvento(eventoSelected);
    	this.complementoCats = new HashMap<String, Categoria>();
		this.nombresCategoria = new LinkedList<String>();
		this.nombresCategoriaSelected = new LinkedList<String>();
		
		for (Categoria categoria : pertenecen) {
			this.categorias.put(categoria.getNombre(), categoria);
			this.nombresCategoriaSelected.add(categoria.getNombre());
		}
		
		for (Categoria categoria : todas) {
			this.nombresCategoria.add(categoria.getNombre());
			this.todasCategorias.put(categoria.getNombre(), categoria);
			if (!this.categorias.containsKey(categoria.getNombre())) {
				this.complementoCats.put(categoria.getNombre(), categoria);
			}
		}	
    	
        this.exito = true;
        
        return "modificar";
    }
    
    
    public void logoListener(FileUploadEvent event) throws Exception{
    	UploadedFile uploadedFile = event.getUploadedFile();
    	this.imagen = new Imagen();
		this.imagen.setImagen(uploadedFile.getData());
        System.out.println("finlistener");
    }
    
    
    public void paint(OutputStream stream, Object object) throws IOException {
    	if (this.imagen != null){
    		stream.write(imagen.getImagen());
    	}
    		
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

	public Date getFechaComienzo() {
		return fechaComienzo;
	}

	public void setFechaComienzo(Date fechaComienzo) {
		this.fechaComienzo = fechaComienzo;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Imagen getImagen() {
		return imagen;
	}

	public void setImagen(Imagen imagen) {
		this.imagen = imagen;
	}

	public int getObjectSelected() {
		return objectSelected;
	}

	public void setObjectSelected(int objectSelected) {
		this.objectSelected = objectSelected;
	}

	public List<SelectItem> getObjects() {
		this.objects = new LinkedList<SelectItem>();		
		this.sitios = gs.obtenerTodosSitiosInteres();    	
		for(SitioInteres s : this.sitios){
	       	this.objects.add(new SelectItem(s.getId(), s.getNombre()));
	    }		    	
		return objects;
	}

	public void setObjects(List<SelectItem> objects) {
		this.objects = objects;
	}

	public List<SitioInteres> getSitios() {
		return sitios;
	}

	public void setSitios(List<SitioInteres> sitios) {
		this.sitios = sitios;
	}

	public HashMap<String, Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(HashMap<String, Categoria> categorias) {
		this.categorias = categorias;
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

	public int getEventoSelected() {
		return eventoSelected;
	}

	public void setEventoSelected(int eventoSelected) {
		this.eventoSelected = eventoSelected;
	}

	public List<SelectItem> getEventos() {
		GregorianCalendar dia = new GregorianCalendar(1900, 01, 01);		 
		this.eventos = new LinkedList<SelectItem>();
        List<Evento> aux = ge.obtenerEventos(dia);
        for (Evento e : aux){
        	this.eventos.add(new SelectItem(e.getId(), e.getNombre()));
        }		
		return eventos;
	}

	public void setEventos(List<SelectItem> eventos) {
		this.eventos = eventos;
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