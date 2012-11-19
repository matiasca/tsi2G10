package otros;

import java.util.Timer;
import java.util.TimerTask;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import controladores.IControladorActualizador;
import controladores.IControladorNotificacion;
import controladores.IControladorUsuario;

public class ActualizadorNotificaciones {

	private static Timer timer;
	
	public ActualizadorNotificaciones() {
		if (timer == null)
			timer = new Timer(true);

		timer.schedule(new Actualizador(), 1000, 5 * 60 * 1000); //5 minutos

	}

	class Actualizador extends TimerTask {

		public Actualizador() {
			System.out.println("Se creo el Actualizador");
		}

		public void run() {

			System.out.println("Actualizador");
			
			InitialContext ctx;

			try {
				
				ctx = new InitialContext();
				IControladorActualizador icactualizador = (IControladorActualizador) ctx
						.lookup("java:global/GeoRedUy-EAR/GeoRedUy-EJB/ControladorActualizador!controladores.IControladorActualizador");
			
				icactualizador.actualizarNotificaciones();
				
				icactualizador.verificarConexion();

			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
