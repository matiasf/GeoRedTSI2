package persistencia;

import java.util.List;

import javax.ejb.Local;

@Local
public interface CategoriaDAO {

	public Categoria insertar(Categoria entidad);
	
	public Categoria modificar(Categoria entidad);
	
	public Categoria buscarPorId(int id);
	
	public List<Categoria> obtenerTodos();
	
	public List<Categoria> obtenerCategoriaOferta(int idOferta);

	public List<Categoria> obtenerCategoriasDeSitioInteres(int idServicio);

}
