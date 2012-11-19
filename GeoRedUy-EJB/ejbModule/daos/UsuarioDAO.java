package daos;

import java.util.Collection;

import javax.ejb.Local;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import otros.Categoria;
import otros.Imagen;
import otros.TipoNotificacion;

import pojos.CheckIn;
import pojos.Compra;
import pojos.SolicitudContacto;
import pojos.Usuario;



@Local
public interface UsuarioDAO {

	public void insertar(Usuario u);
	
	public Usuario obtenerUsuario(String mail);
	
	public boolean updateUsuario(String mail,String token,String nombre,String sexo, Imagen fotoPerfil,
			Collection<Categoria> categorias,Collection<TipoNotificacion> notificaciones);

	public void updateToken(String mail, String token, String tokenCloud);
	
	public void agregarCheckInUsuario(CheckIn c, Usuario u);
	
	public JSONArray obtenerUsuarios(String mail, String cadena);
	
	public JSONArray obtenerContactos(String mail);
	
	public JSONArray obtenerContactosConectados(String mail);
	
	public JSONArray obtenerSolicitudes(String mail);
	
	public SolicitudContacto obtenerSolicitud(String emisor, String receptor);
	
	public void eliminarSolicitudEnviada(Usuario emisor, SolicitudContacto s);
	
	public void eliminarSolicitudRecibida(Usuario receptor, SolicitudContacto s);
	
	public void eliminarSolicitud(SolicitudContacto s);
	
	public boolean nuevoContacto(String mail, String mail_contacto);
	
	public void nuevaSolicitudEnviada(Usuario u, SolicitudContacto s);
	
	public void nuevaSolicitudRecibida(Usuario u, SolicitudContacto s);

	public JSONObject obtenerPerfil(Usuario contacto);
	
	public Collection<String> obtenerNotificacionesDeseadas(String mail);
	
	public void solicitudesVistas(Usuario usuario);
	
	public void insertarCompra(String mail, Compra c);
	
	public boolean usuarioComproOferta(String mail, int idOferta);
	
	public void setearConectado(String mail);
	
	public void setearDesconectado(String mail);
	
	public void llegoRespuestaConexion(String mail);
	
	public void verificarConexion();
}
