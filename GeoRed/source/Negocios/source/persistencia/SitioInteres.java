package persistencia;

import java.io.Serializable;
import java.lang.String;
import java.util.Collection;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: Sitio de Interes
 *
 */
@Entity
@XmlRootElement
public class SitioInteres implements Serializable {

	   
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
