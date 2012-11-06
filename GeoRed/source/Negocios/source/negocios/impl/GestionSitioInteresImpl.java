package negocios.impl;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import negocios.GestionSitioInteres;
import negocios.excepciones.EntidadNoExiste;
import persistencia.Categoria;
import persistencia.CategoriaDAO;
import persistencia.CheckIn;
import persistencia.CheckInDAO;
import persistencia.Imagen;
import persistencia.ImagenDAO;
import persistencia.SitioInteres;
import persistencia.SitioInteresDAO;
import persistencia.Usuario;
import persistencia.UsuarioDAO;

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
	
	@EJB
	private ImagenDAO imagenDAO;
	
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
	
	/*
	@Override
	public void generarReporte() {		
		try{
      	  InputStream jasperIS = getClass().getResourceAsStream("/META-INF/reportes/holaMundo.jasper");
      	  System.out.println(jasperIS);
      	  Map<String, Object> mapa = new HashMap<String, Object>(); 
      	  	System.setProperty("java.awt.headless", "true");
      		System.out.println(java.awt.GraphicsEnvironment.isHeadless());
      		
      		
            JasperPrint print = JasperFillManager.fillReport(jasperIS,mapa,new JREmptyDataSource());
            JasperViewer jviewer = new JasperViewer(print,false);
            jviewer.setVisible(true);
            
        }catch(Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,"Se produjo un error al leer el archivo .jasper");
        }    
	}*/

	@Override
	public void hacerCheckIn(int idUsuario, int idSitioInteres, Integer idImagen, CheckIn checkIn) throws EntidadNoExiste {
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
		if (idImagen != null) {
			Imagen imagen = imagenDAO.buscarPorId(idImagen);
			if (imagen == null) {
				String msg = "No existe la imagen con id " + idImagen;
				throw new EntidadNoExiste(idImagen, msg);
			}
			checkIn.setFoto(imagen);
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
	
	@Override
	public void borrarCategoriasSitio(int idSitio, Collection<Integer> idCategorias) {
		SitioInteres sitio = sitioInteresDAO.buscarPorId(idSitio);
		for (Integer idCategoria : idCategorias) {
			Categoria cat = categoriaDAO.buscarPorId(idCategoria);
			if (sitio.getCategorias().contains(cat)) {
				sitio.getCategorias().remove(cat);
			}
		}
		
	}

	@Override
	public long cantCheckInsSitio(int idSitio, Calendar inicio, Calendar fin) {
		long ret = 0;
		if ((inicio == null) && (fin == null)) {
			ret = sitioInteresDAO.cantCheckInSitio(idSitio); 
		}
		else if (fin == null) {
			ret = sitioInteresDAO.cantCheckInSitioDesde(idSitio, inicio);
		}
		else if (inicio == null) {
			ret = sitioInteresDAO.cantCheckInSitioHasta(idSitio, fin);
		}
		else {
			ret = sitioInteresDAO.cantCheckInSitioEntre(idSitio, inicio, fin);
		}
		return ret;
	}

	@Override
	public List<CheckIn> obtenerCheckInsAmigosLocal(int idUsuario, int idSitio) {
		return checkInDAO.getCheckInAmigosLocal(idUsuario, idSitio);
	}

}
