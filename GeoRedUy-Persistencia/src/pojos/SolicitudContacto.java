package pojos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Entity
public class SolicitudContacto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int identidad;

	private boolean vista;
	
	@ManyToOne
	Usuario receptorSolicitud;
	@ManyToOne
	Usuario emisorSolicitud;


	public SolicitudContacto() {
		setVista(false);
	}
	
	public SolicitudContacto(Usuario emisorSolicitud,Usuario receptorSolicitud) {
		this.receptorSolicitud = receptorSolicitud;
		this.emisorSolicitud = emisorSolicitud;
		setVista(false);
	}

	public boolean isVista() {
		return vista;
	}

	public void setVista(boolean vista) {
		this.vista = vista;
	}

	public Usuario getReceptorSolicitud() {
		return receptorSolicitud;
	}

	public void setReceptorSolicitud(Usuario receptorSolicitud) {
		this.receptorSolicitud = receptorSolicitud;
	}


	public Usuario getEmisorSolicitud() {
		return emisorSolicitud;
	}

	public void setEmisorSolicitud(Usuario emisorSolicitud) {
		this.emisorSolicitud = emisorSolicitud;
	}

	public JSONObject aJSON() {

		JSONObject json = new JSONObject();
		try {
			json.put("mailEmisor", emisorSolicitud.getMail());
			if (emisorSolicitud.getNombre() != null)
				json.put("nombreEmisor", emisorSolicitud.getNombre());
			else
				json.put("nombreEmisor", "");
			
			json.put("mailReceptor", receptorSolicitud.getMail());
			if (receptorSolicitud.getNombre() != null)
				json.put("nombreReceptor", receptorSolicitud.getNombre());
			else
				json.put("nombreReceptor", "");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;

	}

}
