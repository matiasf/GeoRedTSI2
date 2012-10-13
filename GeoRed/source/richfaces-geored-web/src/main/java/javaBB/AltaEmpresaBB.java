package javaBB;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

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
 
	@EJB
	private GestionEmpresas ge;
	
    public AltaEmpresaBB() {
        System.out.println("altaEmpresaBean instantiated");
        this.exito = true;
    }
    
    /* logica y navegación*/
    
    public String altaEmpresa() {
    	String retorno = "";
    	
    	retorno = "exito";
    	//***** LOGICA    	
    	//chequeo de admin sistema
    	Empresa empresa = new Empresa();
    	empresa.setNombre(this.nombre);
    	empresa.setPassword("1111");
    	empresa.setMailAdmin(this.mail);
    	ge.agregarEmpresa(empresa);    	
    	retorno = "exito";   	
    	//LOGICA *******/
    	this.setExito(true);    		
    	
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
}