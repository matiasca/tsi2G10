package pojos;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("Evento")
public class NotificacionEvento extends Notificacion {

	@OneToOne
	private Evento evento;

	public NotificacionEvento() {
	}
	
	public NotificacionEvento(Evento evento,String titulo, String descripcion) {
		super(titulo, descripcion);
		this.evento = evento;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	
	
}
