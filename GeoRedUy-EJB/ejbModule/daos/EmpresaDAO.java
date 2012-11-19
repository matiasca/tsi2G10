package daos;

import java.util.Collection;

import javax.ejb.Local;

import otros.Imagen;

import pojos.Empresa;
import pojos.LocalEmp;




@Local
public interface EmpresaDAO {
	
	
	public int crearEmpresa(Empresa emp);
	
	public int agregarLocal(String nombreEmp ,LocalEmp local);
	
	public boolean modificarEmpresa(Empresa emp);
	
	public Empresa obtenerEmpresa(String nombreEmp);
	
	public Imagen obtenerLogo(String nombreEmp);
	
	public Collection<LocalEmp> listarLocales(String nombreEmp);

}
