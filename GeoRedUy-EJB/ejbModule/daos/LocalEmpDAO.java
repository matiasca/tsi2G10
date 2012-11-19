package daos;

import javax.ejb.Local;

import pojos.Administrador;
import pojos.Empresa;
import pojos.LocalEmp;




@Local
public interface LocalEmpDAO {

	public void insertar(LocalEmp loc, String nombreEmpresa);
	public LocalEmp obtenerLocalDeEmpresa(String nombreEmpresa, String nombre);
	public LocalEmp obtenerLocal(int idLocal);
}
