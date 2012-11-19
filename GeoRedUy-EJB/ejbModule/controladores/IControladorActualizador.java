package controladores;

import javax.ejb.Remote;

@Remote
public interface IControladorActualizador {

	public void crearActualizador();
	
	public void actualizarNotificaciones();
	
	public void verificarConexion();
}
