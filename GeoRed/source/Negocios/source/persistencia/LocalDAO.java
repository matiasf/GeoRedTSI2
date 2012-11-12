package persistencia;

import java.util.List;

@javax.ejb.Local
public interface LocalDAO {
	
	public Local insertar(Local entidad);
	
	public Local modificar(Local entidad);
	
	public Local buscarPorId(int id);
	
	public List<Local> getLocalesPorEmpresa(int idEmpresa);
	
	public boolean existe(int id);
	
	public void borrar(int id);
	
	public void borrar(Local entidad);
	
	public List<Local> obtenerTodos();

}
