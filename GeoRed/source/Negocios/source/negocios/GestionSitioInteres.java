package negocios;


import java.util.List;

import persistencia.CheckIn;
import persistencia.SitioInteres;

public interface GestionSitioInteres {
	
	public void agregarSitioInteres(SitioInteres sitioInteres);
	
	public SitioInteres modifciarSitioInteres(SitioInteres sitioInteres);
	
	public SitioInteres obtenerSitioInters(int id);
	
	public void borrarSitioInteres(int id);
	
	public List<SitioInteres> obtenerTodosSitiosInteres();
	
	public void hacerCheckIn(int idUsuario, CheckIn checkIn);
	

}
