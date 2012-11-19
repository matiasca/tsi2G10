package daos;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pojos.Evento;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EventoDAOImpl implements EventoDAO {

	@PersistenceContext(unitName = "GeoRedUy-Persistencia")
	private EntityManager em;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void crearEvento(Evento ev) {
		
		em.persist(ev);
		
		
	}

}
