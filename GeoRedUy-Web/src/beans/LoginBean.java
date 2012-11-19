package beans;



import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONObject;

import pojos.Administrador;
import pojos.AdministradorApp;
import pojos.AdministradorEmpresa;





import controladores.IControladorAdministrador;


public class LoginBean {

	private String usuario;
	private String contrasenia;
	private SesionBean sesion;
	
	public void setSesion(SesionBean sesion) {
		this.sesion = sesion;
	}
	
	@EJB
	private IControladorAdministrador icadmin;

	

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        md.update(contrasenia.getBytes());
        
      //convert the byte to hex format method 1
        byte byteData[] = md.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
       
		
		
		this.contrasenia = sb.toString();
		
	}


	public String iniciarSesion() {
		Administrador ad = icadmin.loguearAdmin(usuario, contrasenia);

		

		

		//(ad instanceof AdministradorApp)
		if (ad instanceof AdministradorEmpresa){
			System.out.println("empreasa");
			sesion.setLogueado(true);
			sesion.setMail(ad.getId());
			String nombreempresa =((AdministradorEmpresa) ad).getEmpresa().getNombre();
			sesion.setNombreEmpresa(nombreempresa);
			sesion.setTipoUsuario("Empresa");
			return "homeAdmin";
		}else if(ad instanceof AdministradorApp){
			sesion.setLogueado(true);
			sesion.setMail(ad.getId());
			System.out.println("app");
			sesion.setTipoUsuario("Aplicacion");
			return "homeAdminApp";
		}else{
			String mensaje = "Contraseña Inválida";
			FacesMessage msg = new FacesMessage(mensaje, "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "Mal";
		}
			
	}
	
	public String cerrarSesion() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		
	
		context.getExternalContext().getSessionMap().remove("#{sesionBean}"); 
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
			    .getExternalContext().getSession(true);
			    session.removeAttribute("sesionBean");
			    
			    return "cerrarSesion";
		
	}
	
	/**
	 * Redirige, en caso de estar logueado al home de usuairo o tecnico dependiendo del tipo de usuario
	 */
	public void redireccionWeb() {
		System.out.println("Entro-> ");
		try {
			//obtengo la sesion
			HttpSession session = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext().getSession(true);
			SesionBean sesionBean = (SesionBean) session
					.getAttribute("sesionBean");
			
			if (sesionBean != null && sesionBean.isLogueado()) {
				//obtengo parametros del request
				ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
				String contextPath = contexto.getRequestContextPath();
				
				//redirecciono en base a el tipo de usuario
				if(sesion.getTipoUsuario().equals("Empresa")){
					System.out.println("Entro-> Empresa");
					contexto.redirect(contextPath + "/faces/homeAdminEmpresa.xhtml");	
				}else{
					System.out.println("Entro-> Aplicacion");
					contexto.redirect(contextPath + "/faces/homeAdmin.xhtml");
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error al redirigir");
		}
	}
	
	/**
	 * Redirige, en caso de estar logueado al home de usuairo o tecnico dependiendo del tipo de usuario
	 */
	public void redireccionMovil() {
		try {
			//obtengo la sesion
			HttpSession session = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext().getSession(true);
			SesionBean sesionBean = (SesionBean) session
					.getAttribute("sesionBean");
			
			if (sesionBean != null && sesionBean.isLogueado()) {
				//obtengo parametros del request
				ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
				String contextPath = contexto.getRequestContextPath();
				
				//redirecciono en base a el tipo de usuario
				if(sesion.getTipoUsuario().equals("Empresa")){
					contexto.redirect(contextPath + "/faces/movil/homeAdminEmpresa.xhtml");	
				}else{
					contexto.redirect(contextPath + "/faces/movil/login.xhtml");
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error al redirigir");
		}
	}
	
}