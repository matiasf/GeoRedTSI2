package negocios;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.mail.MessagingException;
import javax.naming.NamingException;

import negocios.excepciones.EntidadNoExiste;

import persistencia.Categoria;
import persistencia.Empresa;
import persistencia.Evento;
import persistencia.Local;
import persistencia.Oferta;

@javax.ejb.Local
public interface GestionEmpresas {
	
	public boolean chechLogin(String email, String password);
	
	public void agregarEmpresa(Empresa empresa) throws MessagingException, IOException, NamingException;
	
	public Empresa modifciarEmpresa(Empresa empresa) throws EntidadNoExiste;
	
	public void agregarLocal(int idEmpresa, Local local) throws EntidadNoExiste;
	
	public Empresa obtenerEmpresa(int id);
	
	public Empresa obtenerEmpresaPorMail(String mail);
	
	public List<Empresa> obtenerTodasLasEmpresas();
	
	public List<Local> obtenerLocalesDeEmpresa(int idEmpresa) throws EntidadNoExiste;
	
	public void borrarEmpresa(int id) throws EntidadNoExiste;
	
	public void borrarLocal(int id) throws EntidadNoExiste;
	
	public void agregarOferta(int idLocal, Oferta oferta) throws EntidadNoExiste;
	
	public void altaCategoria(Categoria categoria);
	
	public List<Categoria> obtenerCategorias();
	
	public int altaEvento(Evento evento);
	
	public List<Evento> obtenerEventos(Calendar desdeFecha);
	
	public void agregarCategoriasOferta(int idOferta, Collection<Integer> idCategorias);
	
	public void borrarCategoriasOferta(int idOferta, Collection<Integer> idCategorias);
	
	public List<Oferta> obenerTodasLasOfertas();
	
	public long obtenerCantPagosDeOferta(int idOferta, Calendar desde, Calendar hasta);

	public List<Oferta> obtenerOfertasDeLocal(int idLocal);
	
	public Oferta obtenerOferta(int idOferta);
	
	public List<Categoria> obtenerCategoriasOferta(int idOferta);
	
	public void modificarOferta(Oferta oferta);
}
