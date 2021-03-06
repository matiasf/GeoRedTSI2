package persistencia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
	@Column(unique=true)
	private String Nombre;
	private String Descripcion;
	private static final long serialVersionUID = 1L;
	private double latitud;
	private double longitud;
	private String googleCalendarId;

	
	@ManyToMany
	private Collection<Categoria> categorias;
	
	@OneToMany(mappedBy="sitioInteres")
	private Collection<CheckIn> checkIns;

	@OneToMany(cascade={CascadeType.ALL})
	private Collection<Imagen> fotos;
	
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
	public double getLatitud() {
		return latitud;
	}
	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}
	public double getLongitud() {
		return longitud;
	}
	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}
	public Collection<Imagen> getFotos() {
		return fotos;
	}
	public void setFotos(Collection<Imagen> fotos) {
		this.fotos = fotos;
	}
	public String getGoogleCalendarId() {
		return googleCalendarId;
	}
	public void setGoogleCalendarId(String googleCalendarId) {
		this.googleCalendarId = googleCalendarId;
	}
	
	public List<Notificacion> getCheckInAmigos(Usuario usuario) {
		List<Notificacion> ret = new ArrayList<Notificacion>();
		for (CheckIn checkIn : checkIns) {
			if (checkIn.getUsuario().getContactos().contains(usuario)) {
				ret.add(checkIn);
			}
		}
		return ret;
	}
}
