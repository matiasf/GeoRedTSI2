package javaBB;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

import persistencia.Empresa;
import persistencia.Imagen;

import negocios.GestionAutenticacion;
import negocios.GestionEmpresas;
import negocios.GestionSitioInteres;
import negocios.excepciones.EntidadNoExiste;

@ManagedBean(name = "modificarEmpresa", eager = true)
@SessionScoped
public class ModificarEmpresaBB {
	
	private String mail;
	private String nombre;
	private String descripcion;
	private String password;
	private int id;
	private int idImagen;
		
	private Imagen imagen;
	
	
	private UploadedFile uploadedFile;
	
	private boolean exito;
	
	@EJB
	private GestionEmpresas ge;
	
    public ModificarEmpresaBB() {    	
    	
        System.out.println("modificarEmpresaBean instantiated");        
        
    }
    
    @PostConstruct
    public void cargarDatos(){
    	FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)context.getExternalContext().getSession(true);
    	String mailEmpresa = (String) session.getAttribute("mailEmpresa");
    	Empresa empresa = ge.obtenerEmpresaPorMail(mailEmpresa);    	
    	this.nombre = empresa.getNombre();
    	this.mail = empresa.getMailAdmin();
    	this.descripcion = empresa.getDescripcion();
    	this.imagen = empresa.getLogo();
    	this.id = empresa.getId();
    	if (this.imagen != null){
    		this.idImagen = empresa.getLogo().getId();
    	}	
    }
    
    /* logica y navegación*/
    
    public String modificarEmpresa() {
    	String retorno = "";
    	FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession)context.getExternalContext().getSession(true);
		String mailEmpresa = (String) session.getAttribute("mailEmpresa");
    	Empresa empresa = ge.obtenerEmpresaPorMail(mailEmpresa);    	
    	    	
    	try {    		
    		empresa.setId(id);
    		empresa.setNombre(this.nombre);
        	empresa.setMailAdmin(this.mail);
        	empresa.setDescripcion(this.descripcion);
        	empresa.setLogo(this.imagen);
        	empresa.getLogo().setId(idImagen);
        	    	
			ge.modifciarEmpresa(empresa);
			session.setAttribute("nombreEmpresa", empresa.getNombre());    		
    		session.setAttribute("mailEmpresa", empresa.getMailAdmin());
    		session.setAttribute("descripcionEmpresa", empresa.getDescripcion());
    		context.getExternalContext().getSessionMap().remove("altaEmpresaBB");
    		
    		StatusBB statusBB = (StatusBB) context.getExternalContext().getSessionMap().get("statusBB");
            statusBB.setExito(true);
            statusBB.setError(false);
            statusBB.setExitoMsg("Se ha modifico la empresa " + empresa.getNombre());
    		
    		
    	} catch (EntidadNoExiste e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	
    	retorno = "exito";   	
    	    		
    	
        return retorno;
    }
    
    
    public void logoListener(FileUploadEvent event) throws Exception{
    	this.uploadedFile = event.getUploadedFile();

    	this.imagen = new Imagen();
		this.imagen.setImagen(this.uploadedFile.getData());
		this.imagen.setId(idImagen);
		
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
	public String getMail() {
		FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)context.getExternalContext().getSession(true);
    	this.mail = (String) session.getAttribute("mailEmpresa");
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Imagen getImagen() {
		return imagen;
	}

	public void setImagen(Imagen imagen) {
		this.imagen = imagen;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
}