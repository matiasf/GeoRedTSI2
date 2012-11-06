package javaBB;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import persistencia.Empresa;

import negocios.GestionAutenticacion;
import negocios.GestionEmpresas;

@ManagedBean(name = "login", eager = true)
@SessionScoped
public class LoginBB {
	
	@Size(min=1, message="El campo mail es obligatorio")
	private String mail;
	@Size(min=1, message="Debe ingresar su password")
	private String password;
	
	private Empresa empresaSession;
	
	private boolean error;
	private String msgError;
	
	@EJB
	private GestionEmpresas ge;
	
    public LoginBB() {
    	error = false;
        System.out.println("WelcomeBean instantiated");
    }
    
    /* logica y navegación*/
    public String login() {
    	String retorno = "";
    	
    	boolean logAdm = this.mail.equals("admin");    	
    	if(logAdm){
    		retorno = "loginAdminSistema";
    	} 
    	//chequeo de admin empresa
    	/*
    	boolean logEmp = this.mail.equals("empresa");
    	if(logEmp){
    		retorno = "loginAdminEmpresa";
    	} */
    	//***** LOGICA    	
    	//chequeo de admin sistema
    	/*boolean logAdm = ga.checkAdmin(this.mail, this.password);    	
    	if(logAdm){
    		retorno = "loginAdminSistema";
    	} */
    	//chequeo de admin empresa
    	boolean logEmp = ge.chechLogin(this.mail, this.password);    			
    	if(logEmp){    		
    		FacesContext context = FacesContext.getCurrentInstance();
    	    HttpSession session = (HttpSession)context.getExternalContext().getSession(true);    	
    	    Empresa empresa = ge.obtenerEmpresaPorMail(this.mail);    		
    		session.setAttribute("idEmpresa", empresa.getId());
    		session.setAttribute("nombreEmpresa", empresa.getNombre());    		
    		session.setAttribute("mailEmpresa", empresa.getMailAdmin());
    		session.setAttribute("passEmpresa", empresa.getPassword());
    		session.setAttribute("descripcionEmpresa", empresa.getDescripcion());
    		retorno = "loginAdminEmpresa";    		
    		context.getExternalContext().getSessionMap().remove("altaLocalBB");
    	} 
    	//LOGICA *******/
		error = !(logEmp || logAdm);
		if (error) {
			msgError = "El usuario o password son incorectos";
		}
		else {
			msgError = "";
		}
    	
        return retorno;
    }
    
    
    
    
    /* setters y getters */
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Empresa getEmpresaSession() {
		return empresaSession;
	}

	public void setEmpresaSession(Empresa empresaSession) {
		this.empresaSession = empresaSession;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getMsgError() {
		return msgError;
	}

	public void setMsgError(String msgError) {
		this.msgError = msgError;
	}
	
	
}