package persistencia;

import java.io.Serializable;
import java.lang.String;
import java.util.Collection;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Entity implementation class for Entity: Usuario
 *
 */
@Entity

public class Usuario implements Serializable {

	   
	@Id
	@GeneratedValue
	private int id;
	
	@NotNull
	private String nombre;
	private static final long serialVersionUID = 1L;
	
	@OneToMany
	private Collection<Categoria> categorias;
	
	@OneToMany(mappedBy="usuario")
	private Collection<CheckIn> checkIns;
	

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
   
}