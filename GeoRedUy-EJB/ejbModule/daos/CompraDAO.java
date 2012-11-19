package daos;

import javax.ejb.Local;

import pojos.Compra;

@Local
public interface CompraDAO {

	public void insertar(Compra c,int idOferta);

}
