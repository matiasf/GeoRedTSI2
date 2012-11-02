package negocios;

import java.util.Calendar;
import java.util.List;

import negocios.excepciones.EntidadNoExiste;

import persistencia.Categoria;
import persistencia.Empresa;
import persistencia.Evento;
import persistencia.Local;
import persistencia.Oferta;

@javax.ejb.Local
public interface GestionEmpresas {
	
	public boolean chechLogin(String email, String password);
	
	public void agregarEmpresa(Empresa empresa);
	
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
	
}
