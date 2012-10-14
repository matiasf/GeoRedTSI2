package persistencia;

import java.util.List;

@javax.ejb.Local
public interface EmpresaDAO {

	public Empresa insertar(Empresa entidad);
	
	public Empresa modificar(Empresa entidad);
	
	public Empresa buscarPorId(int id);
	
	public boolean checkLogin(String mailAdmin, String password);
	
	public List<Empresa> obtenerTodas();
	
	public boolean existe(int id);
	
	public void borrar(int id);
	
	public void borrar(Empresa empresa);

	public Empresa buscarPorMail(String mail);
}
