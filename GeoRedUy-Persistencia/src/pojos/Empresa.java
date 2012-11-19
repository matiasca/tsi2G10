package pojos;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.codehaus.jettison.json.JSONObject;

import otros.Imagen;

@Entity
public class Empresa implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	private String nombre;
	
	@OneToMany
	private Collection<LocalEmp> locales;
	
	
	@Column(length = 10000)
	private String descripcion;
	
	@OneToOne
	private Imagen foto;
	
	
	
	public Imagen getFoto() {
		return foto;
	}

	public void setFoto(Imagen foto) {
		this.foto = foto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	public Empresa(String nombre) {
		
		this.nombre = nombre;
		this.locales= new ArrayList<LocalEmp>();
	}
	
	public Empresa() {
		
		this.locales= new ArrayList<LocalEmp>();
	}

	public Collection<LocalEmp> getLocales() {
		return locales;
	}

	public void setLocales(Collection<LocalEmp> locales) {
		this.locales = locales;
	}

	
	
}
