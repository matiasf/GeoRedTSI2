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
import persistencia.Imagen;
import persistencia.Local;
import persistencia.Oferta;
import persistencia.SitioInteres;

import negocios.GestionAutenticacion;
import negocios.GestionEmpresas;
import negocios.GestionSitioInteres;
import negocios.excepciones.EntidadNoExiste;

@ManagedBean(name = "altaOferta", eager = true)
@SessionScoped
public class AltaOfertaBB {
	
	private String nombre;
	private String descripcion;
	
	private Date fechaComienzo;
	private Date fechaFin;
	
	private Imagen imagen;	
	
	private List<Categoria> categorias;
	private List<Categoria> categoriasSelected;
	private List<String> nombresCategoria;
	private List<String> nombresCategoriaSelected;	
	
	private List<Local> locales;
	
	private float costo;
	
	private int objectSelected;
	private List<SelectItem> objects;
	
	private boolean exito;
	
	@EJB
	private GestionEmpresas ge;
	
	
    public AltaOfertaBB() {    	
        System.out.println("altaOfertaBean instantiated");        
        this.exito = true;        
    }
    
    /* logica y navegación*/
    
    public String alta() {
    	String retorno = "";
    	
    	Oferta oferta = new Oferta();
    	oferta.setNombre(this.nombre);
    	oferta.setDescripcion(this.descripcion);
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(this.fechaComienzo);    	
    	oferta.setComienzo(cal);
    	Calendar cal2 = Calendar.getInstance();
    	cal2.setTime(this.fechaFin);    	
    	oferta.setFin(cal2);
    	oferta.setFoto(this.imagen);    	
    	oferta.setCosto(this.costo);
    	
    	this.categoriasSelected = new LinkedList<Categoria>();
    	for(String s : this.nombresCategoriaSelected){
    		for(Categoria cate : this.categorias){
    			if (s.equals(cate.getNombre())){
    				this.categoriasSelected.add(cate);
    			}
    		}
    	}
    	
    	oferta.setCategorias(this.categoriasSelected);
    	
    	try {
    		FacesContext context = FacesContext.getCurrentInstance();
    		HttpSession session = (HttpSession)context.getExternalContext().getSession(true);
    		String mailEmpresa = (String) session.getAttribute("mailEmpresa");
        	Empresa empresa = ge.obtenerEmpresaPorMail(mailEmpresa);
        	this.locales = ge.obtenerLocalesDeEmpresa(empresa.getId());
        	for(Local loc : this.locales){
        		if (loc.getId() == this.objectSelected){
        			oferta.setLocal(loc);
        			break;        	
        		}
        	}
    		ge.agregarOferta(this.objectSelected, oferta);
    		this.setExito(true); 
            context.getExternalContext().getSessionMap().remove("altaOfertaBB");
            
            StatusBB statusBB = (StatusBB) context.getExternalContext().getSessionMap().get("statusBB");
            statusBB.setExito(true);
            statusBB.setError(false);
            statusBB.setExitoMsg("Se ha dado de alta el la oferta " + oferta.getNombre());
            
            retorno = "exito";
    	} catch (Exception e){
    		retorno = "revento";
    	} catch (EntidadNoExiste e) {
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
    	FacesContext context = FacesContext.getCurrentInstance(); 
    	StatusBB statusBB = (StatusBB) context.getExternalContext().getSessionMap().get("statusBB");
        if (statusBB != null) {
        	statusBB.setExito(false);
        	statusBB.setError(false);
        }
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
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession)context.getExternalContext().getSession(true);
		String mailEmpresa = (String) session.getAttribute("mailEmpresa");
    	Empresa empresa = ge.obtenerEmpresaPorMail(mailEmpresa);
    	try {
			this.locales = ge.obtenerLocalesDeEmpresa(empresa.getId());
			for(Local loc : this.locales){
	        	this.objects.add(new SelectItem(loc.getId(), loc.getNombre()));
	        }
		} catch (EntidadNoExiste e) {
			
			e.printStackTrace();
		}    	
		return objects;
	}

	public void setObjects(List<SelectItem> objects) {
		this.objects = objects;
	}

	public float getCosto() {
		return costo;
	}

	public void setCosto(float costo) {
		this.costo = costo;
	}

	public List<Local> getLocales() {
		return locales;
	}

	public void setLocales(List<Local> locales) {
		this.locales = locales;
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
	
}