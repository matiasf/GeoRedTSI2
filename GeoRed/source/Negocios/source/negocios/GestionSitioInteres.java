package negocios;


import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.ejb.Local;

import negocios.excepciones.EntidadNoExiste;

import persistencia.CheckIn;
import persistencia.SitioInteres;

@Local
public interface GestionSitioInteres {
	
	public void agregarSitioInteres(SitioInteres sitioInteres);
	
	public SitioInteres modifciarSitioInteres(SitioInteres sitioInteres);
	
	public SitioInteres obtenerSitioInteres(int id);
	
	public void borrarSitioInteres(int id) throws EntidadNoExiste;
	
	public List<SitioInteres> obtenerTodosSitiosInteres();
	
	//public void generarReporte();
	
	public void hacerCheckIn(int idUsuario, int idSitioInteres, CheckIn checkIn) throws EntidadNoExiste;
	
	public void agregarCategoriaSitio(int idSitio, Collection<Integer> idCategorias);
	
	public long cantCheckInsSitio(int idSitio, Calendar inicio, Calendar fin);
	
	public List<CheckIn> obtenerCheckInsAmigosLocal(int idUsuario, int idSitio);
}

