package persistencia;

import java.io.Serializable;
import java.lang.String;
import java.util.Calendar;

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
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar fecha;
	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Usuario usuario;

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
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Calendar getFecha() {
		return fecha;
	}
	public void setFecha(Calendar fecha) {
		this.fecha = fecha;
	}
	
   
}
