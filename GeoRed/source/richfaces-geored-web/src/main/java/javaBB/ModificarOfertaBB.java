package javaBB;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

import negocios.GestionEmpresas;
import negocios.excepciones.EntidadNoExiste;
import persistencia.Categoria;
import persistencia.Empresa;
import persistencia.Imagen;
import persistencia.Local;
import persistencia.Oferta;

@ManagedBean(name = "modificarOferta", eager = true)
@SessionScoped
public class ModificarOfertaBB {
	
	private String nombre;
	private String descripcion;
	
	private Date fechaComienzo;
	private Date fechaFin;
	
	
	private Imagen imagen;
	private int idImagen;
	private UploadedFile uploadedFile;
	
	private HashMap<String, Categoria> categorias;
	private List<String> nombresCategoria;
	private List<String> nombresCategoriaSelected;
	
	private HashMap<String, Categoria> complementoCats;
	private HashMap<String, Categoria> todasCategorias;
	
	private List<Local> locales;
	private List<Oferta> ofertas;
	
	private float costo;
	private float valoracion;
	private int objectSelected;
	private List<SelectItem> localesSelect;
	private List<SelectItem> ofertasSelect;
	
	private boolean exito;
	
	private int localSelected;
	private int ofertaSelected;
	
	private Oferta oferta;
	
	
	
	
	@EJB
	private GestionEmpresas ge;
	
	@PostConstruct
	public void cargarDatos() {
		System.out.println("ModificarOfertaBB incializado");
		localesSelect = new LinkedList<SelectItem>();
		ofertasSelect = new LinkedList<SelectItem>();
		FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)context.getExternalContext().getSession(true);
    	String mailEmpresa = (String) session.getAttribute("mailEmpresa");
    	Empresa empresa = ge.obtenerEmpresaPorMail(mailEmpresa);    	
    	localSelected = -1;
    	ofertaSelected = -1;
    	try {
			locales = ge.obtenerLocalesDeEmpresa(empresa.getId());
			for (Local local : locales) {
				localesSelect.add(new SelectItem(local.getId(), local.getNombre()));
			}
			
		} catch (EntidadNoExiste e) {
			e.printStackTrace();
		}
	}
	
	public void localChanged(ValueChangeEvent e) {
		this.ofertas = ge.obtenerOfertasDeLocal((Integer)e.getNewValue());
		this.ofertasSelect.clear();
		for (Oferta oferta : this.ofertas) {
			this.ofertasSelect.add(new SelectItem(oferta.getId(), oferta.getNombre()));
		}
	}

	public String seleccionarLocal() {
		this.oferta = ge.obtenerOferta(ofertaSelected);
		this.nombre = oferta.getNombre();
		this.descripcion = oferta.getDescripcion();
		this.costo = oferta.getCosto();
		this.fechaComienzo = oferta.getComienzo().getTime();
		this.fechaFin = oferta.getFin().getTime();
		this.valoracion = oferta.getValoracion();
		
		this.categorias = new HashMap<String, Categoria>();
		this.todasCategorias = new HashMap<String, Categoria>();
		List<Categoria> todas = ge.obtenerCategorias();
		List<Categoria> pertenecen = ge.obtenerCategoriasOferta(ofertaSelected);
		this.complementoCats = new HashMap<String, Categoria>();
		this.nombresCategoria = new LinkedList<String>();
		this.nombresCategoriaSelected = new LinkedList<String>();
		
		if (oferta.getFoto() != null) {
			this.imagen = oferta.getFoto();
			this.idImagen = this.imagen.getId();
		}
		
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
		
		return "modificar";
	}
	
	public String confirmar() {
		oferta.setNombre(nombre);
		oferta.setDescripcion(descripcion);
		Calendar fecha = new GregorianCalendar();
		fecha.setTime(fechaComienzo);
		oferta.setComienzo(fecha);
		oferta.setValoracion(this.valoracion);
		fecha = new GregorianCalendar();
		fecha.setTime(fechaFin);
		oferta.setCosto(costo);
		
		if (imagen != null) {
			oferta.setFoto(imagen);
		}
		
		ge.modificarOferta(oferta);
		
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
		
		ge.borrarCategoriasOferta(oferta.getId(), aBorrar);
		ge.agregarCategoriasOferta(oferta.getId(), aLevantar);

		FacesContext context = FacesContext.getCurrentInstance(); 
        context.getExternalContext().getSessionMap().remove("modificarSitioInteresBB");
        
        
		return "exito";
	}
	
    public void fotoListener(FileUploadEvent event) throws Exception{
    	this.uploadedFile = event.getUploadedFile();

    	this.imagen = new Imagen();
		this.imagen.setImagen(this.uploadedFile.getData());
		this.imagen.setId(idImagen);
		System.out.println("Entro al listener");
		
    }
    
    public void paint(OutputStream stream, Object object) throws IOException {
    	if (this.imagen != null){
    		stream.write(imagen.getImagen());
    	}
    		
    }

	
	public String cancelar() {
    	String retorno = "";
    	
    	//removerBB
    	retorno = "cancelar";   		
    	
        return retorno;
    }
	
	public String modificar() {
		this.oferta = ge.obtenerOferta(ofertaSelected);
		return "modificar";
	}
	

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public List<Local> getLocales() {
		return locales;
	}

	public void setLocales(List<Local> locales) {
		this.locales = locales;
	}

	public float getCosto() {
		return costo;
	}

	public void setCosto(float costo) {
		this.costo = costo;
	}

	public int getObjectSelected() {
		return objectSelected;
	}

	public void setObjectSelected(int objectSelected) {
		this.objectSelected = objectSelected;
	}

	public List<SelectItem> getLocalesSelect() {
		return localesSelect;
	}

	public void setLocalesSelect(List<SelectItem> localesSelect) {
		this.localesSelect = localesSelect;
	}

	public List<SelectItem> getOfertasSelect() {
		return ofertasSelect;
	}

	public void setOfertasSelect(List<SelectItem> ofertasSelect) {
		this.ofertasSelect = ofertasSelect;
	}

	public boolean isExito() {
		return exito;
	}

	public void setExito(boolean exito) {
		this.exito = exito;
	}
	
	public int getLocalSelected() {
		return localSelected;
	}

	public void setLocalSelected(int localSelected) {
		this.localSelected = localSelected;
	}

	public int getOfertaSelected() {
		return ofertaSelected;
	}

	public void setOfertaSelected(int ofertaSelected) {
		this.ofertaSelected = ofertaSelected;
	}

	public List<Oferta> getOfertas() {
		return ofertas;
	}

	public void setOfertas(List<Oferta> ofertas) {
		this.ofertas = ofertas;
	}

	public Oferta getOferta() {
		return oferta;
	}

	public void setOferta(Oferta oferta) {
		this.oferta = oferta;
	}

	public float getValoracion() {
		return valoracion;
	}

	public void setValoracion(float valoracion) {
		this.valoracion = valoracion;
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

	public int getIdImagen() {
		return idImagen;
	}

	public void setIdImagen(int idImagen) {
		this.idImagen = idImagen;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
	
	
	
}
