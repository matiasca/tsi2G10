package daos;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pojos.Administrador;
import pojos.AdministradorEmpresa;
import pojos.Empresa;


@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AdministradorDAOImpl implements AdministradorDAO {
	
	@PersistenceContext(unitName="GeoRedUy-Persistencia")
	private EntityManager em;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void insertar(Administrador admin) {
		
		em.persist(admin);
		
		
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Administrador obtenerAdmin(String id) {
		
		
		return em.find(Administrador.class, id);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean agregarEmpresa(String idAdmin, Empresa emp){
		
		AdministradorEmpresa admin =em.find(AdministradorEmpresa.class, idAdmin);
		if(admin==null){
			return false;
		}else{
			admin.setEmpresa(emp);
			em.merge(admin);
			return true;
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void cambiarContraseniaMail(String id,String mailNuevo, String contrasenia){
		
		AdministradorEmpresa admin =em.find(AdministradorEmpresa.class, id);
		
		if(admin!=null){
			System.out.println("adentro if , admin no null");
			if(!mailNuevo.isEmpty())
				admin.setId(mailNuevo);
			if(!contrasenia.isEmpty())
				admin.setContrasenia(contrasenia);
			if (!mailNuevo.isEmpty() || !contrasenia.isEmpty())
				em.merge(admin);
		}
	}
}
