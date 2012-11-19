package pojos;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class CheckIn {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	private SitioInteres sitio;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Usuario usuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	@Column(length = 10000)
	private String comentario;

	@OneToOne(fetch = FetchType.LAZY)
	private Imagen foto;
	
	@OneToMany(fetch = FetchType.LAZY)
	private Collection<Comentario> comentarios;

	public CheckIn() {
	}

	public CheckIn(SitioInteres sitio, Usuario usuario, String comentario,
			Imagen foto) {
		this.sitio = sitio;
		this.usuario = usuario;
		this.fecha = new Date();
		this.comentario = comentario;
		this.foto = foto;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public SitioInteres getSitio() {
		return sitio;
	}

	public void setSitio(SitioInteres sitio) {
		this.sitio = sitio;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Imagen getFoto() {
		return foto;
	}

	public void setFoto(Imagen foto) {
		this.foto = foto;
	}

	public void agregarComentario(Comentario c){
		comentarios.add(c);
	}
	
	public Collection<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(Collection<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public JSONObject aJSON() {

		JSONObject json = new JSONObject();
		try {
			json.put("id", id);
			if (fecha != null)
				json.put("fecha", this.fecha);
			else
				json.put("fecha", "");

			if (comentario != null)
				json.put("comentario", this.comentario);
			else
				json.put("comentario", "");

			json.put("id", this.id);
			if (foto != null)
				json.put("foto", this.foto.aJSON());
	
			if (usuario != null){
				json.put("usuario", this.usuario.getNombre());
			}else
				json.put("usuario", "");
			
			if (this.usuario.getFotoPerfil()!= null)
				json.put("fotoPerfil", this.usuario.getFotoPerfil().aJSON());
		

			if (sitio != null)
				json.put("nombreSitio", this.sitio.getNombre());
			else
				json.put("nombreSitio", "");

			JSONArray jcomentarios = new JSONArray();
			if (comentarios != null)
				for (Comentario c : this.comentarios) {
					if (c != null)
						jcomentarios.put(c.aJSON());
				}
			json.put("comentarios", jcomentarios);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
}
