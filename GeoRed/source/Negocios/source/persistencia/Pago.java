package persistencia;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Pago
 *
 */
@Entity

public class Pago implements Serializable {

	   
	@Id
	@GeneratedValue
	private int id;
	private int evaluacion;
	private String comentario;
	private static final long serialVersionUID = 1L;

	public Pago() {
		super();
	}   
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public int getEvaluacion() {
		return this.evaluacion;
	}

	public void setEvaluacion(int evaluacion) {
		this.evaluacion = evaluacion;
	}   
	public String getComentario() {
		return this.comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
   
}
