package javaBB;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;

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
	
	private Object[] logoData;
	
	private Imagen imagen;
	private byte[] imageToShow;
	
	private UploadedFile uploadedFile;
	
	private boolean exito;
	
	@EJB
	private GestionEmpresas ge;
	
    public ModificarEmpresaBB() {    	
    	
        System.out.println("modificarEmpresaBean instantiated");        
        
    }
    
    public void cargarDatos(){
    	FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)context.getExternalContext().getSession(true);
    	String mailEmpresa = (String) session.getAttribute("mailEmpresa");
    	Empresa empresa = ge.obtenerEmpresaPorMail(mailEmpresa);    	
    	this.nombre = empresa.getNombre();
    	this.mail = empresa.getMailAdmin();
    	this.descripcion = empresa.getDescripcion();
    	this.imagen = empresa.getLogo();
    	if (this.imagen != null){
    		this.imageToShow = this.imagen.getImagen();	
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
    		empresa.setNombre(this.nombre);
        	empresa.setMailAdmin(this.mail);
        	empresa.setDescripcion(this.descripcion);
        	empresa.setLogo(this.imagen);
        	
    		/*Imagen imagen = new Imagen();
    		imagen.setImagen(this.uploadedFile.getData());
    		empresa.setLogo(imagen);*/
    		
			ge.modifciarEmpresa(empresa);
			session.setAttribute("nombreEmpresa", empresa.getNombre());    		
    		session.setAttribute("mailEmpresa", empresa.getMailAdmin());
    		session.setAttribute("descripcionEmpresa", empresa.getDescripcion());
    		
     		
    		
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
		
        System.out.println("finlistener");
    }
    
    
    public void paint(OutputStream stream, Object object) throws IOException {
    	if (this.imagen != null){
    		stream.write(this.imageToShow);
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
		FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)context.getExternalContext().getSession(true);
    	this.nombre = (String) session.getAttribute("nombreEmpresa");
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
		FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)context.getExternalContext().getSession(true);
    	this.descripcion = (String) session.getAttribute("descripcionEmpresa");
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

	public String getPassword() {
		FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)context.getExternalContext().getSession(true);
    	this.password = (String) session.getAttribute("passEmpresa");
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public byte[] getImageToShow() {
		return imageToShow;
	}

	public void setImageToShow(byte[] imageToShow) {
		this.imageToShow = imageToShow;
	}
}