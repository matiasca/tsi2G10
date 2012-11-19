package pojos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import otros.Categoria;
import otros.Imagen;

@Entity
public class LocalEmp {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String nombre;
	private String direccion;

	private int latitud;
	private int longitud;
	
	@ManyToOne
	private Empresa empresa;
	
	public LocalEmp() {
		
	}
	
	
	public Empresa getEmpresa() {
		return empresa;
	}


	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getId() {
		return id;
	}


	public LocalEmp(String nombre, String direccion, int latitud, int longitud) {
		
		this.nombre = nombre;
		this.direccion = direccion;
		this.latitud = latitud;
		this.longitud = longitud;
	}

	public int getLatitud() {
		return latitud;
	}

	public void setLatitud(int latitud) {
		this.latitud = latitud;
	}

	public int getLongitud() {
		return longitud;
	}

	public void setLongitud(int longitud) {
		this.longitud = longitud;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	
	public JSONObject aJSON(){
		JSONObject json = new JSONObject();
		try {
			if (nombre != null)
				json.put("nombre", nombre);	
			else
				json.put("nombre", "");
			if (direccion != null)
				json.put("direccion", direccion);	
			else
				json.put("direccion", "");
		
			json.put("nombreEmpresa", empresa.getNombre());
			
	
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
}
