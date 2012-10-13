package javaBB;

import java.util.LinkedList;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import persistencia.Empresa;

import negocios.GestionAutenticacion;
import negocios.GestionEmpresas;
import negocios.GestionSitioInteres;

@ManagedBean(name = "modificarEmpresa", eager = true)
@SessionScoped
public class ModificarEmpresaBB extends LoginBB{
	
	private String mail;
	private String nombre;
	private boolean exito;
	
	private String descripcion;
	private Object[] logoData;
	
	@EJB
	private GestionEmpresas ge;
	
    public ModificarEmpresaBB() {    	
        System.out.println("modificarEmpresaBean instantiated");
        /*this.mail = this.getEmpresaSession().getMailAdmin();
        this.nombre = this.getEmpresaSession().getNombre();
        this.descripcion = this.getEmpresaSession().getDescripcion();*/
        
        this.exito = true;
    }
    
    /* logica y navegación*/
    
    public String modificarEmpresa() {
    	String retorno = "";
    	
    	Empresa empresa = new Empresa();
    	empresa.setNombre(this.nombre);
    	empresa.setMailAdmin(this.mail);
    	empresa.setDescripcion(this.descripcion);    	
    	ge.modifciarEmpresa(empresa);    	
    	retorno = "exito";   	
    	    		
    	
        return retorno;
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
		this.mail = this.getEmpresaSession().getMailAdmin();
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getNombre() {
		this.nombre = this.getEmpresaSession().getNombre();
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
		this.descripcion = this.getEmpresaSession().getDescripcion();
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