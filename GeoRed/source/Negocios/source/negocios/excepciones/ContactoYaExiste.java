package negocios.excepciones;

public class ContactoYaExiste extends Throwable {

	private static final long serialVersionUID = 1775452480771127905L;

	private String mensaje = "El usuario ya tiene al contacto";

	public ContactoYaExiste(String msg) {
		super(msg);
		mensaje = msg;
	}
	
	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	

}
