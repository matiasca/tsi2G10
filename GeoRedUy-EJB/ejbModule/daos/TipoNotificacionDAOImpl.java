package daos;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import otros.TipoNotificacion;



@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class TipoNotificacionDAOImpl implements TipoNotificacionDAO {

	@PersistenceContext(unitName="GeoRedUy-Persistencia")
	private EntityManager em;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void insertar(TipoNotificacion not){
		em.persist(not);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public TipoNotificacion obtenerNotificacion(String nombre){
		
		return em.find(TipoNotificacion.class, nombre);
	}
}
