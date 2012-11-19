package controladores;


import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import otros.Categoria;
import otros.Imagen;

import pojos.Empresa;
import pojos.LocalEmp;

@Local
public interface IControladorEmpresa {

	public Imagen obtenerLogo(String idEmpresa);
	public boolean altaEmpresa(String emailAdmin, String nombreEmp, String contrasenia);
	
	public boolean modificarEmpresa(String idEmpresa, String Descripcion, byte[] logo, String nombreLogo);
	
	public boolean insertarLocal(String idEmpresa,String nombre,int latitud,int longitud, String direccion);
	
	public List<LocalEmp> listarLocalesDeEmpresa(String idEmpresa);
	
}
