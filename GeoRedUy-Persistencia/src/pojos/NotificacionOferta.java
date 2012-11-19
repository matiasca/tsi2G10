package pojos;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("Oferta")
public class NotificacionOferta extends Notificacion {
	
	@OneToOne
	private Oferta oferta;

	
	public NotificacionOferta() {
	}
	
	public NotificacionOferta(Oferta oferta, String titulo, String descripcion) {
		super(titulo, descripcion);
		this.oferta = oferta;
	}

	public Oferta getOferta() {
		return oferta;
	}

	public void setOferta(Oferta oferta) {
		this.oferta = oferta;
	}

	
}
