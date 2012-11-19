package controladores;

import javax.ejb.Local;

import org.codehaus.jettison.json.JSONArray;

@Local
public interface IControladorNotificacion {

	public JSONArray verNotificaciones(String mail);
	
	public boolean hayNotificacionesSitiosInteres(String mail, int latitud, int longitud);
	
	public boolean hayNotificacionesCheckins(String mail, int latitud, int longitud);
	
	public boolean hayNotificacionesOfertas(String mail, int latitud, int longitud);
	
	public boolean hayNotificacionesEventos(String mail, int latitud, int longitud);
	
	public void notificacionesVistas(String mail);
	
	public void actualizarNotificaciones();
	
	public void crearActualizador();

}
