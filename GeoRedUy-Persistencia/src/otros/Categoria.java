package otros;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Entity
public class Categoria implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private String nombreCategoria; /* Música,Entretenimiento,Juegos,Gastronomía,Deportes,
										Negocios,Estética,Inmuebles,Turismo */
	

	public Categoria() {
	}

	public Categoria(String categoria) {

		this.nombreCategoria = categoria;
	}

	public String getCategoria() {
		return nombreCategoria;
	}

	public void setCategoria(String categoria) {
		this.nombreCategoria = categoria;
	}

	public JSONObject aJSON() {
		JSONObject json = new JSONObject();
		try {
			if (nombreCategoria != null)
				json.put("nombreCategoria", this.nombreCategoria);
			else
				json.put("nombreCategoria", "");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

}
