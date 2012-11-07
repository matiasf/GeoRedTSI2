package negocios.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.stream.XMLStreamException;

import negocios.GestionUsuarios;
import negocios.excepciones.ContactoYaExiste;
import negocios.excepciones.EntidadNoExiste;
import negocios.impl.eventosExternos.GoogleCalendarFeed;
import persistencia.Categoria;
import persistencia.CategoriaDAO;
import persistencia.CheckIn;
import persistencia.CheckInDAO;
import persistencia.Empresa;
import persistencia.EmpresaDAO;
import persistencia.Evento;
import persistencia.EventoDAO;
import persistencia.Imagen;
import persistencia.ImagenDAO;
import persistencia.Invitacion;
import persistencia.InvitacionDAO;
import persistencia.Local;
import persistencia.Notificacion;
import persistencia.Oferta;
import persistencia.OfertaDAO;
import persistencia.Pago;
import persistencia.PagoDAO;
import persistencia.SitioInteres;
import persistencia.SitioInteresDAO;
import persistencia.Usuario;
import persistencia.UsuarioDAO;

@Stateless
public class GestionUsuariosImpl implements GestionUsuarios {

	@EJB
	private UsuarioDAO usuarioDAO;
	
	@EJB
	private InvitacionDAO invitacionDAO;
	
	@EJB
	private OfertaDAO ofertaDAO;
	
	@EJB
	private PagoDAO pagoDAO;
	
	@EJB
	private SitioInteresDAO sitioInteresDAO;
	
	@EJB
	private CategoriaDAO categoriaDAO;
	
	@EJB
	private ImagenDAO imagenDAO;
	
	@EJB
	private EmpresaDAO empresaDAO;
	
	@EJB
	private CheckInDAO checkInDAO;
	
	@EJB
	private EventoDAO eventoDAO;
	
	@Override
	public int checkLogin(String nombre, String password) {
		return usuarioDAO.checkLogin(nombre, password);
	}

	@Override
	public void registrarUsuario(Usuario usuario) {
		usuarioDAO.insertar(usuario);
	}

	@Override
	public void modificarUsuario(Usuario usuario) throws EntidadNoExiste {
		if (!usuarioDAO.existe(usuario.getId())) {
			String msg = "El usuario " + usuario.getId() + "no existe";
			throw new EntidadNoExiste(usuario.getId(), msg);
		}
		usuarioDAO.modificar(usuario);
	}

	@Override
	public List<Usuario> getContactos(int idUsuario) throws EntidadNoExiste {
		if (!usuarioDAO.existe(idUsuario)) {
			String msg = "El usuario " + idUsuario + "no existe";
			throw new EntidadNoExiste(idUsuario, msg);
		}
		return usuarioDAO.obtenerContactos(idUsuario);
	}

	@Override
	public Usuario getContacto(int idUsuario, int idContacto) throws EntidadNoExiste {
		if (!usuarioDAO.existe(idUsuario)) {
			String msg = "El usuario " + idUsuario + "no existe";
			throw new EntidadNoExiste(idUsuario, msg);
		}
		if (!usuarioDAO.existe(idContacto)) {
			String msg = "El usuario " + idContacto + "no existe";
			throw new EntidadNoExiste(idContacto, msg);
		}
		return usuarioDAO.obtenerContacto(idUsuario, idContacto);
	}

	@Override
	public void invitarContacto(int idUsuario, int idContacto) throws EntidadNoExiste, ContactoYaExiste {
		Usuario remitente = usuarioDAO.buscarPorId(idUsuario);
		if (remitente == null) {
			String msg = "El usuario " + idUsuario + "no existe";
			throw new EntidadNoExiste(idUsuario, msg);
		}
		Usuario contacto = usuarioDAO.buscarPorId(idContacto);
		if (contacto == null) {
			String msg = "El usuario " + idContacto + "no existe";
			throw new EntidadNoExiste(idContacto, msg);
		}
		if (usuarioDAO.obtenerContacto(idUsuario, idContacto) != null) {
			String msg = "El usuario " + contacto.getNombre() + 
					" ya es contacto de " + remitente.getNombre();
			throw new ContactoYaExiste(msg);
		}
		Invitacion invitacion = new Invitacion();
		invitacion.setRemitente(remitente);
		contacto.getInvitaciones().add(invitacion);
		invitacionDAO.insertar(invitacion);
	}

	@Override
	public List<Invitacion> getInvitaciones(int idUsuario) throws EntidadNoExiste {
		if (!usuarioDAO.existe(idUsuario)) {
			String msg = "El usuario " + idUsuario + "no existe";
			throw new EntidadNoExiste(idUsuario, msg);
		}
		
		return invitacionDAO.getInvitacionesPorUsuario(idUsuario);
	}

	@Override
	public void aceptarInvitacion(int idUsuario, int idContacto) throws EntidadNoExiste, ContactoYaExiste {
		Usuario remitente = usuarioDAO.buscarPorId(idContacto);
		Usuario contacto = this.getContacto(idUsuario, idContacto);
		if (contacto != null) {
			String msg = "El usuario " + contacto.getNombre() + 
					" ya es contacto de " + remitente.getNombre();
			throw new ContactoYaExiste(msg);
		}
		contacto = usuarioDAO.buscarPorId(idUsuario);
		Invitacion invitacion = invitacionDAO.getInvitacionPorContactoRmte(idUsuario, idContacto);
		if (invitacion == null) {
			EntidadNoExiste e = new EntidadNoExiste();
			e.setMensaje("No existe una invitacion para el usuario " + contacto.getNombre() + " del usuario " + remitente.getNombre());
			throw e;
		}
		remitente.getContactos().add(contacto);
		contacto.getContactos().add(remitente);
		Collection<Invitacion> invitaciones = contacto.getInvitaciones();
		invitaciones.remove(invitacion);
		usuarioDAO.flush();
		invitacionDAO.borrar(invitacion.getId());
	}


	@Override
	public void comprarOferta(int idUsuario, int idOferta, Pago pago)
			throws EntidadNoExiste {
		Usuario usuario = usuarioDAO.buscarPorId(idUsuario);
		pago.setFecha(Calendar.getInstance());
		if (usuario == null) {
			String msg = "El usuario con id " + idUsuario + " no existe";
			throw new EntidadNoExiste(idUsuario, msg);
		}
		Oferta oferta = ofertaDAO.buscarPorId(idOferta);
		if (oferta == null) {
			String msg = "La oferta con id " + idOferta + " no existe";
			throw new EntidadNoExiste(idOferta, msg);
		}
		Pago pagoIns = pagoDAO.insertar(pago);
		oferta.getPagos().add(pagoIns);
		pagoDAO.flush();
		ofertaDAO.flush();
		float prom = ofertaDAO.promedioValoraciones(idOferta);
		oferta.setValoracion(prom);
	}

	@Override
	public List<Notificacion> getNotificaciones(final int idUsuario, 
			final double latitud, 
			final double longitud, 
			final double distancia) throws EntidadNoExiste {
		
		Usuario usuario = usuarioDAO.buscarPorId(idUsuario);
		if (usuario == null) {
			String msg = "El usuario " + idUsuario + " no existe";
			throw new EntidadNoExiste(idUsuario, msg);
		}
		List<Notificacion> ret = getNotSitio(idUsuario, latitud, longitud, distancia);
		ret.addAll(getNotLocales(idUsuario, latitud, longitud, distancia));
		ret.addAll(getNotiEventosInternos(idUsuario, latitud, longitud, distancia));
		return ret;
	}
	
	private List<Notificacion> getNotiEventosInternos(final int idUsuario, 
			final double latitud, 
			final double longitud, 
			final double distancia) {
		List<Evento> eventos = eventoDAO.obtenerEventosNotificacion(Calendar.getInstance());
		List<Notificacion> ret = new ArrayList<Notificacion>();
		for (Evento evento : eventos) {
			if (this.distanciaEntrePuntos(latitud, longitud, evento.getLatitud(), evento.getLongitud()) <= distancia) {
				ret.add(evento);
			}
		}
		return ret;
	}
	
	private List<Notificacion> getNotSitio(final int idUsuario, 
			final double latitud, 
			final double longitud, 
			final double distancia) {
		List<SitioInteres> sitios = sitioInteresDAO.obtenerParaUsuario(idUsuario);
		List<Notificacion> ret = new ArrayList<Notificacion>();
		Calendar now = Calendar.getInstance();
		for (SitioInteres sitio : sitios) {
			if (this.distanciaEntrePuntos(latitud, longitud, sitio.getLatitud(), sitio.getLongitud()) <= distancia) {
				ret.add(sitio);
				List<CheckIn> checkInAmigos = checkInDAO.getCheckInAmigosLocal(idUsuario, sitio.getId());
				for (CheckIn checkIn : checkInAmigos) {
					ret.add(checkIn);
				}
				if (sitio.getGoogleCalendarId() != null && !sitio.getGoogleCalendarId().isEmpty()) {
					GoogleCalendarFeed gcf = new GoogleCalendarFeed();
					gcf.setCalendarId(sitio.getGoogleCalendarId());
					try {
						List<Evento> eventos = gcf.obtenerEventos();
						for (Evento evento : eventos) {
							if (evento.getInicio().before(now) && evento.getFin().after(now)) {
								evento.setLatitud(sitio.getLatitud());
								evento.setLongitud(sitio.getLongitud());
								ret.add(evento);	
							}
							
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (XMLStreamException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return ret;
	}
	
	private List<Notificacion> getNotLocales(final int idUsuario, 
			final double latitud, 
			final double longitud, 
			final double distancia) {
		List<Empresa> empresas = empresaDAO.obtenerTodas();
		List<Notificacion> ret = new ArrayList<Notificacion>();
		for (Empresa empresa : empresas) {
			for (Local local : empresa.getLocales()) {
				if (this.distanciaEntrePuntos(latitud, longitud, local.getLatitud(), local.getLongitud()) <= distancia) {
					ret.add(local);
				}
			}
		}
		return ret;
	}
	
	private double distanciaEntrePuntos(double lat1, double long1, double lat2, double long2) {
		double earthRadius = 6371;
	    double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(long2-long1);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	               Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
	               Math.sin(dLng/2) * Math.sin(dLng/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    double dist = earthRadius * c;

	    return dist;
	}
	
	@Override
	public List<Usuario> buscarUsuario(String nombre) {
		return usuarioDAO.buscarUsuarios(nombre);
	}

	@Override
	public void agregarCategorias(int idUsuario, Collection<Integer> idCategorias) {
		Usuario usuario = usuarioDAO.buscarPorId(idUsuario);
		for (Integer idCat : idCategorias) {
			Categoria cat = categoriaDAO.buscarPorId(idCat);
			if (usuarioDAO.obtenerCategoria(idUsuario, idCat) == null) {
				usuario.getCategorias().add(cat);
			}
		}
	}
	
	@Override
	public void borrarCategorias(int idUsuario, Collection<Integer> idCategorias) {
		Usuario usuario = usuarioDAO.buscarPorId(idUsuario);
		for (Integer idCat : idCategorias) {
			Categoria cat = categoriaDAO.buscarPorId(idCat);
			if (usuarioDAO.obtenerCategoria(idUsuario, idCat) != null) {
				usuario.getCategorias().remove(cat);
			}
		}
	}

	@Override
	public Imagen obtenerImagen(int id) {
		return imagenDAO.buscarPorId(id);
	}
	
	@Override
	public List<Categoria> obtenerCategorias() {
		return categoriaDAO.obtenerTodos();
	}

	@Override
	public int checkLoginUsuarioFacebook(String nombre) {
		return usuarioDAO.checkLoginUsuarioFacebook(nombre);
	}

	@Override
	public int altaImagen(Imagen imagen) {
		return imagenDAO.insertar(imagen).getId();	
	}

	@Override
	public List<Oferta> obtenerOfertasLocalUsuario(int idLocal, int idUsuario) {
		return ofertaDAO.ofertasRelacionadas(idLocal, idUsuario);
	}

	@Override
	public Usuario obtenerUsario(int idUsuario) {
		return usuarioDAO.buscarPorId(idUsuario);
	}

}
