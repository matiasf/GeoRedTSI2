package persistencia;

public interface PagoDAO {
	
	public Pago insertar(Pago entidad);
	
	public void modificar(Pago entidad);
	
	public Pago buscarPorId(int id);

}
