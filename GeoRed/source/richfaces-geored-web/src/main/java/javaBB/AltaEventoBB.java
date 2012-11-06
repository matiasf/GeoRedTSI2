package javaBB;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
public class AltaEventoBB {
	
	private String nombre;
	private String descripcion;
	
	private float latitud;
	private float longitud;
	
	private Date fechaComienzo;
	private Date fechaFin;
	
	private Imagen imagen;	
	
	private List<Categoria> categorias;
	private List<Categoria> categoriasSelected;
	private List<String> nombresCategoria;
	private List<String> nombresCategoriaSelected;
	
	
	private List<SitioInteres> sitios;
	
	private int objectSelected;
	private List<SelectItem> objects;
	
	private boolean exito;
	
	@EJB
	private GestionSitioInteres gs;
	
	@EJB
	private GestionEmpresas ge;
	
	
    public AltaEventoBB() {    	
        System.out.println("altaEventoBean instantiated");        
        this.exito = true;        
    }
    
    /* logica y navegación*/
    
    public String alta() {
    	String retorno = "";
    	
    	Evento evento = new Evento();
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
    	
    	
    	this.categoriasSelected = new LinkedList<Categoria>();
    	for(String s : this.nombresCategoriaSelected){
    		for(Categoria cate : this.categorias){
    			if (s.equals(cate.getNombre())){
    				this.categoriasSelected.add(cate);
    			}
    		}
    	}
    	
    	evento.setCategorias(this.categoriasSelected);
    	
    	evento.setFoto(this.imagen);
    	
    	
    	try {
    		ge.altaEvento(evento);    		
    		this.setExito(true); 
    		
    		FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getSessionMap().remove("altaEventoBB");
            retorno = "exito";
    	} catch (Exception e){
    		retorno = "revento";
    	} 
    	
    	return retorno;
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

	public List<Categoria> getCategorias() {
		this.categorias = ge.obtenerCategorias();
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