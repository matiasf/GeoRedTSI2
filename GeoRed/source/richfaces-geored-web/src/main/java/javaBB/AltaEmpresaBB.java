package javaBB;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import persistencia.Empresa;

import negocios.GestionAutenticacion;
import negocios.GestionEmpresas;
import negocios.GestionSitioInteres;

@ManagedBean(name = "altaEmpresa", eager = true)
@SessionScoped
public class AltaEmpresaBB {
	
	private String mail;
	private String nombre;
	private boolean exito;
	private boolean error;
	private String msjError;
	private String msj;
 
	@EJB
	private GestionEmpresas ge;
	
    public AltaEmpresaBB() {
        System.out.println("altaEmpresaBean instantiated");
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse)context.getExternalContext().getResponse();
    	HttpServletRequest request= (HttpServletRequest)context.getExternalContext().getRequest();
    	
    	this.exito = request.getAttribute("exito") != null;
    	this.error = request.getAttribute("error") != null;
    	this.msjError = (String)request.getAttribute("msjError");
        
        //this.exito = true;
    }
    
    /* logica y navegación*/
    
    public String altaEmpresa() {
    	String retorno = "";
    	    	
    	Empresa empresa = new Empresa();
    	empresa.setNombre(this.nombre);
    	empresa.setPassword("1111");
    	empresa.setMailAdmin(this.mail);
    	try {
    		ge.agregarEmpresa(empresa);
    		FacesContext context = FacesContext.getCurrentInstance();
    		HttpServletRequest request = ((HttpServletRequest)context.getExternalContext().getRequest());
    		this.exito = true;
    		request.setAttribute("exito", true);
    		
        	context.getExternalContext().getSessionMap().remove("altaEmpresaBB");
        	retorno = "exito";
    	} catch (Exception e){
    		FacesContext context = FacesContext.getCurrentInstance();
    		HttpServletRequest request = ((HttpServletRequest)context.getExternalContext().getRequest());
    		request.setAttribute("error", true);
    		request.setAttribute("msjError", e.getMessage());
    		this.setError(true);
    		this.msjError = e.getMessage();
    		retorno = "revente";    		    		
    	}   	
    	
        return retorno;
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

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getMsjError() {
		return msjError;
	}

	public void setMsjError(String msjError) {
		this.msjError = msjError;
	}

	public String getMsj() {
		this.msj = "HOla";
		return msj;
	}

	public void setMsj(String msj) {
		this.msj = msj;
	}
}