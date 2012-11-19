/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package otros;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EnviarMail {

	static final String HOST = "smtp.gmail.com";
	static final String USER = "georeduy2012@gmail.com";
	static final String PASSWORD = "georeduy2012";
	static final String AUTH = "true";
	static final String PORT = "587";

	public EnviarMail() {
	}

	public void enviarMailInvitacion(String mail, String nombreEmisor) {

		String Asunto = "Invitación a GeoRed-Uy";

		// seteo param de archivo de propiedades
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.setProperty("mail.smtp.host", HOST);
		props.setProperty("mail.smtp.user", USER);
		props.setProperty("mail.smtp.user", PASSWORD);
		props.setProperty("mail.smtp.auth", AUTH);
		props.setProperty("mail.smtp.port", PORT);

		// obtenemos la sesion de mail
		Session session = Session.getDefaultInstance(props);
		//session.setDebug(true);

		// comenzamos a setear mensaje
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(USER));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					mail));// destinatario

			message.setSubject(Asunto);
			message.setText(
					"<b>Hola,"
							+ "<br>"+ "<br>"
							+ nombreEmisor
							+ " ya usa GeoRedUy y quiere que vos tambien seas parte!"
							+ "<br>"
							+ "Registrate y busca a tus amigos en GeoRedUy, "
							+ "la red social de los uruguayos."+ "<br>"+ "<br>"
							+ "Mucha suerte." + "<br>"
							+ "Te esperamos!", "UTF-8", "html");

			// configuramos y enviamos mensaje
			Transport t = session.getTransport("smtp");
			t.connect(USER, PASSWORD);
			t.sendMessage(message, message.getAllRecipients());
			t.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void enviarMailAdminEmpresa(String mail, String contrasenia) {

		String Asunto = "Bienvenido a GeoRed-Uy";

		// seteo param de archivo de propiedades
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.setProperty("mail.smtp.host", HOST);
		props.setProperty("mail.smtp.user", USER);
		props.setProperty("mail.smtp.user", PASSWORD);
		props.setProperty("mail.smtp.auth", AUTH);
		props.setProperty("mail.smtp.port", PORT);

		// obtenemos la sesion de mail
		Session session = Session.getDefaultInstance(props);
		//session.setDebug(true);

		// comenzamos a setear mensaje
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(USER));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					mail));// destinatario

			message.setSubject(Asunto);
			message.setText(
					"<b>Hola,"
							+ "<br>"
							+ " bienvenido a GeoRedUy!"
							+ "<br>"
							+ "Su contraseña es: "
							+ contrasenia + "<br>"+ "<br>"
							+ "Ingrese a nuestro sitio y comience a gestionar su empresa."+ "<br>"
							+ "Mucha suerte!" + "<br>", "UTF-8", "html");

			// configuramos y enviamos mensaje
			Transport t = session.getTransport("smtp");
			t.connect(USER, PASSWORD);
			t.sendMessage(message, message.getAllRecipients());
			t.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
