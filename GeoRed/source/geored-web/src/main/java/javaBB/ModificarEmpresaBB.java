package javaBB;

import javax.faces.bean.ManagedBean;

import persistencia.Empresa;

import negocios.GestionAutenticacion;
import negocios.GestionEmpresas;

@ManagedBean(name = "modificarEmpresa", eager = true)
public class ModificarEmpresaBB {
	
	private String mail;
	private String nombre;
	private boolean exito;
	
	private String descripcion;
	private Object[] logoData;
	
	
    public ModificarEmpresaBB() {    	
        System.out.println("modificarEmpresaBean instantiated");
        /***** LOGICA
    	GestionEmpresas ge = null;
    	//chequeo de admin sistema    	
    	Empresa empresa = ge.obtenerEmpresa(id);//??
    	this.nombre = empresa.getNombre();
    	this.mail = empresa.getMailAdmin();
    	this.descripcion = empresa.getDescripcion();   	    	
    	LOGICA *******/
        this.exito = true;
    }
    
    /* logica y navegación*/
    
    public void modificarEmpresa() {
    	String retorno = "";
    	
    	retorno = "exito";
    	/***** LOGICA
    	GestionEmpresas ge = null;
    	//chequeo de admin sistema
    	Empresa empresa = new Empresa();
    	empresa.setNombre(this.nombre);
    	empresa.setMailAdmin(this.mail);
    	empresa.setDescripcion(this.descripcion);    	
    	ge.modifciarEmpresa(empresa);    	
    	retorno = "exito";   	
    	LOGICA *******/
    	this.setExito(true);    		
    	
        //return retorno;
    }
    
    public void logoListener() {
    	
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

	public String getDescripcion() {
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
}