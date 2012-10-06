package persistencia;

import javax.persistence.EntityManager;

public abstract class BaseDAO<Entidad> {
	
	@javax.persistence.PersistenceContext(unitName = "Negocios")
	protected EntityManager em;

	public Entidad insertar(Entidad entidad) {
		em.persist(entidad);
		return entidad;
	}
	
	public void modificar(Entidad entidad) {
		em.merge(entidad);
	}
	
	public void borrar(Entidad entidad) {
		em.remove(entidad);
	}
	
	public void borrar(int id) {
		Entidad entidad = this.buscarPorId(id);
		this.borrar(entidad);
	}
	
	public abstract Entidad buscarPorId(int id);
}
