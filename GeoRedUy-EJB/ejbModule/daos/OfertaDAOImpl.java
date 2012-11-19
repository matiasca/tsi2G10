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

import pojos.Compra;
import pojos.Oferta;
import pojos.SitioInteres;
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class OfertaDAOImpl implements OfertaDAO {

	@PersistenceContext(unitName="GeoRedUy-Persistencia")
	private EntityManager em;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void crear(Oferta of) {
		
		em.persist(of);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean comprarOferta(int idOferta, Compra c){
		Oferta of = em.find(Oferta.class, idOferta);
		if (of!=null){
			of.agregarCompra(c);
			em.merge(of);
			return true;
		}else 
			return false;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Oferta obtenerOferta(String mail, int idOferta){
		return em.find(Oferta.class, idOferta);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean evaluarOferta(int idOferta,int evaluacion){
		Oferta of = em.find(Oferta.class, idOferta);
		if (of!=null){
			double nuevaCalificacion = ((of.getCalificacion()*of.getCantidadCalificaciones())+evaluacion)/(of.getCantidadCalificaciones()+1);
			of.setCalificacion(nuevaCalificacion);
			of.setCantidadCalificaciones(of.getCantidadCalificaciones()+1);
			em.merge(of);
			return true;
		}else 
			return false;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public JSONArray reporteOfertas(Date fechaInicio, Date fechaFin){
		
		Query query, query2 = null;
		
		if(fechaFin==null)
			fechaFin = new Date();
		
		if (fechaInicio != null) {
			query = em
					.createQuery("Select ofe.nombre ,ofe.calificacion, ofe.costo, ofe.local.nombre ,ofe.local.empresa.nombre ,COUNT(co) FROM Oferta ofe  LEFT OUTER JOIN ofe.compras co WHERE co.fecha >= :fechaInicio AND co.fecha<=:fechaFin GROUP BY ofe.id ORDER BY COUNT(co) DESC ");
			query.setParameter("fechaInicio", fechaInicio);
			query.setParameter("fechaFin", fechaFin);

		
			query2 = em
					.createQuery("Select ofe.nombre ,ofe.calificacion, ofe.costo, ofe.local.nombre ,ofe.local.empresa.nombre ,COUNT(co) FROM Oferta ofe  LEFT OUTER JOIN ofe.compras co WHERE ofe.id NOT IN(Select ofe.id FROM Oferta ofe  LEFT OUTER JOIN ofe.compras co WHERE co.fecha >= :fechaInicio AND co.fecha<=:fechaFin GROUP BY ofe.id ) AND (co.fecha < :fechaInicio OR co.fecha>:fechaFin OR ofe.compras IS EMPTY ) GROUP BY ofe.id ORDER BY ofe.nombre ");
			query2.setParameter("fechaInicio", fechaInicio);
			query2.setParameter("fechaFin", fechaFin);

		} else {
			query = em
					.createQuery("Select ofe.nombre ,ofe.calificacion, ofe.costo, ofe.local.nombre ,ofe.local.empresa.nombre ,COUNT(co) FROM Oferta ofe  LEFT OUTER JOIN ofe.compras co WHERE co.fecha<=:fechaFin GROUP BY ofe.id ORDER BY COUNT(co) DESC");
			
			query.setParameter("fechaFin", fechaFin);
			
			query2 = em
					.createQuery("Select ofe.nombre ,ofe.calificacion, ofe.costo, ofe.local.nombre ,ofe.local.empresa.nombre ,COUNT(co) FROM Oferta ofe  LEFT OUTER JOIN ofe.compras co WHERE ofe.id NOT IN(Select ofe.id FROM Oferta ofe  LEFT OUTER JOIN ofe.compras co WHERE  co.fecha<=:fechaFin GROUP BY ofe.id  ) AND (co.fecha>:fechaFin OR ofe.compras IS EMPTY) GROUP BY ofe.id ORDER BY ofe.nombre ");
			
			query2.setParameter("fechaFin", fechaFin);
			
			

		}
		List<Object[]> ofertas = query.getResultList();
		System.out.println(ofertas.size());
		JSONArray jofertas = new JSONArray();

		for (Object[] ob : ofertas) {
			JSONObject json = new JSONObject();
			try {
				json.put("nombre", ob[0]);
				json.put("calificacion", ob[1]);
				json.put("costo", ob[2]);
				json.put("local", ob[3]);
				json.put("empresa", ob[4]);
				json.put("cantidad", ob[5]);
				double costo=(Double)ob[2];
				long cantidad = (Long)ob[5];
				json.put("monto",cantidad*costo);
				

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jofertas.put(json);
		}
		
		 ofertas = query2.getResultList();
		System.out.println(ofertas.size());
		
		for (Object[] ob : ofertas) {
			JSONObject json = new JSONObject();
			try {
				json.put("nombre", ob[0]);
				json.put("calificacion", ob[1]);
				json.put("costo", ob[2]);
				json.put("local", ob[3]);
				json.put("empresa", ob[4]);
				json.put("cantidad", 0);
				
				json.put("monto",0);
				

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jofertas.put(json);
		}
		
		
		return jofertas;
	}
	
}
