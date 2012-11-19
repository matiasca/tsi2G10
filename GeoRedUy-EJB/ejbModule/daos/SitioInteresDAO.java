package daos;


import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import pojos.CheckIn;
import pojos.SitioInteres;


@Local
public interface SitioInteresDAO {

	public void insertar(SitioInteres s);

	public SitioInteres obtenerSitioInteres(String nombre);

	public JSONArray listarSitiosInteresCercanos(int latitud,int longitud);
	
	public void agregarCheckInSitioInteres(CheckIn c,SitioInteres s);
	
	public int cantidadAListar(String nombreBusqueda);
	
	public List<SitioInteres> listar(String nombre);
	
	public JSONArray cantidadCheckInsPorSitio(Date fechaInicio, Date fechaFin);
	
	public JSONObject obtenerInfoSitioInteres(String nombre);
}
