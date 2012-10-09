package javaBB;

import javax.faces.bean.ManagedBean;

import persistencia.Empresa;

import negocios.GestionAutenticacion;
import negocios.GestionEmpresas;

@ManagedBean(name = "menu", eager = true)
public class MenuBB {
	
	
    public MenuBB() {
        System.out.println("menuBB instantiated");
    }
    
    /* logica y navegación*/
    
    public String altaEmpresa() {    	
    	return "altaEmpresa";    	
    }
    
    public String modificarEmpresa() {    	
    	return "modificarEmpresa";    	
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

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}