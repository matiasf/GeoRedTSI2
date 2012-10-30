package persistencia;

import javax.ejb.Local;

@Local
public interface ImagenDAO {
	
public Imagen insertar(Imagen entidad);
	
	public Imagen modificar(Imagen entidad);
	
	public Imagen buscarPorId(int id);

}
