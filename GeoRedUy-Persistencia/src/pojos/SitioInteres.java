package pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;


import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;


import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import org.codehaus.jettison.json.JSONObject;

import otros.Categoria;
import otros.Imagen;

@Entity
public class SitioInteres implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private String nombre;
	
	@Column(length = 10000)
	private String descripcion;
	private int latitud;
	private int longitud;
	private String direccion;
	
	@OneToOne(fetch = FetchType.LAZY)
	private Imagen logo;

	@OneToMany(cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	private Collection<Imagen> imagenes;


	@ManyToMany(fetch = FetchType.LAZY)
	private Collection<Categoria> categorias;

	@OneToMany(fetch = FetchType.LAZY)
	private Collection<CheckIn> checksIn;

	public SitioInteres() {
		imagenes = new ArrayList<Imagen>();
		categorias = new ArrayList<Categoria>();
		checksIn = new ArrayList<CheckIn>();
	}

	public SitioInteres(String nombre,String direccion, String descripcion, int latitud,
			int longitud, Imagen logo, Collection<Imagen> imagenes,
			Collection<Categoria> categorias) {

		this.nombre = nombre;
		this.descripcion = descripcion;
		this.imagenes = imagenes;
		this.categorias = categorias;
		this.latitud = latitud;
		this.longitud = longitud;
		this.direccion= direccion;
		this.logo = logo;
	}

	
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Collection<CheckIn> getChecksIn() {
		return checksIn;
	}

	public void setChecksIn(Collection<CheckIn> checksIn) {
		this.checksIn = checksIn;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Imagen getLogo() {
		return logo;
	}

	public void setLogo(Imagen logo) {
		this.logo = logo;
	}

	public Collection<Imagen> getImagenes() {
		return imagenes;
	}

	public void setImagenes(Collection<Imagen> imagenes) {
		this.imagenes = imagenes;
	}

	public Collection<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(Collection<Categoria> categorias) {
		this.categorias = categorias;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
	
	public void agregarCheckIn(CheckIn c) {
		this.checksIn.add(c);
	}

	public JSONObject aJSON() {

		JSONObject json = new JSONObject();
		try {
			if (nombre != null)
				json.put("nombre", this.nombre);
			else
				json.put("nombre", "");
			
			if (direccion != null)
				json.put("direccion", this.direccion);
			else
				json.put("direccion", "");

			if (descripcion != null)
				json.put("descripcion", this.descripcion);
			else
				json.put("descripcion", "");

			json.put("latitud", this.latitud);

			json.put("longitud", this.longitud);

			JSONArray jcategorias = new JSONArray();
			if (categorias != null)
				for (Categoria cat : this.categorias) {
					if (cat != null)
						jcategorias.put(cat.aJSON());
				}
			json.put("categorias", jcategorias);
			
			if (logo!=null)
				json.put("logo", logo.aJSON());
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	
	public JSONObject aJSONInfo() {

		JSONObject json = new JSONObject();
		try {
			if (nombre != null)
				json.put("nombre", this.nombre);
			else
				json.put("nombre", "");
			
			if (direccion != null)
				json.put("direccion", this.direccion);
			else
				json.put("direccion", "");

			if (descripcion != null)
				json.put("descripcion", this.descripcion);
			else
				json.put("descripcion", "");

			json.put("latitud", this.latitud);

			json.put("longitud", this.longitud);

			JSONArray jcategorias = new JSONArray();
			if (categorias != null)
				for (Categoria cat : this.categorias) {
					if (cat != null)
						jcategorias.put(cat.aJSON());
				}
			json.put("categorias", jcategorias);
			
			if (logo!=null)
				json.put("logo", logo.aJSON());
			
			JSONArray jimagenes = new JSONArray();
			if (imagenes != null)
				for (Imagen im : this.imagenes) {
					if (im != null)
						jimagenes.put(im.aJSON());
				}
			json.put("imagenes", jimagenes);
			
			JSONArray jcheckIns = new JSONArray();
			if (checksIn != null)
				for (CheckIn c : this.checksIn) {
					if (c != null)
						jcheckIns.put(c.aJSON());
				}
			json.put("checkins", jcheckIns);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}


}
