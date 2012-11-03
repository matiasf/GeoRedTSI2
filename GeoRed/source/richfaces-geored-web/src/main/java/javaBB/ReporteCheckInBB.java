package javaBB;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import persistencia.CheckIn;
import persistencia.Empresa;
import persistencia.SitioInteres;
import persistencia.Usuario;

import negocios.GestionAutenticacion;
import negocios.GestionEmpresas;
import negocios.GestionSitioInteres;
import negocios.GestionUsuarios;
import negocios.excepciones.EntidadNoExiste;

@ManagedBean(name = "reporteCheckIn")
@SessionScoped
public class ReporteCheckInBB {
	
	private boolean exito;
	
	List<SitioInteres> sitios;
	List<DatoReporteCheckIn> datos;
	
	
	@EJB
	private GestionSitioInteres gs;
	
	@EJB
	private GestionUsuarios gu;
	
    public ReporteCheckInBB() {    	
        System.out.println("ReporteCheckInBean instantiated");        
        
        this.exito = true;
    }  
    
    
    /* logica y navegación*/   
    
    public void cargar(){
    	Usuario usuario1 = new Usuario();
    	usuario1.setNombre("usuario1");
    	usuario1.setPassword("1111");    	
    	gu.registrarUsuario(usuario1);
    	
    	Usuario usuario2 = new Usuario();
    	usuario2.setNombre("usuario2");
    	usuario2.setPassword("1111");    	
    	gu.registrarUsuario(usuario2);
    	
    	this.sitios = gs.obtenerTodosSitiosInteres();
    	for(SitioInteres s : this.sitios){
    		CheckIn check = new CheckIn();
    		check.setSitioInteres(s);
    		check.setUsuario(usuario1);
    		try {
				gs.hacerCheckIn(usuario1.getId(), s.getId(), check);
				gs.hacerCheckIn(usuario2.getId(), s.getId(), check);
			} catch (EntidadNoExiste e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    		
    	}
    		
    	
    }
    
    public void generar() {
    	//String retorno = "";    	
    	
    	
    	this.datos = new LinkedList<DatoReporteCheckIn>();
    	this.sitios = gs.obtenerTodosSitiosInteres();
    	for(SitioInteres s : this.sitios){		
    		DatoReporteCheckIn dato = new DatoReporteCheckIn(s.getNombre(), s.getDescripcion(),
    				s.getLatitud(), s.getLongitud(), 2);
    		this.datos.add(dato);
    	}	

    	//retorno = "exito";   	    		
    	
    	//FacesContext context = FacesContext.getCurrentInstance(); 
        //context.getExternalContext().getSessionMap().remove("reporteCheckInBB");
    	
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

	public boolean isExito() {
		return exito;
	}


	public void setExito(boolean exito) {
		this.exito = exito;
	}


	public List<SitioInteres> getSitios() {
		return sitios;
	}


	public void setSitios(List<SitioInteres> sitios) {
		this.sitios = sitios;
	}


	public List<DatoReporteCheckIn> getDatos() {
		generar();
		return datos;
	}


	public void setDatos(List<DatoReporteCheckIn> datos) {		
		this.datos = datos;
	}
    
    
    
    
    

	
}