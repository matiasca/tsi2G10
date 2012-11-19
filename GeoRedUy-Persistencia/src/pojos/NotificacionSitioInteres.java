package pojos;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("SitioInteres")
public class NotificacionSitioInteres extends Notificacion {

	@OneToOne
	private SitioInteres sitio;

	public NotificacionSitioInteres(){}
	
	public NotificacionSitioInteres(SitioInteres sitio, String titulo, String descripcion) {
		super(titulo, descripcion);
		this.sitio = sitio;
	}

	public SitioInteres getSitio() {
		return sitio;
	}

	public void setSitio(SitioInteres sitio) {
		this.sitio = sitio;
	}
	
	
}
