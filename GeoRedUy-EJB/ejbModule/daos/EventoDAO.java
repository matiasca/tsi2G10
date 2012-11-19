package daos;

import java.util.Collection;

import javax.ejb.Local;

import otros.Imagen;

import pojos.Empresa;
import pojos.Evento;
import pojos.LocalEmp;




@Local
public interface EventoDAO {
	
	
	public void crearEvento(Evento ev);
	

}
