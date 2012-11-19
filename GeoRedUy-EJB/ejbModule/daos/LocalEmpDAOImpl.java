package daos;

import java.util.Iterator;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import pojos.CheckIn;
import pojos.Empresa;
import pojos.LocalEmp;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class LocalEmpDAOImpl implements LocalEmpDAO {

	@PersistenceContext(unitName = "GeoRedUy-Persistencia")
	private EntityManager em;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void insertar(LocalEmp loc, String nombreEmpresa) {
		 Empresa empresa = em.find(Empresa.class, nombreEmpresa);
		 loc.setEmpresa(empresa);
		 em.persist(empresa);
		 em.persist(loc);
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public LocalEmp obtenerLocalDeEmpresa(String nombreEmpresa, String nombre) {

		Query q = em
				.createQuery("SELECT e.locales FROM Empresa e WHERE e.nombre=:nombreEmpresa");

		q.setParameter("nombreEmpresa", nombreEmpresa);
		List<LocalEmp> locales = q.getResultList();
		System.out.println(locales.size());
		Iterator<LocalEmp> it = locales.iterator();
		boolean encontre = false;

		LocalEmp loc = null;
		while (it.hasNext() && !encontre) {
			loc = (LocalEmp) it.next();
			if (loc.getNombre().equals(nombre))
				encontre = true;
		}

		if (encontre)
			return loc;
		else
			return null;
	}

	public LocalEmp obtenerLocal(int idLocal) {

		return em.find(LocalEmp.class, idLocal);

	}
}
