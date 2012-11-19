package controladores;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import pojos.Administrador;
import pojos.AdministradorApp;
import pojos.AdministradorEmpresa;

import daos.AdministradorDAO;


@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ControladorAdministrador implements IControladorAdministrador {

	@EJB
	private AdministradorDAO adminDAO;

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public boolean altaAdminEmpresa(String id, String contrasenia) {

		if (adminDAO.obtenerAdmin(id) == null) {
			AdministradorEmpresa ad = new AdministradorEmpresa(id, contrasenia);
			adminDAO.insertar(ad);

			return true;
		}
		return false;
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Administrador loguearAdmin(String id, String contrasenia) {

		Administrador ad = adminDAO.obtenerAdmin(id);
		
		if (ad == null) {
			return null; // no existe el usuario
			
		} else {
			if (ad.getContrasenia().equals(contrasenia)){
					
				return ad;
				
			}else
				return null; // credenciales incorrectas

		}

	}

	public void cambiarContraseniaMail(String id, String mailNuevo, String contrasenia){
		adminDAO.cambiarContraseniaMail(id, mailNuevo, contrasenia);
	}
}
