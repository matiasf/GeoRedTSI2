package persistencia;

import javax.ejb.Stateless;

@Stateless
public class ImagenDAOImpl extends BaseDAO<Imagen> implements ImagenDAO {

	@Override
	public Imagen buscarPorId(int id) {
		return em.find(Imagen.class, id);
	}

}
