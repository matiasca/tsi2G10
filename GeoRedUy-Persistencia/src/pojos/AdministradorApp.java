package pojos;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("Aplicacion")
public class AdministradorApp extends Administrador {

	private static final long serialVersionUID = 1L;

	public AdministradorApp() {
		// TODO Auto-generated constructor stub
	}

	public AdministradorApp(String id, String contrasenia) {
		super(id, contrasenia);
		// TODO Auto-generated constructor stub
	}

}
