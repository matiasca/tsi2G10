package daos;

import javax.ejb.Local;

import pojos.Administrador;
import pojos.Empresa;




@Local
public interface AdministradorDAO {

	public void insertar(Administrador admin);
	
	public Administrador obtenerAdmin(String id);
	
	public boolean agregarEmpresa(String idAdmin, Empresa emp);
	
	public void cambiarContraseniaMail(String id, String mailNuevo,String contrasenia);
}
