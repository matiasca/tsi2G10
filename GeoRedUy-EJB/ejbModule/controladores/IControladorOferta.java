package controladores;

import java.util.Collection;
import java.util.Date;

import org.codehaus.jettison.json.JSONArray;

public interface IControladorOferta {

	public boolean crearOferta(String nombreEmpresa,String nombre,String descripcion, Date fechaInicio,Date fechaFin,byte[] file,String fileName, Double costo,
			Collection<String> tiposCategorias,int local);
	
	public boolean comprarOferta(String mail,int idOferta);
	
	public boolean usuarioComproOferta(String mail,int idOferta);
	
	public boolean evaluarOferta(int idOferta,int evaluacion);
	
	public JSONArray reporteOfertas(Date fechaInicio, Date fechaFin);
}
