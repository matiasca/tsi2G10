package daos;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import otros.Categoria;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CategoriaDAOImpl implements CategoriaDAO {

	@PersistenceContext(unitName="GeoRedUy-Persistencia")
	private EntityManager em;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void insertar(Categoria cat){
		em.persist(cat);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Categoria obtenerCategoria(String nombre){
		
		return em.find(Categoria.class, nombre);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Collection<Categoria> listarCategorias(){
		
		Query q = em.createQuery("SELECT c FROM Categoria c"); 
		return q.getResultList();

	}
}
