package controladores;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import otros.Categoria;
import pojos.SitioInteres;


@Local
public interface IControladorSitioInteres {

	public boolean altaSitioInteres(String nombre,String direccion, String descripcion,
			int latitud, int longitud, byte[] logo, String nombreLogo, Map<String,byte[]> imagenes,
			Collection<String> categorias);

	public JSONArray listarSitiosInteresCercanos(int latitud, int longitud);
	
	public List<SitioInteres> listarSitiosInteres(String nombreBusqueda);
	
	public Collection<Categoria>  listarCategorias();
	
	public boolean altaCheckIn(String comentario,String mail,String token,String nombreSitio,byte[] foto);
	
	public void agregarComentarioCheckIn(String mail,int idCheckIn, String comentario);

	public JSONArray listarUltimosCkeckInsAmigos(String mail, int principio, int fin);
	
	public int cantidadListarSitiosInteres(String nombreBusqueda);
	
	public int crearEvento(String nombre,String direccion, String descripcion,Date fechaInicio,Date fechaFin, int latitud, int longitud,
			byte[] imagen, String nombreImagen,
			Collection<String> categorias, String nombreSitio);

	public JSONArray cantidadCheckInsPorSitio(Date fechaInicio, Date fechaFin);
	
	public JSONObject verInfoCheckIn(int idCheckIn);
	
	public JSONObject verInfoSitioInteres(String nombre);
	
}
