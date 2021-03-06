package persistencia;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 * Entity implementation class for Entity: Local
 *
 */
@Entity
public class Local implements Serializable, Notificacion {

	   
	@Id
	@GeneratedValue
	private int id;
	@NotNull
	@Column(unique=true)
	private String nombre;
	private String descripcion;
	
	private double latitud;
	private double longitud;
	
	
	@OneToMany(mappedBy="local", cascade={CascadeType.REMOVE})
	private Collection<Oferta> ofertas;
	
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
	public double getLatitud() {
		return this.latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}   
	public double getLongitud() {
		return this.longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}
	public Collection<Oferta> getOfertas() {
		return ofertas;
	}
	public void setOfertas(Collection<Oferta> ofertas) {
		this.ofertas = ofertas;
	}
   
}
