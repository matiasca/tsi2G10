package daos;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pojos.Compra;
import pojos.Oferta;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CompraDAOImpl implements CompraDAO {

	@PersistenceContext(unitName="GeoRedUy-Persistencia")
	private EntityManager em;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void insertar(Compra c,int idOferta) {
		Oferta of = em.find(Oferta.class, idOferta);
		c.setOferta(of);
		em.persist(c);
	}

	
}
