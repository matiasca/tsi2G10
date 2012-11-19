package pojos;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import otros.Categoria;
import otros.Imagen;

@Entity
public class Evento {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String nombre;
	
	@Column(length = 10000)
	private String descripcion;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaInicio;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaFin;
	
	private String direccion;
	private int latitud;
	private int longitud;
	
	@OneToOne(fetch = FetchType.LAZY)
	private Imagen imagen;
	
	
	
	@ManyToMany
	private Collection <Categoria> categorias;
	
	@OneToOne
	private SitioInteres sitioInteres;

	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getDireccion() {
		return direccion;
	}


	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}


	public Evento() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Evento(String nombre, String descripcion, Date fechaInicio,
			Date fechaFin,String direccion, int latitud, int longitud, Imagen imagen, Collection<Categoria> categorias,
			SitioInteres sitioInteres) {
		
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.latitud = latitud;
		this.longitud = longitud;
		this.imagen = imagen;
		this.direccion=direccion;
		
		this.categorias = categorias;
		this.sitioInteres = sitioInteres;
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

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
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

	public Imagen getImagen() {
		return imagen;
	}

	public void setImagen(Imagen imagen) {
		this.imagen = imagen;
	}



	public Collection<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(Collection<Categoria> categorias) {
		this.categorias = categorias;
	}

	public SitioInteres getSitioInteres() {
		return sitioInteres;
	}

	public void setSitioInteres(SitioInteres sitioInteres) {
		this.sitioInteres = sitioInteres;
	}
	
	public JSONObject aJSON(){
		JSONObject json = new JSONObject();

		try {

			json.put("id", id);	
			if (nombre != null)
				json.put("nombre", nombre);	
			else
				json.put("nombre", "");
			
			if (descripcion != null)
				json.put("descripcion", descripcion);	
			else
				json.put("descripcion", "");
			
			if (direccion != null)
				json.put("direccion", direccion);	
			else
				json.put("direccion", "");
			
			if (fechaInicio != null)
				json.put("fechaInicio", fechaInicio.toString());	
			else
				json.put("fechaInicio", "");
			
			if (fechaFin != null)
				json.put("fechaFin", fechaFin.toString());	
			else
				json.put("fechaFin", "");
			
			JSONArray jcategorias = new JSONArray();
			if (categorias != null)
				for (Categoria cat : this.categorias) {
					if (cat != null)
						jcategorias.put(cat.aJSON());
				}
			json.put("categorias", jcategorias);
	
			if (sitioInteres != null){
				json.put("nombreSitio", sitioInteres.getNombre());
				json.put("direccionSitio", sitioInteres.getDireccion());
			}
			if (imagen!=null)
				json.put("foto", imagen.aJSON());
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
}
