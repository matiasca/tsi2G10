package otros;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import com.sun.jersey.core.util.Base64;
import org.codehaus.jettison.json.JSONObject;



@Entity
public class Imagen implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Lob
	private byte[] imagen;
	
	private String nombre;

	public Imagen() {
	}

	public Imagen(byte[] imagen, String nombre) {
		this.nombre = nombre;
		this.imagen = imagen;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public JSONObject aJSON() {
		JSONObject json = new JSONObject();
		try {
			if (imagen != null)
				json.put("imagen",new String(Base64.encode(imagen)));
			else
				json.put("imagen", "");

			json.put("nombre", this.nombre);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
}
