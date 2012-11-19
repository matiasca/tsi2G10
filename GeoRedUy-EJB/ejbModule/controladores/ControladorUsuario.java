package controladores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import otros.ActualizadorNotificaciones;
import otros.Categoria;
import otros.EnviarMail;
import otros.Imagen;
import otros.TipoNotificacion;

import pojos.SolicitudContacto;
import pojos.Usuario;

import daos.CategoriaDAO;
import daos.ImagenDAO;
import daos.TipoNotificacionDAO;
import daos.SolicitudContactoDAO;
import daos.UsuarioDAO;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ControladorUsuario implements IControladorUsuario {

	@EJB
	private UsuarioDAO usuarioDAO;
	@EJB
	private CategoriaDAO categoriaDAO;
	@EJB
	private TipoNotificacionDAO notificacionDAO;
	@EJB
	private SolicitudContactoDAO solicitudDAO;
	@EJB
	private ImagenDAO imagenDAO;

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public int loguearUsuario(String mail, String token, String tokenCloud) {
		
		new ActualizadorNotificaciones();
		
		Usuario u = usuarioDAO.obtenerUsuario(mail);
		if (u == null) {
			u = new Usuario(mail, token, tokenCloud, false);
			usuarioDAO.insertar(u);
			return 2; // el usuario es nuevo

		} else {
			usuarioDAO.updateToken(mail, token, tokenCloud);

			if (!u.isRegistrado())
				return 2;
			else
				return 1; // usuario logueado correctamente
		}

	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public boolean usuarioValido(String mail, String token) {

		Usuario u = usuarioDAO.obtenerUsuario(mail);
		if (u != null) {
			if (u.getToken().equals(token))
				return true;
			else
				return false;
		} else
			return false;

	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public String obtenerTokenCloud(String mail,String token){
		Usuario u = usuarioDAO.obtenerUsuario(mail);
		if (u!=null)
			return u.getTokenCloud();
		else
			return null;
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public String obtenerNombre(String mail,String token){
		Usuario u = usuarioDAO.obtenerUsuario(mail);
		return u.getNombre();
		
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Collection<String> obtenerNotificacionesDeseadas(String mail){
		return usuarioDAO.obtenerNotificacionesDeseadas(mail);
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public boolean registrarUsuario(String mail, String token, String nombre,
			String sexo, byte[] foto, Collection<String> tiposCategoria,
			Collection<String> tiposNotificacion) {

		Collection<Categoria> categorias = new ArrayList<Categoria>();

		for (Iterator<String> it = tiposCategoria.iterator(); it.hasNext();) {
			String nombreCat = (String) it.next();
			Categoria cat = categoriaDAO.obtenerCategoria(nombreCat);
			categorias.add(cat);
		}

		Collection<TipoNotificacion> notificaciones = new ArrayList<TipoNotificacion>();

		for (Iterator<String> it = tiposNotificacion.iterator(); it.hasNext();) {
			String nombreNot = (String) it.next();
			TipoNotificacion not = notificacionDAO.obtenerNotificacion(nombreNot);
			notificaciones.add(not);
		}
		
		Imagen im = new Imagen(foto,"");
		imagenDAO.insertar(im);
		return usuarioDAO.updateUsuario(mail, token, nombre, sexo, im, categorias,
				notificaciones);
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public JSONArray listarUsuarios(String mail, String cadena) {

			return usuarioDAO.obtenerUsuarios(mail,cadena);
	}

	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public JSONArray listarContactos(String mail, String token) {

		return usuarioDAO.obtenerContactos(mail);
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public JSONArray listarContactosConectados(String mail){
		return usuarioDAO.obtenerContactosConectados(mail);
	}

	public boolean aceptarSolicitud(String mail, String token,
			String mail_contacto) {

		return usuarioDAO.nuevoContacto(mail, mail_contacto);

	}

	public boolean nuevaSolicitud(String mail, String token,
			String mail_contacto) {

		Usuario u = usuarioDAO.obtenerUsuario(mail);
		Usuario contacto = usuarioDAO.obtenerUsuario(mail_contacto);
		if (contacto != null) {
			SolicitudContacto s = new SolicitudContacto(u,contacto);
			solicitudDAO.insertar(s);
			usuarioDAO.nuevaSolicitudEnviada(u, s);
			usuarioDAO.nuevaSolicitudRecibida(contacto, s);
			return true;
		}

		return false;

	}
	
	public boolean rechazarSolicitud(String rechazante, String emisor){
		
		Usuario uRechazante = usuarioDAO.obtenerUsuario(rechazante);
		Usuario uEmisor = usuarioDAO.obtenerUsuario(emisor);
		if (uEmisor != null) {
			SolicitudContacto s = usuarioDAO.obtenerSolicitud(emisor,rechazante);
			if (s!=null){
				usuarioDAO.eliminarSolicitudEnviada(uEmisor,s);
				usuarioDAO.eliminarSolicitudRecibida(uRechazante, s);
				usuarioDAO.eliminarSolicitud(s);
				return true;
			}else
				return false;
		}
		return false;
	}
	
	public JSONObject verPerfil(String mail,String token, String mail_contacto){
		Usuario contacto = usuarioDAO.obtenerUsuario(mail_contacto);
		if (contacto != null)
			return usuarioDAO.obtenerPerfil(contacto);
		else
			return null;
	}
	

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public JSONArray listarMisSolicitudes(String mail) {

			return usuarioDAO.obtenerSolicitudes(mail);
	}
	
	public void solicitudesVistas(String receptor){
		
		Usuario usuario = usuarioDAO.obtenerUsuario(receptor);
		usuarioDAO.solicitudesVistas(usuario);
	}
	
	public void usuarioConectado(String mail){
	
			usuarioDAO.setearConectado(mail);
	}
	
	public void usuarioDesconectado(String mail){
		usuarioDAO.setearDesconectado(mail);
	}
	
	public void llegoRespuestaConexion(String mail){
		usuarioDAO.llegoRespuestaConexion(mail);
	}
	
	public void verificarConexion(){
		usuarioDAO.verificarConexion();
	}
	
	public void enviarInvitacion(String mailEmisor, String mailInvitado){
		EnviarMail e = new EnviarMail();
		Usuario u = usuarioDAO.obtenerUsuario(mailEmisor);
		e.enviarMailInvitacion(mailInvitado, u.getNombre());
	}
}
