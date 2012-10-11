package negocios;


import java.util.List;

import javax.ejb.Local;

import persistencia.SitioInteres;

@Local
public interface GestionSitioInteres {
	
	public void agregarSitioInteres(SitioInteres sitioInteres);
	
	public SitioInteres modifciarSitioInteres(SitioInteres sitioInteres);
	
	public SitioInteres obtenerSitioInteres(int id);
	
	//public void borrarSitioInteres(int id) throws EntidadNoExiste;
	
	public List<SitioInteres> obtenerTodosSitiosInteres();
	
	//public void hacerCheckIn(int idUsuario, int idSitioInteres, CheckIn checkIn) throws EntidadNoExiste;
	

}
