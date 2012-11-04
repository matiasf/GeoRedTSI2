package javaBB;

import java.util.Calendar;
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
import persistencia.Oferta;
import persistencia.SitioInteres;
import persistencia.Usuario;

import negocios.GestionAutenticacion;
import negocios.GestionEmpresas;
import negocios.GestionSitioInteres;
import negocios.GestionUsuarios;
import negocios.excepciones.EntidadNoExiste;

@ManagedBean(name = "reporteOferta")
@SessionScoped
public class ReporteOfertaBB {
	
	private boolean exito;
	
	private List<Oferta> ofertas;
	private List<DatoReporteOferta> datos;
	private int cantDatos;
	private Date fechaComienzo;
	private Date fechaFin;
	
	
	
	@EJB
	private GestionSitioInteres gs;
	
	@EJB
	private GestionEmpresas ge;
	
    public ReporteOfertaBB() {    	
        System.out.println("ReporteOfertaBB instantiated");        
        
        this.exito = true;
    }  
    
    
    /* logica y navegación*/   
    
    
    public void generar() {
    	    	
    	Calendar cal = null;
    	Calendar cal2 = null;
    	
    	if (this.fechaComienzo != null){
    		cal = Calendar.getInstance();
        	cal.setTime(this.fechaComienzo);	
    	}
    	
    	if (this.fechaFin != null){
    		cal2 = Calendar.getInstance();
        	cal2.setTime(this.fechaFin);
    	}
    	
    	
    	this.datos = new LinkedList<DatoReporteOferta>();
    	//FIXME
    	//this.ofertas = ge.ob
    	this.ofertas = new LinkedList<Oferta>();
    	    	    	
    	int k = 1;
    	for(Oferta o : this.ofertas){		
    		DatoReporteOferta dato = new DatoReporteOferta(o.getId(), o.getNombre(), o.getDescripcion(), 
    				6, 7.6);
    		k++;
    		this.datos.add(dato);
    	}	
    	this.cantDatos = this.datos.size();

    	
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
        context.getExternalContext().getSessionMap().remove("reporteOfertaBB");
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


	public List<Oferta> getSitios() {
		return ofertas;
	}


	public void setSitios(List<Oferta> ofertas) {
		this.ofertas = ofertas;
	}


	public List<DatoReporteOferta> getDatos() {
		generar();
		return datos;
	}


	public void setDatos(List<DatoReporteOferta> datos) {		
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


	public List<Oferta> getOfertas() {
		return ofertas;
	}


	public void setOfertas(List<Oferta> ofertas) {
		this.ofertas = ofertas;
	}
    
    
    
    
    

	
}