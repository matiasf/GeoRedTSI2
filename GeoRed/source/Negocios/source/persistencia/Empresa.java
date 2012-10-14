package persistencia;

import java.io.Serializable;
import java.lang.String;
import java.util.Collection;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: Empresa
 *
 */
@Entity
@XmlRootElement
public class Empresa implements Serializable {

	
	@Id
	@GeneratedValue
	private int id;
	@NotNull
	@Column(unique=true)
	private String nombre;
	private String descripcion;
	@NotNull
	private String mailAdmin;
	@NotNull
	private String password;
	private String nombreAdmin;
	
	@OneToMany(cascade={CascadeType.REMOVE})
	private Collection<Local> locales;
	
	private static final long serialVersionUID = 1L;

	public Empresa() {
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
	public String getMailAdmin() {
		return this.mailAdmin;
	}

	public void setMailAdmin(String mailAdmin) {
		this.mailAdmin = mailAdmin;
	}   
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}   
	public String getNombreAdmin() {
		return this.nombreAdmin;
	}

	public void setNombreAdmin(String nombreAdmin) {
		this.nombreAdmin = nombreAdmin;
	}
	public Collection<Local> getLocales() {
		return locales;
	}
	public void setLocales(Collection<Local> locales) {
		this.locales = locales;
	}
   
}
