package negocios;

import java.util.List;

import javax.ejb.Local;

import dataTypes.EmpresaDTO;
import dataTypes.LocalDTO;

@Local
public interface GestionEmpresas {
	
	public boolean chechLogin(String email, String password);
	
	public void agregarEmpresa(EmpresaDTO empresa);
	
	public EmpresaDTO modifciarEmpresa(EmpresaDTO empresa);
	
	public void agregarLocal(int idEmpresa, LocalDTO local);
	
	public EmpresaDTO obtenerEmpresa(int id);
	
	public List<EmpresaDTO> obtenerTodasLasEmpresas();
	
	public List<LocalDTO> obtenerLocalesDeEmpresa(int idEmpresa);
	
	public void borrarEmpresa(int id);
	
	public void borrarLocal(int id);

}
