package persistencia;

import java.io.Serializable;
import java.lang.String;
import java.util.Calendar;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Oferta
 *
 */
@Entity

public class Oferta implements Serializable {

	   
	@Id
	private int id;
	private String nombre;
	private String descripcion;
	private float costo;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar comienzo;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar fin;
	
	
	private static final long serialVersionUID = 1L;

	public Oferta() {
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
	public float getCosto() {
		return this.costo;
	}

	public void setCosto(float costo) {
		this.costo = costo;
	}
	public Calendar getComienzo() {
		return comienzo;
	}
	public void setComienzo(Calendar comienzo) {
		this.comienzo = comienzo;
	}
	public Calendar getFin() {
		return fin;
	}
	public void setFin(Calendar fin) {
		this.fin = fin;
	}
   
}
