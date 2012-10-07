package persistencia;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Entity implementation class for Entity: Empresa
 *
 */
@Entity

public class Empresa implements Serializable {

	
	@Id
	@GeneratedValue
	private int id;
	@NotNull
	private String nombre;
	private String descripcion;
	@NotNull
	private String mailAdmin;
	@NotNull
	private String password;
	private String nombreAdmin;
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
   
}
