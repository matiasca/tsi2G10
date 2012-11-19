package controladores;

import java.util.Collection;

import javax.ejb.Local;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import otros.TipoNotificacion;

import pojos.Usuario;


@Local
public interface IControladorUsuario {

	public int loguearUsuario(String id, String token, String tokenCloud);
	
	public boolean usuarioValido(String mail,String token);
	
	public String obtenerTokenCloud(String mail,String token);
	
	public String obtenerNombre(String mail,String token);
	
	public Collection<String> obtenerNotificacionesDeseadas(String mail);

	public boolean registrarUsuario(String mail,String token, String nombre,
			String sexo,byte[] fotoPerfil, Collection<String> categorias,
			Collection<String> notificaciones);
	
	public JSONArray listarUsuarios (String mail,String cadena);
	
	public boolean aceptarSolicitud(String mail, String token, String mail_contacto);
	
	public boolean nuevaSolicitud(String mail, String token, String mail_contacto);
	
	public boolean rechazarSolicitud(String rechazante, String emisor);
	
	public JSONArray listarContactos (String mail,String token);
	
	public JSONObject verPerfil(String mail,String token, String mail_contacto);
	
	public JSONArray listarMisSolicitudes(String mail);
	
	public void solicitudesVistas(String receptor);
	
	public void usuarioConectado(String mail);
	
	public void usuarioDesconectado(String mail);
	
	public JSONArray listarContactosConectados(String mail);
	
	public void llegoRespuestaConexion(String mail);
	
	public void verificarConexion();
	
	public void enviarInvitacion(String mailEmisor, String mailInvitado);
	
}
