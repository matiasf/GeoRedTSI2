package persistencia;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 * Entity implementation class for Entity: Usuario
 *
 */
@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"nombre", "facebookUser"}))
public class Usuario implements Serializable {

	   
	@Id
	@GeneratedValue
	private int id;
	
	@NotNull
	private String nombre;

	private String password;
	
	private boolean facebookUser;
	
	private static final long serialVersionUID = 1L;
	
	@ManyToMany
	private Collection<Categoria> categorias;
	
	@OneToMany(mappedBy="usuario")
	private Collection<CheckIn> checkIns;
	
	@OneToMany
	private Collection<Invitacion> invitaciones;
	
	@ManyToMany
	private Collection<Usuario> contactos;
	

	public Usuario() {
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
	public Collection<Categoria> getCategorias() {
		return categorias;
	}
	public void setCategorias(Collection<Categoria> categorias) {
		this.categorias = categorias;
	}
	public Collection<CheckIn> getCheckIns() {
		return checkIns;
	}
	public void setCheckIns(Collection<CheckIn> checkIns) {
		this.checkIns = checkIns;
	}
	public Collection<Invitacion> getInvitaciones() {
		return invitaciones;
	}
	public void setInvitaciones(Collection<Invitacion> invitaciones) {
		this.invitaciones = invitaciones;
	}
	public Collection<Usuario> getContactos() {
		return contactos;
	}
	public void setContactos(Collection<Usuario> contactos) {
		this.contactos = contactos;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isFacebookUser() {
		return facebookUser;
	}
	public void setFacebookUser(boolean facebookUser) {
		this.facebookUser = facebookUser;
	}
	
   
}
