package persistencia;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 * Entity implementation class for Entity: Sitio de Interes
 *
 */
@Entity
public class SitioInteres implements Serializable, Notificacion {

	   
	@Id
	@GeneratedValue
	private int id;
	
	@NotNull
	private String Nombre;
	private String Descripcion;
	private static final long serialVersionUID = 1L;
	
	@OneToMany
	private Collection<Categoria> categorias;
	
	@OneToMany(mappedBy="sitioInteres")
	private Collection<CheckIn> checkIns;

	public SitioInteres() {
		super();
	}   
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public String getNombre() {
		return this.Nombre;
	}

	public void setNombre(String Nombre) {
		this.Nombre = Nombre;
	}   
	public String getDescripcion() {
		return this.Descripcion;
	}

	public void setDescripcion(String Descripcion) {
		this.Descripcion = Descripcion;
	}
	public Collection<CheckIn> getCheckIns() {
		return checkIns;
	}
	public void setCheckIns(Collection<CheckIn> checkIns) {
		this.checkIns = checkIns;
	}
	public Collection<Categoria> getCategorias() {
		return categorias;
	}
	public void setCategorias(Collection<Categoria> categorias) {
		this.categorias = categorias;
	}
   
}
