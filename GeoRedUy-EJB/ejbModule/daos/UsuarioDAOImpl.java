package daos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


import otros.Categoria;
import otros.Imagen;
import otros.TipoNotificacion;

import pojos.CheckIn;
import pojos.Compra;
import pojos.Oferta;
import pojos.SitioInteres;
import pojos.SolicitudContacto;
import pojos.Usuario;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UsuarioDAOImpl implements UsuarioDAO {

	@PersistenceContext(unitName = "GeoRedUy-Persistencia")
	private EntityManager em;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void insertar(Usuario u) {
		em.persist(u);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Usuario obtenerUsuario(String mail) {
		return em.find(Usuario.class, mail);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void agregarCheckInUsuario(CheckIn c, Usuario u) {
		u.agregarCheckIn(c);
		em.merge(u);

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateToken(String mail, String token, String tokenCloud) {
		Usuario u = em.find(Usuario.class, mail);
		if (u != null) {
			u.setToken(token);
			u.setTokenCloud(tokenCloud);
		}
		em.merge(u);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean updateUsuario(String mail, String token, String nombre,
			String sexo, Imagen fotoPerfil, Collection<Categoria> categorias,
			Collection<TipoNotificacion> notificaciones) {

		Usuario u = em.find(Usuario.class, mail);
		if (u != null) {
			if (u.getToken().equals(token)) {
				u.setRegistrado(true);
				u.setNombre(nombre);
				u.setSexo(sexo);
				u.setCategorias(categorias);
				u.setNotificaciones(notificaciones);
				u.setFotoPerfil(fotoPerfil);
				u.setConectado(true);
				em.merge(u);
				return true;
			}
			return false;
		} else
			return false;
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public JSONArray obtenerUsuarios(String mail, String cadena) {

		Query query = em
				.createQuery("Select u FROM Usuario u WHERE u.mail<>:mail AND (u.nombre LIKE :cadena OR u.mail LIKE :cadena)").setMaxResults(50);
		query.setParameter("mail", mail);
		query.setParameter("cadena", "%" + cadena + "%");

		List<Usuario> usuarios = query.getResultList();
		
		Usuario user = em.find(Usuario.class, mail);
		JSONArray jusuarios = new JSONArray();
		
		for (Usuario u : usuarios) {

			JSONObject ob = new JSONObject();
			try {
				ob.put("mail", u.getMail());
				ob.put("nombre", u.getNombre());
				if (u.getFotoPerfil()!=null)
					ob.put("foto", u.getFotoPerfil().aJSON());
				if (user.getContactos().contains(u))
					ob.put("estado", "Amigo");
				
				else if (user.getSolicitudesRecibidas().contains(u)){
					
					ob.put("estado", "Espera aceptacion");

				}else {

					Collection<SolicitudContacto> solicitudesEnviadas = user
							.getSolicitudesEnviadas();
					Iterator<SolicitudContacto> it = solicitudesEnviadas
							.iterator();
					boolean encontre = false;
					while ((it.hasNext()) && (!encontre)) {
						SolicitudContacto solicitud = (SolicitudContacto) it
								.next();
						if (solicitud.getReceptorSolicitud().equals(u)) {
							encontre = true;
						}
					}

					if (encontre)
						ob.put("estado", "Solicitud");
					
					else{
						Collection<SolicitudContacto> solicitudesRecibidas = user.getSolicitudesRecibidas();
						it = solicitudesRecibidas.iterator();
						encontre = false;
						while ((it.hasNext()) && (!encontre)) {
							SolicitudContacto solicitud = (SolicitudContacto) it.next();
							if (solicitud.getEmisorSolicitud().equals(u)) {
								encontre = true;
							}
						}

						if (encontre)
							ob.put("estado", "Espera aceptacion");
						else
							ob.put("estado", "Desconocido");
					}
				}
				jusuarios.put(ob);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return jusuarios;
	}
	

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public JSONArray obtenerContactos(String mail) {

		JSONArray jusuarios = new JSONArray();
		Usuario usuario = em.find(Usuario.class, mail);

		for (Usuario u : usuario.getContactos()) {

			JSONObject ob = new JSONObject();
			try {
				ob.put("mail", u.getMail());
				ob.put("nombre", u.getNombre());
				if (u.getFotoPerfil()!=null)
					ob.put("foto", u.getFotoPerfil().aJSON());
				jusuarios.put(ob);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return jusuarios;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public JSONArray obtenerContactosConectados(String mail){
		
		JSONArray jusuarios = new JSONArray();
		Usuario usuario = em.find(Usuario.class, mail);

		for (Usuario u : usuario.getContactos()) {
			if (u.isConectado()){
				JSONObject ob = new JSONObject();
				try {
					ob.put("mail", u.getMail());
					ob.put("nombre", u.getNombre());
					if (u.getFotoPerfil()!=null)
						ob.put("foto", u.getFotoPerfil().aJSON());
					jusuarios.put(ob);
	
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return jusuarios;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean nuevoContacto(String mail, String mail_contacto) {

		Usuario usuarioEmisorSolicitud = em.find(Usuario.class, mail_contacto);
		Usuario usuarioReceptorSolicitud = em.find(Usuario.class, mail);
		
		if ((usuarioReceptorSolicitud != null)&&(usuarioEmisorSolicitud != null) && (!usuarioEmisorSolicitud.getContactos().contains(usuarioReceptorSolicitud))) {

			Query query = em
					.createQuery("Select s FROM SolicitudContacto s WHERE s.receptorSolicitud.mail = :mail AND s.emisorSolicitud.mail = :mailContacto");
			query.setParameter("mail", mail);
			query.setParameter("mailContacto", mail_contacto);
			
			SolicitudContacto solicitud = (SolicitudContacto) query.getSingleResult();
				
			usuarioEmisorSolicitud.getSolicitudesEnviadas().remove(solicitud);
			usuarioReceptorSolicitud.getSolicitudesRecibidas().remove(solicitud);
			em.remove(solicitud);

			usuarioEmisorSolicitud.agregarContacto(usuarioReceptorSolicitud);
			usuarioReceptorSolicitud.agregarContacto(usuarioEmisorSolicitud);
			em.merge(usuarioEmisorSolicitud);
			em.merge(usuarioReceptorSolicitud);

			return true;
			
		} else
			return false;

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void nuevaSolicitudEnviada(Usuario u, SolicitudContacto s) {
		Collection<SolicitudContacto> solicitudes = u.getSolicitudesEnviadas();
		solicitudes.add(s);
		em.merge(u);

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void nuevaSolicitudRecibida(Usuario recibidor, SolicitudContacto s) {
		recibidor.agregarSolicitudRecibida(s);
		em.merge(recibidor);

	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public SolicitudContacto obtenerSolicitud(String emisor, String receptor){
		
		Query query = em
				.createQuery("Select s FROM SolicitudContacto s WHERE s.receptorSolicitud.mail = :receptor AND s.emisorSolicitud.mail = :emisor");
		query.setParameter("receptor", receptor);
		query.setParameter("emisor", emisor);
		
		SolicitudContacto s = (SolicitudContacto) query.getSingleResult();
		return s;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void eliminarSolicitudRecibida(Usuario receptor, SolicitudContacto s){
		receptor.getSolicitudesRecibidas().remove(s);
		em.merge(receptor);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void eliminarSolicitudEnviada(Usuario emisor, SolicitudContacto s){
		emisor.getSolicitudesEnviadas().remove(s);
		em.merge(emisor);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void eliminarSolicitud(SolicitudContacto s){
		em.remove(s);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public JSONObject obtenerPerfil(Usuario contacto){
		
		return contacto.aJSON();
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public JSONArray obtenerSolicitudes(String mail){
		
		JSONArray jsolicitudes = new JSONArray();
		Usuario usuario = em.find(Usuario.class, mail);

		for (SolicitudContacto s : usuario.getSolicitudesRecibidas()) {

			JSONObject ob = new JSONObject();
			try {
				ob.put("tipo", "SolicitudDeAmistad");
				ob.put("titulo", "Nueva solicitud de amistad");			
				ob.put("descripcion", s.getEmisorSolicitud().getNombre() + " quiere ser tu amigo");
				ob.put("id", s.getEmisorSolicitud().getMail());
				ob.put("nombre", s.getEmisorSolicitud().getNombre());
				if (s.getEmisorSolicitud().getFotoPerfil()!=null)
					ob.put("foto", s.getEmisorSolicitud().getFotoPerfil().aJSON());
				ob.put("vista", s.isVista());
				jsolicitudes.put(ob);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return jsolicitudes;
	}
	

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void solicitudesVistas(Usuario usuario){
		
		for (SolicitudContacto s : usuario.getSolicitudesRecibidas()) {
			s.setVista(true);
			em.merge(s);
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Collection<String> obtenerNotificacionesDeseadas(String mail){
		
		Usuario u = em.find(Usuario.class,mail);
		Collection<TipoNotificacion> notificaciones = u.getNotificaciones();
		Collection<String> listaNotificaciones = new ArrayList<String>();
		for (TipoNotificacion t: notificaciones){
			listaNotificaciones.add(t.getNotificacion());
		}
		return listaNotificaciones;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void insertarCompra(String mail,Compra c){
		Usuario u = em.find(Usuario.class,mail);
		u.agregarCompra(c);
		em.merge(u);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean usuarioComproOferta(String mail, int idOferta){
		
		Usuario u = em.find(Usuario.class,mail);
		Collection<Compra> compras = u.getCompras();
		
		boolean encontre=false;
		Iterator<Compra> it = compras.iterator();
		while(it.hasNext() && !encontre){
			Compra c = (Compra)it.next();
			if (c.getOferta().getId() == idOferta)
				encontre = true;
		}
		
		return encontre;
		
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void setearConectado(String mail){
		Usuario u = em.find(Usuario.class,mail);
		if (u!=null){
			u.setConectado(true);
			em.merge(u);
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void setearDesconectado(String mail){
		Usuario u = em.find(Usuario.class,mail);
		if (u!=null){
			u.setConectado(false);
			em.merge(u);
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void llegoRespuestaConexion(String mail){
		Usuario u = em.find(Usuario.class,mail);
		if (u!=null){
			u.setFechaConexion(new Date());
			u.setConectado(true);
			em.merge(u);
		}
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void verificarConexion(){

		Calendar fechaHoy = Calendar.getInstance();
		fechaHoy.add(Calendar.MINUTE, -3);
		Date haceTresMinutos = fechaHoy.getTime();

		Query query = em.createQuery("Select u FROM Usuario u WHERE u.conectado = TRUE AND u.fechaConexion < :haceTresMinutos");
		query.setParameter("haceTresMinutos", haceTresMinutos);
		List<Usuario> usuarios = query.getResultList();
		
		for (Usuario u: usuarios){
			System.out.println("usuario desconectado: "+u.getNombre());
			u.setConectado(false);
			em.merge(u);
		}
	}
}
