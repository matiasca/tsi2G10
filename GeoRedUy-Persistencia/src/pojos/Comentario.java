package pojos;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Entity
public class Comentario {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	@Column(length = 10000)
	private String comentario;
	private Date fecha;
	
	@ManyToOne
	private Usuario usuario;
	
	public Comentario(){}
	
	public Comentario(String comentario, Date fecha, Usuario usuario) {
		this.comentario = comentario;
		this.fecha = fecha;
		this.usuario = usuario;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public JSONObject aJSON(){
		
		JSONObject json = new JSONObject();
		
		try {
			json.put("comentario", comentario);
			json.put("fecha", fecha.toString());
			json.put("usuario", usuario.getNombre());
			json.put("fotoPerfil", usuario.getFotoPerfil().aJSON());
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return json;
		
	}
}
