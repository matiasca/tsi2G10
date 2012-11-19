package daos;

import javax.ejb.Local;

import org.codehaus.jettison.json.JSONArray;

import pojos.Notificacion;

@Local
public interface NotificacionDAO {

	public void insertar(Notificacion n);
	
	public boolean exiteNotificacionSitioInteres(String mail,int latitud,int longitud);
	
	public boolean exiteNotificacionCheckIn(String mail,int latitud,int longitud);
	
	public boolean exiteNotificacionOferta(String mail,int latitud,int longitud);
	
	public boolean exiteNotificacionEvento(String mail,int latitud,int longitud);
	
	public JSONArray listarNotificaciones(String mail);
	
	public void notificacionesVistas(String mail);
	
	public void actualizarNotificaciones();
	
}
