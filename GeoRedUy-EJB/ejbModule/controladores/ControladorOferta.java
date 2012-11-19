package controladores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.codehaus.jettison.json.JSONArray;

import daos.CategoriaDAO;
import daos.CompraDAO;
import daos.ImagenDAO;
import daos.LocalEmpDAO;
import daos.NotificacionDAO;
import daos.OfertaDAO;
import daos.UsuarioDAO;

import otros.Categoria;
import otros.Imagen;
import pojos.Compra;
import pojos.LocalEmp;
import pojos.NotificacionOferta;
import pojos.Oferta;
import pojos.Usuario;


@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ControladorOferta implements IControladorOferta {

	@EJB
	private ImagenDAO imgDAO;
	@EJB
	private LocalEmpDAO localDAO;
	@EJB
	private OfertaDAO ofertaDAO;
	@EJB
	private CategoriaDAO categoriaDAO;
	@EJB
	private NotificacionDAO notificacionDAO;
	@EJB
	private UsuarioDAO usuarioDAO;
	@EJB
	private CompraDAO compraDAO;

	public boolean crearOferta(String nombreEmpresa, String nombre,
			String descripcion, Date fechaInicio, Date fechaFin, byte[] file,
			String fileName, Double costo, Collection<String> tiposCategorias,
			int local) {

		Collection<Categoria> categorias = new ArrayList<Categoria>();
		if (tiposCategorias != null)
			for (Iterator<String> it = tiposCategorias.iterator(); it.hasNext();) {
				String nombreCat = (String) it.next();
				Categoria cat = categoriaDAO.obtenerCategoria(nombreCat);
				categorias.add(cat);
			}

		Imagen img = new Imagen(file, fileName);
		imgDAO.insertar(img);
		LocalEmp loc = localDAO.obtenerLocal(local);
		Oferta of = new Oferta(nombre, descripcion, fechaInicio, fechaFin, img,
				costo, categorias, loc);
		ofertaDAO.crear(of);

		NotificacionOferta notificacion = new NotificacionOferta(of,
				"Nueva oferta", "Hay una nueva oferta esperandote en "
						+ loc.getNombre() + "!");
		notificacionDAO.insertar(notificacion);

		return true;

	}

	public boolean comprarOferta(String mail,int idOferta){
		
		Usuario u = usuarioDAO.obtenerUsuario(mail);
		if (u != null){
			Compra compra = new Compra(new Date());
			compraDAO.insertar(compra,idOferta);
			ofertaDAO.comprarOferta(idOferta,compra);
			usuarioDAO.insertarCompra(mail, compra);
			return true;
		}else
			return false;
		
		
	}
	
	public boolean usuarioComproOferta(String mail,int idOferta){
		return usuarioDAO.usuarioComproOferta(mail, idOferta);
	}
	
	public boolean evaluarOferta(int idOferta,int evaluacion){
		return ofertaDAO.evaluarOferta(idOferta, evaluacion);
	}
	
	public JSONArray reporteOfertas(Date fechaInicio, Date fechaFin){
		
		return ofertaDAO.reporteOfertas(fechaInicio, fechaFin);

	}
}
