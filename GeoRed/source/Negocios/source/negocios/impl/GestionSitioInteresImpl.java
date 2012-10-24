package negocios.impl;

import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import persistencia.Categoria;
import persistencia.CategoriaDAO;
import persistencia.CheckIn;
import persistencia.CheckInDAO;
import persistencia.SitioInteres;
import persistencia.SitioInteresDAO;
import persistencia.Usuario;
import persistencia.UsuarioDAO;
import negocios.GestionSitioInteres;
import negocios.excepciones.EntidadNoExiste;

@Stateless
public class GestionSitioInteresImpl implements GestionSitioInteres {

	@EJB
	private SitioInteresDAO sitioInteresDAO;
	
	@EJB
	private UsuarioDAO usuarioDAO;
	
	@EJB 
	private CheckInDAO checkInDAO;
	
	@EJB
	private CategoriaDAO categoriaDAO;
	
	@Override
	public void agregarSitioInteres(SitioInteres sitioInteres) {
		sitioInteresDAO.insertar(sitioInteres);
	}

	@Override
	public SitioInteres modifciarSitioInteres(SitioInteres sitioInteres) {
		return sitioInteresDAO.modificar(sitioInteres);
	}

	@Override
	public SitioInteres obtenerSitioInteres(int id) {
		return sitioInteresDAO.buscarPorId(id);
	}

	@Override
	public void borrarSitioInteres(int id) throws EntidadNoExiste {
		if (!sitioInteresDAO.existe(id)) {
			String msg = "No existe el sitio de interes con id " + id; 
			throw new EntidadNoExiste(id, msg);
		}
		sitioInteresDAO.borrar(id);
	}

	@Override
	public List<SitioInteres> obtenerTodosSitiosInteres() {
		return sitioInteresDAO.obtenerTodos();
	}

	@Override
	public void hacerCheckIn(int idUsuario, int idSitioInteres, CheckIn checkIn) throws EntidadNoExiste {
		Usuario usuario = usuarioDAO.buscarPorId(idUsuario);
		if (usuario == null) {
			String msg = "No existe el usuario con id " + idUsuario;
			throw new EntidadNoExiste(idUsuario, msg);
		}
		SitioInteres sitio = sitioInteresDAO.buscarPorId(idSitioInteres);
		if (sitio == null) {
			String msg = "No existe el sitio de interes con id " + idSitioInteres;
			throw new EntidadNoExiste(idSitioInteres, msg);
		}
		checkIn.setSitioInteres(sitio);
		checkIn.setUsuario(usuario);
		CheckIn checkInInsertado = checkInDAO.insertar(checkIn);
		usuario.getCheckIns().add(checkInInsertado);
		sitio.getCheckIns().add(checkInInsertado);
	}

	@Override
	public void agregarCategoriaSitio(int idSitio, Collection<Integer> idCategorias) {
		SitioInteres sitio = sitioInteresDAO.buscarPorId(idSitio);
		for (Integer idCategoria : idCategorias) {
			Categoria cat = categoriaDAO.buscarPorId(idCategoria);
			if (!sitio.getCategorias().contains(cat)) {
				sitio.getCategorias().add(cat);
			}
		}
		
	}

}
