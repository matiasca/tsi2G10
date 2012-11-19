package daos;

import javax.ejb.Local;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import pojos.CheckIn;
import pojos.Comentario;

@Local
public interface CheckInDAO {

	public void insertar(CheckIn c);
	
	public void insertarComentario(Comentario c,int id);
	
	public JSONArray listarUltimosCkeckInsAmigos(String mail, int principio, int fin);
	
	public JSONObject obtenerCheckIn(int id);
}
