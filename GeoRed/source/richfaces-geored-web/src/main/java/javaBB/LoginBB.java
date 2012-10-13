package javaBB;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import persistencia.Empresa;

import negocios.GestionAutenticacion;
import negocios.GestionEmpresas;

@ManagedBean(name = "login", eager = true)
@SessionScoped
public class LoginBB {
	
	private String mail;
	private String password;
	
	private Empresa empresaSession;
	
	@EJB
	private GestionEmpresas ge;
	
    public LoginBB() {
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
    		this.empresaSession = ge.obtenerEmpresaPorMail(this.mail);
    		retorno = "loginAdminEmpresa";
    	} 
    	//LOGICA *******/
    		
    	
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
}