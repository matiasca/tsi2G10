package pojos;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("Empresa")
public class AdministradorEmpresa extends Administrador {
	
	private static final long serialVersionUID = 1L;
	
	@OneToOne
	private Empresa empresa;
	
	
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public AdministradorEmpresa() {
		// TODO Auto-generated constructor stub
	}
	
	public AdministradorEmpresa(String id, String contrasenia){
		
		super(id, contrasenia);
		
	}
}
