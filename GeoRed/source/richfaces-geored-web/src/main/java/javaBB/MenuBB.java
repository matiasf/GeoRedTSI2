package javaBB;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import persistencia.Empresa;

import negocios.GestionAutenticacion;
import negocios.GestionEmpresas;

@ManagedBean(name = "menu", eager = true)
@SessionScoped
public class MenuBB extends LoginBB {
	
	private String context;
	private String contextyurl;
	
	private boolean aExito;
	private boolean bExito;
	private boolean mExito;
	
	private boolean error;
	
	private String msjError;
	
	
	
	
    public MenuBB() {
        System.out.println("menuBB instantiated");        
        
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse)context.getExternalContext().getResponse();
    	HttpServletRequest request= (HttpServletRequest)context.getExternalContext().getRequest();
    	
    	this.aExito = request.getAttribute("aExito") != null;
    	this.bExito = request.getAttribute("bExito") != null;
    	this.mExito = request.getAttribute("mExito") != null;    	
        this.error = request.getAttribute("error") != null;            
        this.msjError = (String) request.getAttribute("msjError");                      
        
        this.contextyurl = request.getContextPath().toString();
        
    }
    
    /* logica y navegación*/
    
    public String altaEmpresa() {
    	FacesContext context = FacesContext.getCurrentInstance();
    	context.getExternalContext().getSessionMap().remove("menuBB");
    	return "/presentacionAdminSistema/altaEmpresa.xhtml";    	
    }
    
    public String modificarEmpresa() {
    	return "/presentacionAdminEmpresa/modificarEmpresa.xhtml";
    	//return "modificarEmpresa";
    	
    }
	
	public String altaSitioInteres(){
		return "/presentacionAdminSistema/altaSitioInteres.xhtml";
		//return "altaSitioInteres";
	}
	
	public String modificarSitioInteres(){
		return "/presentacionAdminSistema/modificarSitioInteres0.xhtml";
		//return "modificarSitioInteres";
	}
	
	public String bajaSitioInteres(){
		return "/presentacionAdminSistema/bajaSitioInteres0.xhtml";
		//return "bajaSitioInteres";
	}
	
	public String altaLocal(){
		return "/presentacionAdminEmpresa/altaLocal.xhtml";
	}
	
	public String modificarLocal(){
		return "/presentacionAdminEmpresa/modificarLocal0.xhtml";
	}
	
	public String bajaLocal(){
		return "/presentacionAdminEmpresa/bajaLocal.xhtml";
	}
	
	public String altaOferta(){
		return "/presentacionAdminEmpresa/altaOferta.xhtml";
	}
	
	public String modificarOferta(){
		return "/presentacionAdminEmpresa/modificarOferta0.xhtml";
	}
	
	public String bajaOferta(){
		return "/presentacionAdminEmpresa/bajaOferta.xhtml";
	}
	
	
    
    public String logout() {
    	String retorno = "";
    	
    	//removerBB
    	retorno = "finalizar";   		
    	
        return retorno;
    }
    
    public String finalizar() {
    	String retorno = "";
    	
    	//removerBB
    	retorno = "finalizar";   		
    	
        return retorno;
    }
    
    public String alMenu() {
    	String retorno = "menu";
        return retorno;
    }
    
    public String cancelar() {
    	String retorno = "";
    	
    	//removerBB
    	retorno = "cancelar";   		
    	
        return retorno;
    }

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getContextyurl() {
		return contextyurl;
	}

	public void setContextyurl(String contextyurl) {
		this.contextyurl = contextyurl;
	}

	
	public String updateCurrent(){
		return "hola";
	}

	public boolean isaExito() {
		return aExito;
	}

	public void setaExito(boolean aExito) {
		this.aExito = aExito;
	}

	public boolean isbExito() {
		return bExito;
	}

	public void setbExito(boolean bExito) {
		this.bExito = bExito;
	}

	public boolean ismExito() {
		return mExito;
	}

	public void setmExito(boolean mExito) {
		this.mExito = mExito;
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
	
	
    
    
    
    /* setters y getters */

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}