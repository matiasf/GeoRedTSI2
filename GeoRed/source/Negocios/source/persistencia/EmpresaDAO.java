package persistencia;

public interface EmpresaDAO {

	public Empresa insertar(Empresa entidad);
	
	public void modificar(Empresa entidad);
	
	public Empresa buscarPorId(int id);
}
