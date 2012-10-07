package negocios;

import java.util.List;

import javax.ejb.Local;

import persistencia.Empresa;

@Local
public interface GestionEmpresas {
	
	public boolean chechLogin(String email, String password);
	
	public void agregarEmpresa(Empresa empresa);
	
	public Empresa modifciarEmpresa(Empresa empresa);
	
	public void agregarLocal(int idEmpresa, Local local);
	
	public Empresa obtenerEmpresa(int id);
	
	public List<Empresa> obtenerTodasLasEmpresas();
	
	public List<Local> obtenerLocalesDeEmpresa(int idEmpresa);
	
	public void borrarEmpresa(int id);
	
	public void borrarLocal(int id);

}
