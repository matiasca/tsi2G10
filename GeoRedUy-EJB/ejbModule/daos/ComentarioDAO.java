package daos;

import javax.ejb.Local;

import pojos.Comentario;

@Local
public interface ComentarioDAO {

	public void insertar(Comentario c);
}
