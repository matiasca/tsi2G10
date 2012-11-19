package daos;

import javax.ejb.Local;

import otros.TipoNotificacion;


@Local
public interface TipoNotificacionDAO {

	public void insertar(TipoNotificacion not);

	public TipoNotificacion obtenerNotificacion(String nombre);
}
