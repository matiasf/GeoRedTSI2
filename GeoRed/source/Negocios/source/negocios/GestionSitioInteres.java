package negocios;


import java.util.List;

import dataTypes.SitioInteresDTO;

public interface GestionSitioInteres {
	
	public void agregarSitioInteres(SitioInteresDTO sitioInteres);
	
	public SitioInteresDTO modifciarSitioInteres(SitioInteresDTO sitioInteres);
	
	public SitioInteresDTO obtenerSitioInters(int id);
	
	public void borrarSitioInteres(int id);
	
	public List<SitioInteresDTO> obtenerTodosSitiosInteres();
	

}
