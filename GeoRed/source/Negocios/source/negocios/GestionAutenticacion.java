package negocios;

public interface GestionAutenticacion {
	
	public boolean checkAdmin(String usuario, String constrasenia);
	
	public boolean checkAdminEmpresa(String usuario, String contrasenia);

}
