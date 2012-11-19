package controladores;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import daos.NotificacionDAO;
import daos.UsuarioDAO;

import otros.ActualizadorNotificaciones;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ControladorActualizador implements IControladorActualizador {
	
	@EJB
	private NotificacionDAO notificacionDAO;
	@EJB
	private UsuarioDAO usuarioDAO;
	
	public void crearActualizador(){
		new ActualizadorNotificaciones();
	}
	
	public void actualizarNotificaciones(){
		notificacionDAO.actualizarNotificaciones();
	}
	
	public void verificarConexion(){
		usuarioDAO.verificarConexion();
	}
}
