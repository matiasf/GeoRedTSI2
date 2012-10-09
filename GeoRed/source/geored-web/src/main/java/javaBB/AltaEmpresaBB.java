package javaBB;

import javax.faces.bean.ManagedBean;

import persistencia.Empresa;

import negocios.GestionAutenticacion;
import negocios.GestionEmpresas;

@ManagedBean(name = "altaEmpresa", eager = true)
public class AltaEmpresaBB {
	
	private String mail;
	private String nombre;
	private boolean exito;
 
	
    public AltaEmpresaBB() {
        System.out.println("altaEmpresaBean instantiated");
        this.exito = true;
    }
    
    /* logica y navegación*/
    
    public void altaEmpresa() {
    	String retorno = "";
    	
    	retorno = "exito";
    	/***** LOGICA
    	GestionEmpresas ge = null;
    	//chequeo de admin sistema
    	Empresa empresa = new Empresa();
    	empresa.setNombre(this.nombre);
    	empresa.setMailAdmin(this.mail);
    	ge.agregarEmpresa(empresa);    	
    	retorno = "exito";   	
    	LOGICA *******/
    	this.setExito(true);    		
    	
        //return retorno;
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