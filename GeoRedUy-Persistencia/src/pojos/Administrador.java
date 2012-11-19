package pojos;

import java.io.Serializable;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="Tipo", discriminatorType=DiscriminatorType.STRING)

public abstract class Administrador implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id	
	private String mail;
	
	private String contrasenia;
	
	public Administrador() {}
	public Administrador(String id, String contrasenia) {
		
		this.mail = id;
		this.contrasenia = contrasenia;
	}
	public String getId() {
		return mail;
	}
	public void setId(String id) {
		this.mail = id;
	}
	public String getContrasenia() {
		return contrasenia;
	}
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	
}
