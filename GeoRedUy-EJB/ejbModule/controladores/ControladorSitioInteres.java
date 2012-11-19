package controladores;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import otros.Categoria;
import otros.Imagen;

import pojos.CheckIn;

import pojos.Evento;

import pojos.NotificacionCheckIn;

import pojos.Comentario;
import pojos.NotificacionEvento;
import pojos.NotificacionSitioInteres;
import pojos.SitioInteres;
import pojos.Usuario;

import daos.CategoriaDAO;
import daos.CheckInDAO;
import daos.ComentarioDAO;
import daos.EventoDAO;
import daos.ImagenDAO;
import daos.NotificacionDAO;
import daos.SitioInteresDAO;
import daos.UsuarioDAO;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ControladorSitioInteres implements IControladorSitioInteres {

	@EJB
	private SitioInteresDAO sitioDAO;
	@EJB
	private UsuarioDAO usuarioDAO;
	@EJB
	private CategoriaDAO categoriaDAO;
	@EJB
	private CheckInDAO checkInDAO;
	@EJB
	private NotificacionDAO notificacionDAO;
	@EJB
	private ImagenDAO imagenDAO;
	@EJB
	private EventoDAO eventoDAO;
	@EJB
	private ComentarioDAO comentarioDAO;

	public boolean altaSitioInteres(String nombre, String direccion,
			String descripcion, int latitud, int longitud, byte[] logo,
			String nombreLogo, Map<String, byte[]> imagenesEnBytes,
			Collection<String> tiposCategorias) {

		SitioInteres s = sitioDAO.obtenerSitioInteres(nombre);

		if (s != null)// ya existe otro sitio con ese nombre
			return false;

		else {
			Collection<Imagen> imagenes = new ArrayList<Imagen>();
			if (imagenesEnBytes != null)
				for (Iterator<String> it = imagenesEnBytes.keySet().iterator(); it
						.hasNext();) {

					String nombreImagen = (String) it.next();
					byte[] im = imagenesEnBytes.get(nombreImagen);
					Imagen imagen = new Imagen(im, nombreImagen);
					imagenDAO.insertar(imagen);
					imagenes.add(imagen);

				}

			Collection<Categoria> categorias = new ArrayList<Categoria>();
			if (tiposCategorias != null)
				for (Iterator<String> it = tiposCategorias.iterator(); it
						.hasNext();) {
					String nombreCat = (String) it.next();
					Categoria cat = categoriaDAO.obtenerCategoria(nombreCat);
					categorias.add(cat);
				}

			Imagen logoImagen = new Imagen(logo, nombreLogo);
			imagenDAO.insertar(logoImagen);

			s = new SitioInteres(nombre, direccion, descripcion, latitud,
					longitud, logoImagen, imagenes, categorias);

			sitioDAO.insertar(s);

			NotificacionSitioInteres notificacion = new NotificacionSitioInteres(
					s, "Nuevo sitio de interes", "Te recomendamos visitar "
							+ nombre + "!");

			notificacionDAO.insertar(notificacion);
			
			return true;
		}
	}

	public double distancia(int a1, int a2, int b1, int b2) {
		double dist = 0;
		dist = Math.sqrt(((a1 - b1) * (a1 - b1)) + ((a2 - b2) * (a2 - b2)));
		return dist / 12;
	}

	public JSONArray listarSitiosInteresCercanos(int latitud, int longitud) {

		return sitioDAO.listarSitiosInteresCercanos(latitud, longitud);

	}

	public Collection<Categoria> listarCategorias() {

		return categoriaDAO.listarCategorias();

	}

	public boolean altaCheckIn(String comentario, String mail, String token,
			String nombreSitio, byte[] foto) {

		Usuario u = usuarioDAO.obtenerUsuario(mail);
		SitioInteres s = sitioDAO.obtenerSitioInteres(nombreSitio);
		if (s != null) {
			Imagen i = null;
			if (foto != null) {
				i = new Imagen(foto, "");
				imagenDAO.insertar(i);

			}
			
			
//			String coment = comentario;
//			try {
//				coment = new String(comentario.getBytes("ISO-8859-1"),"UTF-8");
//				System.out.println("coment: "+coment);
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			System.out.println(coment);
			CheckIn c = new CheckIn(s, u, comentario, i);
			checkInDAO.insertar(c);
			sitioDAO.agregarCheckInSitioInteres(c, s);
			usuarioDAO.agregarCheckInUsuario(c, u);

			NotificacionCheckIn notificacion = new NotificacionCheckIn(c,
					"Nuevo check-in", u.getNombre() + " esta en " + nombreSitio
							+ "!");
			notificacionDAO.insertar(notificacion);
			
			return true;
		} else {
			return false;
		}

	}

	public void agregarComentarioCheckIn(String mail, int idCheckIn,
			String comentario) {

		Usuario u = usuarioDAO.obtenerUsuario(mail);
		Comentario c = new Comentario(comentario, new Date(), u);
		comentarioDAO.insertar(c);
		checkInDAO.insertarComentario(c, idCheckIn);

	}

	public JSONArray listarUltimosCkeckInsAmigos(String mail, int principio,
			int fin) {

		return checkInDAO.listarUltimosCkeckInsAmigos(mail, principio, fin);

	}

	public List<SitioInteres> listarSitiosInteres(String nombreBusqueda) {

		return sitioDAO.listar(nombreBusqueda);
	}

	public int cantidadListarSitiosInteres(String nombreBusqueda) {

		return sitioDAO.cantidadAListar(nombreBusqueda);
	}

	public int crearEvento(String nombre, String direccion, String descripcion,
			Date fechaInicio, Date fechaFin, int latitud, int longitud,
			byte[] imagen, String nombreImagen,
			Collection<String> tiposCategorias, String nombreSitio) {

		if (!nombreSitio.isEmpty()) {

			SitioInteres sitio = sitioDAO.obtenerSitioInteres(nombreSitio);

			Collection<Categoria> categorias = new ArrayList<Categoria>();
			if (tiposCategorias != null)
				for (Iterator<String> it = tiposCategorias.iterator(); it
						.hasNext();) {
					String nombreCat = (String) it.next();
					Categoria cat = categoriaDAO.obtenerCategoria(nombreCat);
					categorias.add(cat);
				}
			Imagen img = null;
			if (imagen != null) {
				img = new Imagen(imagen, nombreImagen);
				imagenDAO.insertar(img);
			}

			Evento ev = new Evento(nombre, descripcion, fechaInicio, fechaFin,
					direccion, latitud, longitud, img, categorias, sitio);
			eventoDAO.crearEvento(ev);
			NotificacionEvento notificacion = new NotificacionEvento(ev,
					"Nuevo evento", nombre);

			notificacionDAO.insertar(notificacion);

		}else{


			Collection<Categoria> categorias = new ArrayList<Categoria>();
			if (tiposCategorias != null)
				for (Iterator<String> it = tiposCategorias.iterator(); it
						.hasNext();) {
					String nombreCat = (String) it.next();
					Categoria cat = categoriaDAO.obtenerCategoria(nombreCat);
					categorias.add(cat);
				}

			Imagen img = null;
			if (imagen != null) {
				img = new Imagen(imagen, nombreImagen);
				imagenDAO.insertar(img);
			}

			Evento ev = new Evento(nombre, descripcion, fechaInicio, fechaFin,
					direccion, latitud, longitud, img, categorias, null);
			eventoDAO.crearEvento(ev);
			NotificacionEvento notificacion = new NotificacionEvento(ev,
					"Nuevo evento", nombre);

			notificacionDAO.insertar(notificacion);
	
		}
		return 1;
	}

	public JSONArray cantidadCheckInsPorSitio(Date fechaInicio, Date fechaFin) {

		return sitioDAO.cantidadCheckInsPorSitio(fechaInicio, fechaFin);
	}

	public JSONObject verInfoCheckIn(int idCheckIn) {
		return checkInDAO.obtenerCheckIn(idCheckIn);
	}

	public JSONObject verInfoSitioInteres(String nombre) {
		return sitioDAO.obtenerInfoSitioInteres(nombre);
	}
}
