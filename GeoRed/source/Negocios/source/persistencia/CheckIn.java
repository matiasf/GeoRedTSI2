package persistencia;

import java.io.Serializable;
import java.lang.String;
import java.util.Calendar;

import javax.ejb.EJB;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Entity implementation class for Entity: CheckIn
 *
 */
@Entity

public class CheckIn implements Serializable {
	   
	@Id
	@GeneratedValue
	private int id;
	private String comentario;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar fecha;
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@NotNull
	private SitioInteres sitioInteres;
	
	@ManyToOne
	@NotNull
	private Usuario usuario;
	
	public CheckIn() {
		super();
	}   
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public String getComentario() {
		return this.comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public Calendar getFecha() {
		return fecha;
	}
	public void setFecha(Calendar fecha) {
		this.fecha = fecha;
	}
	public SitioInteres getSitioInteres() {
		return sitioInteres;
	}
	public void setSitioInteres(SitioInteres sitioInteres) {
		this.sitioInteres = sitioInteres;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
   
}
