package daos;

import java.util.Date;

import javax.ejb.Local;

import org.codehaus.jettison.json.JSONArray;

import pojos.Administrador;
import pojos.Compra;
import pojos.Empresa;
import pojos.Oferta;




@Local
public interface OfertaDAO {

	public void crear(Oferta of);

	public boolean comprarOferta(int idOferta,Compra c);
	
	public Oferta obtenerOferta(String mail, int idOferta);
	
	public boolean evaluarOferta(int idOferta,int evaluacion);
	
	public JSONArray reporteOfertas(Date fechaInicio, Date fechaFin);
}
