package daos;

import javax.ejb.Local;

import pojos.SolicitudContacto;


@Local
public interface SolicitudContactoDAO {

	public void insertar(SolicitudContacto u);
	
}
