package javaBB;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "menu", eager = true)
@SessionScoped
public class StatusBB {

	private String errorMsg;
	private String exitoMsg;
	
	private boolean exito;
	private boolean error;
	

	public void inicia() {
		System.out.println("Se incializo el status");
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getExitoMsg() {
		return exitoMsg;
	}
	public void setExitoMsg(String exitoMsg) {
		this.exitoMsg = exitoMsg;
	}
	public boolean isExito() {
		return exito;
	}
	public void setExito(boolean exito) {
		this.exito = exito;
	}
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
}
