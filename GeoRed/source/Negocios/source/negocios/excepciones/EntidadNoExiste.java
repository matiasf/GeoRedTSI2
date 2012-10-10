package negocios.excepciones;

public class EntidadNoExiste extends Throwable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3340746322813753734L;

	private int id;
	private String mensaje = "El usuario no existe";
	
	
	public EntidadNoExiste() {
		super("La entidad no existe");
	}
	
	public EntidadNoExiste(int id, String mensaje) {
		super(mensaje);
		this.mensaje = mensaje;
		this.id = id;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
		mensaje = "El usuario de id " + id + "no existe";
	}
}
