package controladores;

import javax.ejb.Local;

import pojos.Administrador;

@Local
public interface IControladorAdministrador {
	
	public boolean altaAdminEmpresa(String id, String contrasenia);
	
	public Administrador loguearAdmin (String id, String contrasenia);
	
	public void cambiarContraseniaMail(String id, String mailNuevo, String contrasenia);
	
}
