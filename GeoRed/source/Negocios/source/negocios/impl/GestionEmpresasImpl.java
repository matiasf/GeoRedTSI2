package negocios.impl;

import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import persistencia.Categoria;
import persistencia.CategoriaDAO;
import persistencia.Empresa;
import persistencia.EmpresaDAO;
import persistencia.Local;
import persistencia.LocalDAO;
import persistencia.Oferta;
import persistencia.OfertaDAO;
import persistencia.SitioInteres;
import negocios.GestionEmpresas;
import negocios.excepciones.EntidadNoExiste;

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

	
	
	
	@Override
	public boolean chechLogin(String email, String password) {
		return empresaDAO.checkLogin(email, password);
	}

	@Override
	public void agregarEmpresa(Empresa empresa) {
		empresaDAO.insertar(empresa);
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


}
