package persistencia;

@javax.ejb.Local
public interface PagoDAO {
	
	public Pago insertar(Pago entidad);
	
	public Pago modificar(Pago entidad);
	
	public Pago buscarPorId(int id);

	public void flush();

}
