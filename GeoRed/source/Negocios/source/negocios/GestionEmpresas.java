package negocios;

import java.util.List;

import negocios.excepciones.EntidadNoExiste;
import persistencia.Local;
import persistencia.Oferta;


import persistencia.Empresa;

@javax.ejb.Local
public interface GestionEmpresas {
	
	public boolean chechLogin(String email, String password);
	
	public void agregarEmpresa(Empresa empresa);
	
	public Empresa modifciarEmpresa(Empresa empresa);
	
	public void agregarLocal(int idEmpresa, Local local) throws EntidadNoExiste;
	
	public Empresa obtenerEmpresa(int id);
	
	public List<Empresa> obtenerTodasLasEmpresas();
	
	public List<Local> obtenerLocalesDeEmpresa(int idEmpresa) throws EntidadNoExiste;
	
	public void borrarEmpresa(int id) throws EntidadNoExiste;
	
	public void borrarLocal(int id) throws EntidadNoExiste;
	
	public void agregarOferta(int idLocal, Oferta oferta) throws EntidadNoExiste;
	
	

}
