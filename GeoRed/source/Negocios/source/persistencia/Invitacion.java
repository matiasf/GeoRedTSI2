package persistencia;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Invitacion
 *
 */
@Entity

public class Invitacion implements Serializable {

	   
	@Id
	private int id;
	private static final long serialVersionUID = 1L;

	@ManyToOne
	private Usuario usuario;

	public Invitacion() {
		super();
	}   
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
   
}
