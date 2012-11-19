package pojos;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import otros.Categoria;
import otros.Imagen;
import otros.TipoNotificacion;

@Entity
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private String mail;
	private String nombre;
	private String token;
	private String tokenCloud;
	private String sexo;
	private boolean registrado;
	private boolean conectado;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaConexion;
	
	@OneToOne(fetch = FetchType.LAZY)
	private Imagen fotoPerfil;

	@ManyToMany(fetch = FetchType.LAZY)
	private Collection<Usuario> contactos;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name="usuario_solicitudesenviadas")
	private Collection<SolicitudContacto> solicitudesEnviadas;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name="usuario_solicitudesrecibidas")
	private Collection<SolicitudContacto> solicitudesRecibidas;
	
	@ManyToMany(fetch = FetchType.LAZY)
	private Collection<Categoria> categorias;
	
	@ManyToMany(fetch = FetchType.LAZY)
	private Collection <TipoNotificacion> notificaciones;
	
	@OneToMany(fetch = FetchType.LAZY)
	private Collection <CheckIn> checksIn;
	
	@OneToMany(fetch = FetchType.LAZY)
	private Collection <Compra> compras;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="usuario_notificacionesenviadasvistas")
	private Collection <Notificacion> notificacionesEnviadasVistas;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="usuario_notificacionesenviadasnovistas")
	private Collection <Notificacion> notificacionesEnviadasNoVistas;
	
	public Usuario() {
	}

	public Usuario(String mail, String token,String tokenCloud, boolean registrado) {
		
		this.mail = mail;
		this.tokenCloud = tokenCloud;
		this.token = token;
		this.registrado = registrado;
		this.conectado = true;
		this.fechaConexion = new Date();
		solicitudesEnviadas = new ArrayList<SolicitudContacto>();
		solicitudesRecibidas = new ArrayList<SolicitudContacto>();
		contactos = new ArrayList<Usuario>();
		checksIn = new ArrayList<CheckIn>();
	}

	
	public String getTokenCloud() {
		return tokenCloud;
	}

	public void setTokenCloud(String tokenCloud) {
		this.tokenCloud = tokenCloud;
	}

	

	public Date getFechaConexion() {
		return fechaConexion;
	}

	public void setFechaConexion(Date fechaConexion) {
		this.fechaConexion = fechaConexion;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isConectado() {
		return conectado;
	}

	public void setConectado(boolean conectado) {
		this.conectado = conectado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public void agregarCheckIn(CheckIn c) {
		this.checksIn.add(c);
	}

	public void agregarContacto(Usuario u) {
		this.contactos.add(u);
	}
	
	public void agregarCompra(Compra c) {
		this.compras.add(c);
	}

	public void agregarSolicitudEnviada(SolicitudContacto s) {
		this.solicitudesEnviadas.add(s);
	}
	
	public void agregarSolicitudRecibida(SolicitudContacto s) {
		this.solicitudesRecibidas.add(s);
	}
	
	public void agregarNotificacionVista(Notificacion n) {
		this.notificacionesEnviadasVistas.add(n);
	}
	
	public void agregarNotificacionNoVista(Notificacion n) {
		this.notificacionesEnviadasNoVistas.add(n);
	}
	
	public void removerNotificacionVista(Notificacion n) {
		this.notificacionesEnviadasVistas.remove(n);
	}
	
	public void removerNotificacionNoVista(Notificacion n) {
		this.notificacionesEnviadasNoVistas.remove(n);
	}
	
	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Collection<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(Collection<Categoria> categorias) {
		this.categorias = categorias;
	}

	public Collection<TipoNotificacion> getNotificaciones() {
		return notificaciones;
	}

	public void setNotificaciones(Collection<TipoNotificacion> notificaciones) {
		this.notificaciones = notificaciones;
	}




	public Collection<Notificacion> getNotificacionesEnviadasVistas() {
		return notificacionesEnviadasVistas;
	}



	public void setNotificacionesEnviadasVistas(
			Collection<Notificacion> notificacionesEnviadasVistas) {
		this.notificacionesEnviadasVistas = notificacionesEnviadasVistas;
	}



	public Collection<Notificacion> getNotificacionesEnviadasNoVistas() {
		return notificacionesEnviadasNoVistas;
	}



	public void setNotificacionesEnviadasNoVistas(
			Collection<Notificacion> notificacionesEnviadasNoVistas) {
		this.notificacionesEnviadasNoVistas = notificacionesEnviadasNoVistas;
	}



	public boolean isRegistrado() {
		return registrado;
	}

	public void setRegistrado(boolean registrado) {
		this.registrado = registrado;
	}
	

	public Collection<Usuario> getContactos() {
		return contactos;
	}

	public void setContactos(Collection<Usuario> contactos) {
		this.contactos = contactos;
	}



	public Collection<CheckIn> getChecksIn() {
		return checksIn;
	}

	public void setChecksIn(Collection<CheckIn> checksIn) {
		this.checksIn = checksIn;
	}

	public Collection<SolicitudContacto> getSolicitudesEnviadas() {
		return solicitudesEnviadas;
	}



	public void setSolicitudesEnviadas(
			Collection<SolicitudContacto> solicitudesEnviadas) {
		this.solicitudesEnviadas = solicitudesEnviadas;
	}



	public Collection<SolicitudContacto> getSolicitudesRecibidas() {
		return solicitudesRecibidas;
	}



	public void setSolicitudesRecibidas(
			Collection<SolicitudContacto> solicitudesRecibidas) {
		this.solicitudesRecibidas = solicitudesRecibidas;
	}

	

	public Imagen getFotoPerfil() {
		return fotoPerfil;
	}



	public void setFotoPerfil(Imagen fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}



	public Collection<Compra> getCompras() {
		return compras;
	}

	public void setCompras(Collection<Compra> compras) {
		this.compras = compras;
	}

	public JSONObject aJSON() {

		JSONObject json = new JSONObject();
		try {
			if (mail != null)
				json.put("mail", this.mail);
			else
				json.put("mail", "");

			if (nombre != null)
				json.put("nombre", this.nombre);
			else
				json.put("nombre", "");

			if (sexo != null)
				json.put("sexo", this.sexo);
			else
				json.put("sexo", "");

			JSONArray jcheckIns = new JSONArray();
			if (checksIn != null)
				for (CheckIn c : this.checksIn) {
					if (c != null)
						jcheckIns.put(c.aJSON());
				}
			json.put("checkins", jcheckIns);
			
			if (fotoPerfil!=null)
				json.put("foto", this.fotoPerfil.aJSON());
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
		
	}

}

