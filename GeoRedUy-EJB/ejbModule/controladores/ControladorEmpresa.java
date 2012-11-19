package controladores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import otros.Categoria;
import otros.EnviarMail;
import otros.Imagen;

import pojos.AdministradorEmpresa;
import pojos.Empresa;
import pojos.LocalEmp;
import pojos.NotificacionCheckIn;
import pojos.NotificacionOferta;
import pojos.Oferta;
import daos.AdministradorDAO;
import daos.CategoriaDAO;
import daos.EmpresaDAO;
import daos.ImagenDAO;
import daos.LocalEmpDAO;
import daos.NotificacionDAO;
import daos.OfertaDAO;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ControladorEmpresa implements IControladorEmpresa {

	@EJB
	private AdministradorDAO adminDAO;

	@EJB
	private EmpresaDAO empDAO;

	@EJB
	private ImagenDAO imgDAO;

	@EJB
	private LocalEmpDAO localDAO;

	@EJB
	private CategoriaDAO categoriaDAO;

	@EJB
	private NotificacionDAO notificacionDAO;
		
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public boolean altaEmpresa(String emailAdmin, String nombreEmp,
			String contrasenia) {

		AdministradorEmpresa ad = (AdministradorEmpresa) adminDAO
				.obtenerAdmin(emailAdmin);
		if (ad == null) {
			ad = new AdministradorEmpresa(emailAdmin, contrasenia);
			adminDAO.insertar(ad);
			EnviarMail enviar = new EnviarMail();
			enviar.enviarMailAdminEmpresa(emailAdmin, contrasenia);

		}
		Empresa emp = new Empresa(nombreEmp);

		if (empDAO.crearEmpresa(emp) == 1) {
			adminDAO.agregarEmpresa(emailAdmin, emp);

			return true;
		} else {

			return false;
		}

	}

	public boolean modificarEmpresa(String idEmpresa, String Descripcion,
			byte[] logo, String nombreLogo) {

		Empresa emp = empDAO.obtenerEmpresa(idEmpresa);

		emp.setDescripcion(Descripcion);
		System.out.println("nombre adentro" + nombreLogo);
		Imagen img = new Imagen(logo, nombreLogo);

		imgDAO.insertar(img);
		emp.setFoto(img);

		empDAO.modificarEmpresa(emp);

		return true;
	}

	public boolean insertarLocal(String idEmpresa, String nombre, int latitud,
			int longitud, String direccion) {

		if (localDAO.obtenerLocalDeEmpresa(idEmpresa, nombre) == null) {
			LocalEmp loc = new LocalEmp(nombre, direccion, latitud, longitud);
			localDAO.insertar(loc, idEmpresa);
			empDAO.agregarLocal(idEmpresa, loc);
			return true;
		} else {

			System.out.println("No se creo el local");
			return false;
		}

	}

	public Imagen obtenerLogo(String idEmpresa) {

		return empDAO.obtenerLogo(idEmpresa);
	}

	
	public List<LocalEmp> listarLocalesDeEmpresa(String idEmpresa) {

		return (List<LocalEmp>) empDAO.listarLocales(idEmpresa);
	}
	

}
