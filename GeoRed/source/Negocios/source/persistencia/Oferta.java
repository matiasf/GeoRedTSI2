package persistencia;

import java.io.Serializable;
import java.lang.String;
import java.util.Calendar;
import java.util.Collection;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Entity implementation class for Entity: Oferta
 *
 */
@Entity
public class Oferta implements Serializable {

	   
	@Id
	private int id;
	@NotNull
	private String nombre;
	private String descripcion;
	private float costo;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar comienzo;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar fin;
	
	@OneToMany
	private Collection<Pago> pagos;
	
	@ManyToOne
	private Local local;
	
	@OneToOne(cascade={CascadeType.ALL})
	private Imagen foto;
	
	@ManyToMany
	private Collection<Categoria> categorias;
	
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
	public Collection<Pago> getPagos() {
		return pagos;
	}
	public void setPagos(Collection<Pago> pagos) {
		this.pagos = pagos;
	}
	public Local getLocal() {
		return local;
	}
	public void setLocal(Local local) {
		this.local = local;
	}
	public Imagen getFoto() {
		return foto;
	}
	public void setFoto(Imagen foto) {
		this.foto = foto;
	}
	public Collection<Categoria> getCategorias() {
		return categorias;
	}
	public void setCategorias(Collection<Categoria> categorias) {
		this.categorias = categorias;
	}   
}
