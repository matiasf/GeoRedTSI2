package javaBB;


public class DatoReporteOferta {

	   
	public int codigo;
	public String nombre;	
	public String descripcion;		
	public int cantidadVentas;
	public double valoracion;
	
	
	
	public DatoReporteOferta(int codigo, String nombre, String descripcion,
			int cantidadVentas, double valoracion) {		
		this.codigo = codigo;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.cantidadVentas = cantidadVentas;
		this.valoracion = valoracion;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getCantidadVentas() {
		return cantidadVentas;
	}
	public void setCantidadVentas(int cantidadVentas) {
		this.cantidadVentas = cantidadVentas;
	}
	public double getValoracion() {
		return valoracion;
	}
	public void setValoracion(double valoracion) {
		this.valoracion = valoracion;
	}
	
		
	
	
}
