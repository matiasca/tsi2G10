package pojos;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("CheckIn")
public class NotificacionCheckIn extends Notificacion {

	@OneToOne
	private CheckIn checkin;
	
	public NotificacionCheckIn(){
	}
	
	public NotificacionCheckIn(CheckIn checkin,String titulo, String descripcion) {
		super(titulo, descripcion);
		this.checkin = checkin;
	}

	public CheckIn getCheckin() {
		return checkin;
	}

	public void setCheckin(CheckIn checkin) {
		this.checkin = checkin;
	}


	
	
}
