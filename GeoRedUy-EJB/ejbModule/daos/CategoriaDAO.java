package daos;

import java.util.Collection;

import javax.ejb.Local;

import otros.Categoria;

@Local
public interface CategoriaDAO {

	public void insertar(Categoria cat);

	public Categoria obtenerCategoria(String nombre);
	
	public Collection<Categoria> listarCategorias();
}
