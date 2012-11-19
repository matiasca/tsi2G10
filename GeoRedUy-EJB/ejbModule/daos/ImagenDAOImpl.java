package daos;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import otros.Imagen;


@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ImagenDAOImpl implements ImagenDAO {

	@PersistenceContext(unitName="GeoRedUy-Persistencia")
	private EntityManager em;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void insertar(Imagen img) {
	
		em.persist(img);
	}

}
