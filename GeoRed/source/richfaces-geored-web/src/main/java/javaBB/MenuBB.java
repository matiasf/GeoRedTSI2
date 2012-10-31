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
	
	
    public MenuBB() {
        System.out.println("menuBB instantiated");        
        
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse)context.getExternalContext().getResponse();
    	HttpServletRequest request= (HttpServletRequest)context.getExternalContext().getRequest();
        
        this.contextyurl = request.getContextPath().toString();
        
    }
    
    /* logica y navegación*/
    
    public String altaEmpresa() {    	
    	return "/presentacionAdminSistema/altaEmpresa.xhtml";    	
    }
    
    public String modificarEmpresa() {
    	return "/presentacionAdminSistema/modificarEmpresa.xhtml";
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
	
	
    
    
    
    /* setters y getters */

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}