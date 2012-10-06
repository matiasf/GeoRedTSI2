package persistencia;

import java.util.List;

public interface CategoriaDAO {

	public Categoria insertar(Categoria entidad);
	
	public void modificar(Categoria entidad);
	
	public Categoria buscarPorId(int id);
	
	public List<Categoria> obtenerTodos();
}
