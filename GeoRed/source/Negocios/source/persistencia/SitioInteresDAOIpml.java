package persistencia;

public class SitioInteresDAOIpml extends BaseDAO<SitioInteres> implements
		SitioInteresDAO {

	@Override
	public SitioInteres buscarPorId(int id) {
		SitioInteres ret = em.find(SitioInteres.class, id);
		return ret;
	}

}
