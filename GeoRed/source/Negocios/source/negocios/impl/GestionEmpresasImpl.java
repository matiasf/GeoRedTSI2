package negocios.impl;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.naming.NamingException;

import persistencia.Categoria;
import persistencia.CategoriaDAO;
import persistencia.Empresa;
import persistencia.EmpresaDAO;
import persistencia.Evento;
import persistencia.EventoDAO;
import persistencia.Local;
import persistencia.LocalDAO;
import persistencia.Oferta;
import persistencia.OfertaDAO;
import negocios.GestionEmpresas;
import negocios.excepciones.EntidadNoExiste;
import negocios.impl.mailSender.MailSender;
import negocios.impl.mailSender.MailSender2;

@Stateless
public class GestionEmpresasImpl implements GestionEmpresas {

	@EJB
	private EmpresaDAO empresaDAO;
	@EJB
	private LocalDAO localDAO;
	@EJB 
	private OfertaDAO ofertaDAO;	
	@EJB 
	private CategoriaDAO categoriaDAO;
	@EJB
	private EventoDAO eventoDAO;
	
	
	@Override
	public boolean chechLogin(String email, String password) {
		return empresaDAO.checkLogin(email, password);
	}

	@Override
	public void agregarEmpresa(Empresa empresa) throws MessagingException {
		empresaDAO.insertar(empresa);
		String asunto = "Bienvenido a GeoredUy";
		String cuerpo = "Bienvenido a GeoredUy. Para acceder a la administración de su empresa";
		MailSender mailSender = new MailSender(empresa.getMailAdmin(), asunto, cuerpo);
		mailSender.send();
	}

	@Override
	public Empresa modifciarEmpresa(Empresa empresa) throws EntidadNoExiste {
		if (!empresaDAO.existe(empresa.getId())) {
			String msg = "La empresa " + empresa.getId() + " no existe";
			throw new EntidadNoExiste(empresa.getId(), msg);
		}
		return empresaDAO.modificar(empresa);
	}

	@Override
	public void agregarLocal(int idEmpresa, Local local) throws EntidadNoExiste {
		Empresa empresa = empresaDAO.buscarPorId(idEmpresa);
		if (empresa == null) {
			String msg = "La empresa " + idEmpresa + " no existe";
			throw new EntidadNoExiste(idEmpresa, msg);
		}
		Local localManaged = localDAO.insertar(local);
		empresa.getLocales().add(localManaged);
	}
	
	@Override
	public void modificarLocal(Local local){		
		localDAO.modificar(local);		
	}

	@Override
	public Empresa obtenerEmpresa(int id) {
		return empresaDAO.buscarPorId(id);
	}
	
	@Override
	public Empresa obtenerEmpresaPorMail(String mail) {
		return empresaDAO.buscarPorMail(mail);
	}

	@Override
	public List<Empresa> obtenerTodasLasEmpresas() {
		return empresaDAO.obtenerTodas();
	}

	@Override
	public List<Local> obtenerLocalesDeEmpresa(int idEmpresa) throws EntidadNoExiste {
		if (!empresaDAO.existe(idEmpresa)) {
			String msg = "No existe la empresa con id " + idEmpresa;
			throw new EntidadNoExiste(idEmpresa, msg);
		}
		return localDAO.getLocalesPorEmpresa(idEmpresa);
	}

	@Override
	public void borrarEmpresa(int id) throws EntidadNoExiste {
		if (!empresaDAO.existe(id)) {
			String msg = "No existe la empresa con id " + id;
			throw new EntidadNoExiste(id, msg);
		}
		empresaDAO.borrar(id);
	}

	@Override
	public void borrarLocal(int id) throws EntidadNoExiste {
		if (!localDAO.existe(id)) {
			String msg = "No existe el local con id " + id;
			throw new EntidadNoExiste(id, msg);
		}

	}


	@Override
	public void agregarOferta(int idLocal, Oferta oferta) throws EntidadNoExiste {
		Local local = localDAO.buscarPorId(idLocal);
		if (local == null) {
			String msg = "No existe el local con id " + idLocal;
			throw new EntidadNoExiste(idLocal, msg);
		}
		Oferta insertada = ofertaDAO.insertar(oferta);
		insertada.setLocal(local);
		local.getOfertas().add(oferta);
	}

	@Override
	public void altaCategoria(Categoria categoria) {
		categoriaDAO.insertar(categoria);
	}

	@Override
	public List<Categoria> obtenerCategorias() {
		return categoriaDAO.obtenerTodos();
	}
	
	@Override
	public List<Categoria> obtenerCategoriasDeSitioInteres(int idServicio) {
		return categoriaDAO.obtenerCategoriasDeSitioInteres(idServicio);
	}

	@Override
	public int altaEvento(Evento evento) {
		return eventoDAO.insertar(evento).getId();
	}
	
	@Override
	public void modificarEvento(Evento evento) {
		eventoDAO.modificar(evento);
	}

	@Override
	public List<Evento> obtenerEventos(Calendar desdeFecha) {
		return eventoDAO.obtenerEventos(desdeFecha);
	}
	
	@Override
	public Evento obtenerEvento(int idEvento) {
		return eventoDAO.buscarPorId(idEvento);
	}
	
	@Override
	public Local obtenerLocal(int idLocal) {
		return localDAO.buscarPorId(idLocal);
	}
	
	@Override
	public void agregarCategoriasOferta(int idOferta,
			Collection<Integer> idCategorias) {
		
		Oferta oferta = ofertaDAO.buscarPorId(idOferta);
		for (Integer idCategoria : idCategorias) {
			Categoria cat = categoriaDAO.buscarPorId(idCategoria);
			if (!oferta.getCategorias().contains(cat)) {
				oferta.getCategorias().add(cat);
			}
		}
		
	}

	@Override
	public List<Oferta> obenerTodasLasOfertas() {
		return ofertaDAO.obtenerTodas();
	}

	@Override
	public long obtenerCantPagosDeOferta(int idOferta, Calendar desde,
			Calendar hasta) {
		long ret = 0;
		if ((desde == null) && (hasta == null)) {
			ret = ofertaDAO.obtenerCantPagos(idOferta);
		}
		else if (desde == null) {
			ret = ofertaDAO.obtenerCantPagosHasta(idOferta, hasta);
		}
		else if (hasta == null) {
			ret = ofertaDAO.obtenerCantPagosDesde(idOferta, desde);
		}
		else {
			ret = ofertaDAO.obtenerCantPagosEntre(idOferta, desde, hasta);
		}
		return ret;
	}

	@Override
	public List<Oferta> obtenerOfertasDeLocal(int idLocal) {
		return ofertaDAO.obtenerOfertasLocal(idLocal);
	}

	@Override
	public Oferta obtenerOferta(int idOferta) {
		return ofertaDAO.buscarPorId(idOferta);
	}

	@Override
	public List<Categoria> obtenerCategoriasOferta(int idOferta) {
		return categoriaDAO.obtenerCategoriaOferta(idOferta);
	}

	@Override
	public void modificarOferta(Oferta oferta) {
		ofertaDAO.modificar(oferta);
	}

	@Override
	public void borrarCategoriasOferta(int idOferta,
			Collection<Integer> idCategorias) {
		Oferta oferta = ofertaDAO.buscarPorId(idOferta);
		if (oferta != null) {
			for (Integer id : idCategorias) {
				Categoria categoria  = categoriaDAO.buscarPorId(id);
				if ((categoria != null && (oferta.getCategorias().contains(categoria)))) {
					oferta.getCategorias().remove(categoria);
				}
			}
		}
		
	}

}
