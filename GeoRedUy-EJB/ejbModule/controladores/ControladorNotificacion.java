package controladores;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.codehaus.jettison.json.JSONArray;

import otros.ActualizadorNotificaciones;

import daos.NotificacionDAO;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ControladorNotificacion implements IControladorNotificacion {

	@EJB
	private NotificacionDAO notificacionDAO;
	
	public JSONArray verNotificaciones(String mail){
		
		return notificacionDAO.listarNotificaciones(mail);

	}
	
	public boolean hayNotificacionesSitiosInteres(String mail, int latitud, int longitud){
		
		return notificacionDAO.exiteNotificacionSitioInteres(mail, latitud, longitud);
		
	}
	
	public boolean hayNotificacionesCheckins(String mail, int latitud, int longitud){
		
		return notificacionDAO.exiteNotificacionCheckIn(mail, latitud, longitud);
		
	}
	
	public boolean hayNotificacionesOfertas(String mail, int latitud, int longitud){
		
		return notificacionDAO.exiteNotificacionOferta(mail, latitud, longitud);
		
	}
	
	public boolean hayNotificacionesEventos(String mail, int latitud, int longitud){
		
		return notificacionDAO.exiteNotificacionEvento(mail, latitud, longitud);
		
	}
	
	public void notificacionesVistas(String mail){
		notificacionDAO.notificacionesVistas(mail);
	}
	
	public void actualizarNotificaciones(){
		notificacionDAO.actualizarNotificaciones();
	}
	
	public void crearActualizador(){
		new ActualizadorNotificaciones();
	}

}
