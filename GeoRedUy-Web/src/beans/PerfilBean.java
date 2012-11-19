package beans;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controladores.IControladorAdministrador;
import controladores.IControladorEmpresa;



@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class PerfilBean implements Serializable {

	
	private String mailNuevo;

	
	private String contrasena = "";
	
	@ManagedProperty(value="#{sesionBean}")
	private SesionBean sesion;

	@EJB
	private IControladorAdministrador icAdmin;

	

	public String getMailNuevo() {
		return mailNuevo;
	}


	public void setMailNuevo(String mailNuevo) {
		this.mailNuevo = mailNuevo;
	}


	public void setSesion(SesionBean sesion) {
		this.sesion = sesion;
	}


	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		
		if (!contrasena.equals("")) {
			MessageDigest md = null;
			try {
				md = MessageDigest.getInstance("SHA-256");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			md.update(contrasena.getBytes());

			// convert the byte to hex format method 1
			byte byteData[] = md.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			this.contrasena = sb.toString();
		}else{
			this.contrasena = "";		
		
		}
	}

	

	public String cambiarContrasenia() {
			System.out.println("Cambiar contraseña");
			System.out.println(sesion.getMail());
			System.out.println(mailNuevo+"---"+contrasena);
		icAdmin.cambiarContraseniaMail(sesion.getMail(), mailNuevo, contrasena);
			
			String mensaje = "Contraseña modificada.";
			FacesMessage msg = new FacesMessage(mensaje, "");
			FacesContext.getCurrentInstance().addMessage(null, msg);

		return "homeAdminEmpresa";
	}

	public SesionBean getSesion() {
		return sesion;
	}

}
