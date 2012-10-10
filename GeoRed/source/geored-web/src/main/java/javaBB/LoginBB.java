package javaBB;

import javax.faces.bean.ManagedBean;

import negocios.GestionAutenticacion;

@ManagedBean(name = "login", eager = true)
public class LoginBB {
	
	private String mail;
	private String password;
	
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
    	boolean logEmp = this.mail.equals("empresa");
    	if(logEmp){
    		retorno = "loginAdminEmpresa";
    	} 
    	/***** LOGICA
    	GestionAutenticacion ga = null;
    	//chequeo de admin sistema
    	boolean logAdm = ga.checkAdmin(this.mail, this.password);    	
    	if(logAdm){
    		retorno = "loginAdminSistema";
    	} 
    	//chequeo de admin empresa
    	boolean logEmp = ga.checkAdminEmpresa(this.mail, this.password);
    	if(logEmp){
    		retorno = "loginAdminEmpresa";
    	} 
    	LOGICA *******/
    		
    	
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
}