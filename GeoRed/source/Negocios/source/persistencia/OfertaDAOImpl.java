package persistencia;

import javax.ejb.Stateless;

@Stateless
public class OfertaDAOImpl extends BaseDAO<Oferta> implements OfertaDAO {

	@Override
	public Oferta buscarPorId(int id) {
		Oferta ret = em.find(Oferta.class, id);
		return ret;
	}

}
