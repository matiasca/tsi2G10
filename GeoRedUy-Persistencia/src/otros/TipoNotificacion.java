package otros;

import java.io.Serializable;

import javax.persistence.Entity;

import javax.persistence.Id;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Entity
public class TipoNotificacion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String nombreNotificacion; /* Ofertas,SitiosInteres,Eventos,CheckIn */

	public TipoNotificacion() {
	}

	public TipoNotificacion(String notificacion) {

		this.nombreNotificacion = notificacion;
	}

	public String getNotificacion() {
		return nombreNotificacion;
	}

	public void setNotificacion(String notificacion) {
		this.nombreNotificacion = notificacion;
	}

	public JSONObject aJSON() {
		JSONObject json = new JSONObject();
		try {
			if (nombreNotificacion != null)
				json.put("nombreNotificacion", this.nombreNotificacion);
			else
				json.put("nombreNotificacion", "");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
}
