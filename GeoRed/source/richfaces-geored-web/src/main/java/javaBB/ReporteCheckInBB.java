package javaBB;

import java.util.Date;
import java.util.GregorianCalendar;
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
	
	private List<SitioInteres> sitios;
	private List<DatoReporteCheckIn> datos;
	private int cantDatos;
	private Date fechaComienzo;
	private Date fechaFin;
	
	
	
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
    	int k = 1;
    	for(SitioInteres s : this.sitios){		
    		DatoReporteCheckIn dato = new DatoReporteCheckIn(k, s.getNombre(), s.getDescripcion(),
    				s.getLatitud(), s.getLongitud(), (int) gs.cantCheckInsSitio(s.getId()));
    		k++;
    		this.datos.add(dato);
    	}	
    	this.cantDatos = this.datos.size();

    	//retorno = "exito";   	    		
    	
    	//FacesContext context = FacesContext.getCurrentInstance(); 
        //context.getExternalContext().getSessionMap().remove("reporteCheckInBB");
    	
        //return retorno;
    }
    
    public String continuar() {
    	String retorno = "reporte";    	
    	
    	/*
    	List<Usuario> list = gu.buscarUsuario("usuario2");
    	Usuario user2 = list.get(0);
    	
    	SitioInteres sitio7 = gs.obtenerSitioInteres(7);
    	
    	
    	GregorianCalendar dia1 = new GregorianCalendar(2012, 03, 25);
    	GregorianCalendar dia2 = new GregorianCalendar(2012, 03, 26);
    	GregorianCalendar dia3 = new GregorianCalendar(2012, 03, 27);
    	
    	CheckIn check = new CheckIn();
    	check.setUsuario(user2);
    	check.setSitioInteres(sitio7);
    	check.setFecha(dia1);    	
    	
    	//hoy.add(GregorianCalendar.DATE, -7);
    	CheckIn check2 = new CheckIn();
    	check2.setUsuario(user2);
    	check2.setSitioInteres(sitio7);
    	check2.setFecha(dia2);
    	
    	//hoy.add(GregorianCalendar.DATE, -15);
    	CheckIn check3 = new CheckIn();
    	check3.setUsuario(user2);
    	check3.setSitioInteres(sitio7);
    	check3.setFecha(dia3);
    	
    	try {
			gs.hacerCheckIn(user2.getId(), sitio7.getId(), check);
			gs.hacerCheckIn(user2.getId(), sitio7.getId(), check2);
	    	gs.hacerCheckIn(user2.getId(), sitio7.getId(), check3);
		} catch (EntidadNoExiste e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	*/
    	
        return retorno;
    }
    
    public String finalizar() {
    	String retorno = "exito";
    	FacesContext context = FacesContext.getCurrentInstance(); 
        context.getExternalContext().getSessionMap().remove("reporteCheckInBB");
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


	public int getCantDatos() {
		return cantDatos;
	}


	public void setCantDatos(int cantDatos) {
		this.cantDatos = cantDatos;
	}


	public Date getFechaComienzo() {
		return fechaComienzo;
	}


	public void setFechaComienzo(Date fechaComienzo) {
		this.fechaComienzo = fechaComienzo;
	}


	public Date getFechaFin() {
		return fechaFin;
	}


	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
    
    
    
    
    

	
}