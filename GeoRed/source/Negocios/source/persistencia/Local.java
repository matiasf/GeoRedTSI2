package persistencia;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Entity implementation class for Entity: Local
 *
 */
@Entity

public class Local implements Serializable {

	   
	@Id
	@GeneratedValue
	private int id;
	@NotNull
	private String nombre;
	private String descripcion;
	
	private float latitud;
	private float longitud;
	
	private static final long serialVersionUID = 1L;

	public Local() {
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
	public float getLatitud() {
		return this.latitud;
	}

	public void setLatitud(float latitud) {
		this.latitud = latitud;
	}   
	public float getLongitud() {
		return this.longitud;
	}

	public void setLongitud(float longitud) {
		this.longitud = longitud;
	}
   
}