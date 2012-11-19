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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import otros.Categoria;
import otros.Imagen;

@Entity
public class Oferta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String nombre;
	@Column(length = 10000)
	private String descripcion;
	private int cantidadCalificaciones;
	private double calificacion;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaInicio;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaFin;

	@OneToOne(fetch = FetchType.LAZY)
	private Imagen imagen;

	private Double costo;

	@ManyToMany(fetch = FetchType.LAZY)
	private Collection<Categoria> categorias;

	@ManyToOne
	private LocalEmp local;
	
	@OneToMany(fetch = FetchType.LAZY)
	private Collection<Compra> compras;

	public Oferta() {
	}

	public Oferta(String nombre, String descripcion, Date fechaInicio,
			Date fechaFin, Imagen imagen, Double costo,
			Collection<Categoria> categorias, LocalEmp local) {

		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.imagen = imagen;
		this.costo = costo;
		this.categorias = categorias;
		this.local = local;
		this.calificacion = 0;
		this.cantidadCalificaciones = 0;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Imagen getImagen() {
		return imagen;
	}

	public void setImagen(Imagen imagen) {
		this.imagen = imagen;
	}

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}

	public Collection<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(Collection<Categoria> categorias) {
		this.categorias = categorias;
	}

	public LocalEmp getLocal() {
		return local;
	}

	public void setLocal(LocalEmp local) {
		this.local = local;
	}

	public void agregarCompra(Compra c){
		compras.add(c);
	}
	
	public Collection<Compra> getCompras() {
		return compras;
	}

	public void setCompras(Collection<Compra> compras) {
		this.compras = compras;
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
			if (fechaInicio != null)
				json.put("fechaInicio", fechaInicio.toString());	
			else
				json.put("fechaInicio", "");
			if (fechaFin != null)
				json.put("fechaFin", fechaFin.toString());	
			else
				json.put("fechaFin", "");
			if (costo != null)
				json.put("costo", costo);	
			else
				json.put("costo", "");
			
			json.put("calificacion", calificacion);
			
			if(imagen!=null)
				json.put("foto", imagen.aJSON());
			JSONArray jcategorias = new JSONArray();
			if (categorias != null)
				for (Categoria cat : this.categorias) {
					if (cat != null)
						jcategorias.put(cat.aJSON());
				}
			json.put("categorias", jcategorias);
	
			if (local != null)
				json.put("local", local.aJSON());	
			else
				json.put("local", "");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	public double getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(double calificacion) {
		this.calificacion = calificacion;
	}

	public int getCantidadCalificaciones() {
		return cantidadCalificaciones;
	}

	public void setCantidadCalificaciones(int cantidadCalificaciones) {
		this.cantidadCalificaciones = cantidadCalificaciones;
	}
}
