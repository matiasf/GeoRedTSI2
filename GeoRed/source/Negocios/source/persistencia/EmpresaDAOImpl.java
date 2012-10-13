package persistencia;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

@Stateless
public class EmpresaDAOImpl extends BaseDAO<Empresa> implements EmpresaDAO {

	private static final String checkLoginQuery = "SELECT e FROM Empresa e " +
			"WHERE e.mailAdmin = :mailAdmin " +
			"AND e.password = :password "; 
	
	private static final String findAllQuery = "SELECT e FROM Empresa";
	
	@Override
	public Empresa buscarPorId(int id) {
		Empresa ret = em.find(Empresa.class, id);
		return ret;
	}
	
	@Override
	public boolean checkLogin(String mailAdmin, String password) {
		TypedQuery<Empresa> query = em.createQuery(checkLoginQuery, Empresa.class);
		query.setParameter("mailAdmin", mailAdmin);
		query.setParameter("password", password);
		List<Empresa> resultado = query.getResultList();
		boolean ret = false;
		if (!resultado.isEmpty()) {
			ret = resultado.size() == 1;
		}
		return ret;	
	}

	@Override
	public List<Empresa> obtenerTodas() {
		TypedQuery<Empresa> query = em.createQuery(findAllQuery, Empresa.class);
		return query.getResultList();
	}

}
