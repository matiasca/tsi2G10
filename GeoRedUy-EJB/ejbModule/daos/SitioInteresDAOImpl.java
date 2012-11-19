package daos;

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

import pojos.CheckIn;
import pojos.SitioInteres;
import pojos.Usuario;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SitioInteresDAOImpl implements SitioInteresDAO {

	@PersistenceContext(unitName = "GeoRedUy-Persistencia")
	private EntityManager em;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void insertar(SitioInteres s) {

		em.persist(s);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public SitioInteres obtenerSitioInteres(String nombre) {
		return em.find(SitioInteres.class, nombre);

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void agregarCheckInSitioInteres(CheckIn c, SitioInteres s) {
		s.agregarCheckIn(c);
		em.merge(s);

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public JSONArray listarSitiosInteresCercanos(int latitud, int longitud) {
		Query q = em
				.createQuery("SELECT s FROM SitioInteres s WHERE SQRT((s.latitud - ?)*(s.latitud - ?) + (s.longitud - ?)*(s.longitud - ?)) < 6000");

		q.setParameter(1, latitud);
		q.setParameter(2, latitud);
		q.setParameter(3, longitud);
		q.setParameter(4, longitud);

		List<SitioInteres> sitios = q.getResultList();

		JSONArray jsitios = new JSONArray();

		for (SitioInteres sitio : sitios) {
			jsitios.put(sitio.aJSON());
		}
		return jsitios;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<SitioInteres> listar(String nombreBusqueda) {

		Query query = em.createQuery(
				"Select s FROM SitioInteres s WHERE (s.nombre LIKE :nombre )")
				.setMaxResults(20);
		query.setParameter("nombre", "%" + nombreBusqueda + "%");

		List<SitioInteres> sitios = query.getResultList();

		return sitios;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int cantidadAListar(String nombreBusqueda) {

		Query query2 = em
				.createQuery("Select COUNT(s) FROM SitioInteres s WHERE (s.nombre LIKE :nombre )");
		query2.setParameter("nombre", "%" + nombreBusqueda + "%");

		int count = (Integer) query2.getSingleResult();

		return count;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public JSONArray cantidadCheckInsPorSitio(Date fechaInicio, Date fechaFin) {

		if (fechaFin == null)
			fechaFin = new Date();

		System.out.println("fechaInicio:" + fechaInicio);
		System.out.println("fechaFin:" + fechaFin);

		Query query, query2 = null;
		if (fechaInicio != null) {
			query = em
					.createQuery("Select s.nombre, s.latitud, s.longitud, COUNT(c) FROM SitioInteres s LEFT OUTER JOIN s.checksIn c WHERE c.fecha >= :fechaInicio AND c.fecha<=:fechaFin GROUP BY s.nombre ");
			query.setParameter("fechaInicio", fechaInicio);
			query.setParameter("fechaFin", fechaFin);

			query2 = em
					.createQuery("Select s.nombre, s.latitud, s.longitud FROM SitioInteres s LEFT OUTER JOIN s.checksIn c WHERE s.nombre NOT IN(Select s.nombre FROM SitioInteres s LEFT OUTER JOIN s.checksIn c WHERE c.fecha >= :fechaInicio AND c.fecha<=:fechaFin GROUP BY s.nombre ) AND (c.fecha < :fechaInicio OR c.fecha>:fechaFin OR s.checksIn IS EMPTY) GROUP BY s.nombre");
			query2.setParameter("fechaInicio", fechaInicio);
			query2.setParameter("fechaFin", fechaFin);

		} else {
			query = em
					.createQuery("Select s.nombre, s.latitud, s.longitud, COUNT(c) FROM SitioInteres s LEFT OUTER JOIN s.checksIn c WHERE c.fecha<=:fechaFin GROUP BY s.nombre ");
			query.setParameter("fechaFin", fechaFin);
			
			
			query2 = em
					.createQuery("Select s.nombre, s.latitud, s.longitud FROM SitioInteres s LEFT OUTER JOIN s.checksIn c WHERE s.nombre NOT IN(Select s.nombre FROM SitioInteres s LEFT OUTER JOIN s.checksIn c WHERE  c.fecha<=:fechaFin GROUP BY s.nombre ) AND (c.fecha>:fechaFin OR s.checksIn IS EMPTY) GROUP BY s.nombre");
			
			query2.setParameter("fechaFin", fechaFin);

		}
		List<Object[]> sitios = query.getResultList();
		System.out.println(sitios.size());
		JSONArray jsitios = new JSONArray();

		for (Object[] ob : sitios) {
			JSONObject json = new JSONObject();
			try {
				json.put("nombre", ob[0]);
				json.put("latitud", ob[1]);
				json.put("longitud", ob[2]);
				json.put("cantidad", ob[3]);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jsitios.put(json);
		}

	
			List<Object[]> sitios2 = query2.getResultList();
			System.out.println(sitios2.size());
			for (Object[] ob : sitios2) {

				JSONObject json = new JSONObject();
				try {
					json.put("nombre", ob[0]);
					json.put("latitud", ob[1]);
					json.put("longitud", ob[2]);
					json.put("cantidad", 0);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				jsitios.put(json);
			}
		

		System.out.println(jsitios);
		return jsitios;

	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public JSONObject obtenerInfoSitioInteres(String nombre){
		SitioInteres s = em.find(SitioInteres.class, nombre);
		if (s!= null)
			return s.aJSONInfo();
		else 
			return null;
	}

}
