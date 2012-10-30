package persistencia;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Imagen
 *
 */
@Entity

public class Imagen implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public Imagen() {
		super();
	}
	
	@Id
	@GeneratedValue
	private int id;
	
	@Lob
	private byte[] imagen;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	
}
