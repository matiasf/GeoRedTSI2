package persistencia;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Entity implementation class for Entity: Categoria
 *
 */
@Entity
public class Categoria implements Serializable {

	   
	@Id
	@GeneratedValue
	private int id;
	
	@NotNull
	@Column(unique=true)
	private String nombre;
	private String descripcion;
	private static final long serialVersionUID = 1L;

	public Categoria() {
		super();
	}   
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}   
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
   
}
