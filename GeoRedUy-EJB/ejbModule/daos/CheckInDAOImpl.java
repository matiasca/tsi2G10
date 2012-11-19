package daos;

import java.util.Collection;
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
import org.codehaus.jettison.json.JSONObject;

import pojos.CheckIn;
import pojos.Comentario;
import pojos.SitioInteres;
import pojos.Usuario;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CheckInDAOImpl implements CheckInDAO {

	@PersistenceContext(unitName = "GeoRedUy-Persistencia")
	private EntityManager em;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void insertar(CheckIn c) {

		em.persist(c);

	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void insertarComentario(Comentario comentario,int id){
		CheckIn c = em.find(CheckIn.class, id);
		if (c!=null){
			c.agregarComentario(comentario);
			em.merge(c);
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public JSONObject obtenerCheckIn(int id){
		
		CheckIn c = em.find(CheckIn.class, id);
		if (c!= null)
			return c.aJSON();
		else
			return null;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public JSONArray listarUltimosCkeckInsAmigos(String mail, int principio, int fin) {
		JSONArray jcheckins = new JSONArray();
		Usuario u = em.find(Usuario.class, mail);
		if (u != null) {
			Query q;
			if (u.getContactos().isEmpty())
				q = em.createQuery("SELECT c FROM CheckIn c WHERE c.usuario.mail=:mail ORDER BY c.fecha DESC");
			
			else {
				q = em.createQuery("SELECT c FROM CheckIn c WHERE c.usuario.mail=:mail OR c.usuario IN :contactos ORDER BY c.fecha DESC");
				q.setParameter("contactos", u.getContactos());
			}
			q.setParameter("mail", mail);
			
			q.setFirstResult(principio);
			q.setMaxResults(fin);
			List<CheckIn> checkins = q.getResultList();
			
			for (CheckIn c : checkins) {
				jcheckins.put(c.aJSON());
			}
			
		}

		return jcheckins;
	}
}
