package persistencia;

import javax.ejb.Stateless;

@Stateless
public class PagoDAOImpl extends BaseDAO<Pago> implements PagoDAO {

	@Override
	public Pago buscarPorId(int id) {
		Pago ret = em.find(Pago.class, id);
		return ret;
	}

}
