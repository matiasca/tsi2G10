package daos;

import javax.ejb.Local;

import otros.Imagen;

@Local
public interface ImagenDAO {
	
	public void insertar(Imagen img);

}
