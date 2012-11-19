package daos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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

import pojos.Notificacion;
import pojos.NotificacionCheckIn;
import pojos.NotificacionEvento;
import pojos.NotificacionOferta;
import pojos.NotificacionSitioInteres;
import pojos.SitioInteres;
import pojos.Usuario;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class NotificacionDAOImpl implements NotificacionDAO {

	@PersistenceContext(unitName = "GeoRedUy-Persistencia")
	private EntityManager em;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void insertar(Notificacion n) {

		em.persist(n);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean exiteNotificacionSitioInteres(String mail, int latitud,
			int longitud) {

		Usuario u = em.find(Usuario.class, mail);
		Query query;
		if (u.getNotificacionesEnviadasVistas().isEmpty())
			query = em
					.createQuery("Select n FROM Notificacion n WHERE SQRT((n.sitio.latitud - :lat)*(n.sitio.latitud - :lat) + (n.sitio.longitud - :long)*(n.sitio.longitud - :long)) < 6000");

		else {
			query = em
					.createQuery("Select n FROM Notificacion n WHERE SQRT((n.sitio.latitud - :lat)*(n.sitio.latitud - :lat) + (n.sitio.longitud - :long)*(n.sitio.longitud - :long)) < 6000 AND n NOT IN :notificacionesEnviadasVistas");
			query.setParameter("notificacionesEnviadasVistas",
					u.getNotificacionesEnviadasVistas());
		}

		query.setParameter("lat", latitud);
		query.setParameter("long", longitud);

		List<Notificacion> notificaciones = query.getResultList();
		for (Notificacion n : notificaciones) {
			if (!u.getNotificacionesEnviadasNoVistas().contains(n))
				u.agregarNotificacionNoVista(n);
		}
		em.merge(u);

		if (notificaciones.isEmpty())
			return false;
		else
			return true;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean exiteNotificacionCheckIn(String mail, int latitud,
			int longitud) {

		Usuario u = em.find(Usuario.class, mail);
		Query query;
		if (u.getContactos().isEmpty())
			return false;

		if (u.getNotificacionesEnviadasVistas().isEmpty())
			query = em
					.createQuery("Select n FROM Notificacion n WHERE SQRT((n.checkin.sitio.latitud - :lat)*(n.checkin.sitio.latitud - :lat) + (n.checkin.sitio.longitud - :long)*(n.checkin.sitio.longitud - :long)) < 6000 AND n.checkin.usuario IN :contactos");

		else {
			query = em
					.createQuery("Select n FROM Notificacion n WHERE SQRT((n.checkin.sitio.latitud - :lat)*(n.checkin.sitio.latitud - :lat) + (n.checkin.sitio.longitud - :long)*(n.checkin.sitio.longitud - :long)) < 6000 AND n NOT IN :notificacionesEnviadasVistas AND n.checkin.usuario IN :contactos");
			query.setParameter("notificacionesEnviadasVistas",
					u.getNotificacionesEnviadasVistas());
		}
		query.setParameter("contactos", u.getContactos());
		query.setParameter("lat", latitud);
		query.setParameter("long", longitud);

		List<Notificacion> notificaciones = query.getResultList();
		for (Notificacion n : notificaciones) {
			if (!u.getNotificacionesEnviadasNoVistas().contains(n))
				u.agregarNotificacionNoVista(n);
		}
		em.merge(u);

		if (notificaciones.isEmpty())
			return false;
		else
			return true;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean exiteNotificacionOferta(String mail, int latitud,
			int longitud) {

		Usuario u = em.find(Usuario.class, mail);
		Query query;
		if (u.getNotificacionesEnviadasVistas().isEmpty())
			query = em
					.createQuery("Select n FROM Notificacion n WHERE SQRT((n.oferta.local.latitud - :lat)*(n.oferta.local.latitud - :lat) + (n.oferta.local.longitud - :long)*(n.oferta.local.longitud - :long)) < 6000");

		else {
			query = em
					.createQuery("Select n FROM Notificacion n WHERE SQRT((n.oferta.local.latitud - :lat)*(n.oferta.local.latitud - :lat) + (n.oferta.local.longitud - :long)*(n.oferta.local.longitud - :long)) < 6000 AND n NOT IN :notificacionesEnviadasVistas");
			query.setParameter("notificacionesEnviadasVistas",
					u.getNotificacionesEnviadasVistas());
		}

		query.setParameter("lat", latitud);
		query.setParameter("long", longitud);

		List<Notificacion> notificaciones = query.getResultList();
		for (Notificacion n : notificaciones) {
			if (!u.getNotificacionesEnviadasNoVistas().contains(n))
				u.agregarNotificacionNoVista(n);
		}
		em.merge(u);

		if (notificaciones.isEmpty())
			return false;
		else
			return true;
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean exiteNotificacionEvento(String mail, int latitud,
			int longitud) {
		
		Usuario u = em.find(Usuario.class, mail);
		Query query,query2;
		
		
		if (u.getNotificacionesEnviadasVistas().isEmpty()){
			
			query = em.createQuery("Select n FROM Notificacion n WHERE (SQRT((n.evento.sitioInteres.latitud - :lat)*(n.evento.sitioInteres.latitud - :lat) + (n.evento.sitioInteres.longitud - :long)*(n.evento.sitioInteres.longitud - :long)) < 6000)");
			query2 = em.createQuery("Select n FROM Notificacion n WHERE (SQRT((n.evento.latitud - :lat)*(n.evento.latitud - :lat) + (n.evento.longitud - :long)*(n.evento.longitud - :long)) < 6000)");
		
		}else {
			
			query = em.createQuery("Select n FROM Notificacion n WHERE (SQRT((n.evento.sitioInteres.latitud - :lat)*(n.evento.sitioInteres.latitud - :lat) + (n.evento.sitioInteres.longitud - :long)*(n.evento.sitioInteres.longitud - :long)) < 6000) AND n NOT IN :notificacionesEnviadasVistas");
			query2 = em.createQuery("Select n FROM Notificacion n WHERE SQRT((n.evento.latitud - :lat)*(n.evento.latitud - :lat) + (n.evento.longitud - :long)*(n.evento.longitud - :long)) < 6000 AND n NOT IN :notificacionesEnviadasVistas");
			
			query.setParameter("notificacionesEnviadasVistas",u.getNotificacionesEnviadasVistas());
			query2.setParameter("notificacionesEnviadasVistas",u.getNotificacionesEnviadasVistas());
		}

		
		query.setParameter("lat", latitud);
		query.setParameter("long", longitud);
		query2.setParameter("lat", latitud);
		query2.setParameter("long", longitud);

		List<Notificacion> notificaciones = query.getResultList();
		List<Notificacion> notificaciones2 = query2.getResultList();
		
		for (Notificacion n : notificaciones) {
			if (!u.getNotificacionesEnviadasNoVistas().contains(n)){
				u.agregarNotificacionNoVista(n);
			}
		}
		
		for (Notificacion n : notificaciones2) {
			if (!u.getNotificacionesEnviadasNoVistas().contains(n)){
				u.agregarNotificacionNoVista(n);
			}
		}
		
		em.merge(u);

		if (notificaciones.isEmpty() && notificaciones2.isEmpty())
			return false;
		else
			return true;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public JSONArray listarNotificaciones(String mail) {

		Usuario u = em.find(Usuario.class, mail);
		JSONArray jnotificaciones = new JSONArray();

		Collection<Notificacion> notificacionesVistas = u.getNotificacionesEnviadasVistas();
		for (Notificacion n : notificacionesVistas) {

			JSONObject ob = new JSONObject();
			try {
				ob.put("titulo", n.getTitulo());
				ob.put("descripcion", n.getDescripcion());
				ob.put("vista", true);

				if (n instanceof NotificacionSitioInteres) {
					ob.put("tipo", "SitioInteres");
					ob.put("sitio", ((NotificacionSitioInteres) n).getSitio()
							.aJSON());

				} else if (n instanceof NotificacionCheckIn) {
					ob.put("tipo", "CheckIn");
					ob.put("checkin", ((NotificacionCheckIn) n).getCheckin()
							.aJSON());

				} else if (n instanceof NotificacionOferta) {
					ob.put("oferta", ((NotificacionOferta) n).getOferta()
							.aJSON());
					ob.put("tipo", "Oferta");

				} else if (n instanceof NotificacionEvento) {
					ob.put("tipo", "Evento");
					ob.put("evento", ((NotificacionEvento) n).getEvento()
							.aJSON());

				}

				jnotificaciones.put(ob);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		Collection<Notificacion> notificacionesNoVistas = u.getNotificacionesEnviadasNoVistas();
		for (Notificacion n : notificacionesNoVistas) {

			JSONObject ob = new JSONObject();
			try {
				ob.put("titulo", n.getTitulo());
				ob.put("descripcion", n.getDescripcion());
				ob.put("vista", false);

				if (n instanceof NotificacionSitioInteres) {
					ob.put("tipo", "SitioInteres");
					ob.put("sitio", ((NotificacionSitioInteres) n).getSitio().aJSON());

				} else if (n instanceof NotificacionCheckIn) {
					ob.put("tipo", "CheckIn");
					ob.put("checkin", ((NotificacionCheckIn) n).getCheckin().aJSON());

				} else if (n instanceof NotificacionOferta) {
					ob.put("oferta", ((NotificacionOferta) n).getOferta().aJSON());
					ob.put("tipo", "Oferta");

				} else if (n instanceof NotificacionEvento) {
					ob.put("tipo", "Evento");
					ob.put("evento", ((NotificacionEvento) n).getEvento().aJSON());

				}
				jnotificaciones.put(ob);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return jnotificaciones;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void notificacionesVistas(String mail) {
		Usuario u = em.find(Usuario.class, mail);
		Collection<Notificacion> notificaciones = u.getNotificacionesEnviadasNoVistas();
		for (Notificacion n : notificaciones) {
			u.getNotificacionesEnviadasVistas().add(n);
		}
		u.getNotificacionesEnviadasNoVistas().clear();
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void actualizarNotificaciones() { //borro la notificacion de check in si fue hecho hace mas de dos horas

		Calendar fechaHoy = Calendar.getInstance();
		fechaHoy.add(Calendar.HOUR, -2);
		Date haceDosHoras = fechaHoy.getTime();
		
		Query query = em.createQuery("Select u FROM Usuario u");
		List<Usuario> usuarios = query.getResultList();
		
		Query query2 = em.createQuery("Select n FROM NotificacionOferta n WHERE n.oferta.fechaFin < :fechaHoy");
		query2.setParameter("fechaHoy", new Date());
		List<NotificacionOferta> notificacionesOfertasVencidas = query2.getResultList();
		
		Query query3 = em.createQuery("Select n FROM NotificacionEvento n WHERE n.evento.fechaFin < :fechaHoy");
		query3.setParameter("fechaHoy", new Date());
		List<NotificacionEvento> notificacionesEventosVencidos = query3.getResultList();
		
		Query query4 = em.createQuery("Select n FROM NotificacionCheckIn n WHERE n.checkin.fecha < :haceDosHoras");
		query4.setParameter("haceDosHoras", haceDosHoras);
		List<NotificacionCheckIn> notificacionesCheckInsVencidas = query4.getResultList();


		for (Usuario u : usuarios) {
		
			Collection<Notificacion> notificacionesNoVistas = u.getNotificacionesEnviadasNoVistas();
			Collection<Notificacion> notificacionesNoVistasActualizadas = new ArrayList<Notificacion>();
			for (Notificacion n : notificacionesNoVistas) {
				
				if (n instanceof NotificacionCheckIn) {
					if (!notificacionesCheckInsVencidas.contains(n))
						notificacionesNoVistasActualizadas.add(n);
				
				}else if (n instanceof NotificacionOferta){
					if (!notificacionesOfertasVencidas.contains(n))
						notificacionesNoVistasActualizadas.add(n);
					
				}else if (n instanceof NotificacionEvento){
					if (!notificacionesEventosVencidos.contains(n))
						notificacionesNoVistasActualizadas.add(n);
				}else
					notificacionesNoVistasActualizadas.add(n);
			}
			
			Collection<Notificacion> notificacionesVistas = u.getNotificacionesEnviadasVistas();
			Collection<Notificacion> notificacionesVistasActualizadas = new ArrayList<Notificacion>();
			
			for (Notificacion n : notificacionesVistas) {
			
				if (n instanceof NotificacionCheckIn) {
					if (!notificacionesCheckInsVencidas.contains(n))
						notificacionesVistasActualizadas.add(n);
					
				}else if (n instanceof NotificacionOferta){
					if (!notificacionesOfertasVencidas.contains(n))
						notificacionesVistasActualizadas.add(n);

				}else if (n instanceof NotificacionEvento){
					if (!notificacionesEventosVencidos.contains(n))
						notificacionesVistasActualizadas.add(n);
				}else
					notificacionesVistasActualizadas.add(n);
			}

			u.setNotificacionesEnviadasNoVistas(notificacionesNoVistasActualizadas);
			u.setNotificacionesEnviadasVistas(notificacionesVistasActualizadas);
			em.merge(u);

		}


		for (NotificacionCheckIn n : notificacionesCheckInsVencidas)
			em.remove(n);
		for (NotificacionOferta n : notificacionesOfertasVencidas)
			em.remove(n);
		for (NotificacionEvento n : notificacionesEventosVencidos)
			em.remove(n);

	}
}
