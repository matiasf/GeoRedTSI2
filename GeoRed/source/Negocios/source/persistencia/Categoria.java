package persistencia;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: Categoria
 *
 */
@Entity
@XmlRootElement
public class Categoria implements Serializable {

	   
	@Id
	@GeneratedValue
	private int id;
	
	@NotNull
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
