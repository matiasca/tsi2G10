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

import otros.Imagen;

import pojos.CheckIn;
import pojos.Empresa;
import pojos.LocalEmp;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EmpresaDAOImpl implements EmpresaDAO {

	@PersistenceContext(unitName = "GeoRedUy-Persistencia")
	private EntityManager em;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int crearEmpresa(Empresa emp) {

		if (em.find(Empresa.class, emp.getNombre()) == null) {
			em.persist(emp);
			return 1;
		} else
			return 0;

	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int agregarLocal(String nombreEmp ,LocalEmp local){
		Empresa emp=em.find(Empresa.class, nombreEmp);
		if (emp == null) {
			return 0;
		} else{
			
			Collection<LocalEmp>locales = emp.getLocales();
			locales.add(local);
			
			em.merge(emp);
			return 1;
		}
			

		
	}
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean modificarEmpresa(Empresa emp){
		
		em.merge(emp);
		
		return true;
	}
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Empresa obtenerEmpresa(String nombreEmp){
		
		return em.find(Empresa.class, nombreEmp);
		
		
	}
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Imagen obtenerLogo(String nombreEmp){
		
		return em.find(Empresa.class, nombreEmp).getFoto();
		
	}
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Collection<LocalEmp> listarLocales(String nombreEmp){
		
		
		Empresa emp=em.find(Empresa.class, nombreEmp);
		List<LocalEmp> locales =(List<LocalEmp>) emp.getLocales();
		System.out.println(locales);
		
		return locales;

		
	}
}
