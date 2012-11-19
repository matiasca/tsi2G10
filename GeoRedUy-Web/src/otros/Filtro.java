package otros;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.security.annotation.Authorization;

import beans.SesionBean;


@WebFilter("/faces/*")
public class Filtro implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {    
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        SesionBean auth = (SesionBean) req.getSession().getAttribute("sesionBean");
        String path = req.getRequestURI().substring(req.getContextPath().length());
        
        if ((auth != null && auth.isLogueado())||path.equals("/") || path.equals("/faces/login.xhtml") || path.equals("/faces/login.xhtml")
        		|| path.equals("/faces/javax.faces.resource/primefaces.js") ||
        		path.equals("/faces/javax.faces.resource/jquery/jquery.js")
        		||
        		path.equals("/faces/javax.faces.resource/theme.css")
        		||
        		path.contains("/faces/javax.faces.resource/")
        		
        		
        		) {
        	
        	if((path.equals("/faces/login.xhtml")||path.equals("/"))&& (auth != null && auth.isLogueado()))
        		if(auth.getTipoUsuario().equals("Aplicacion"))
        			res.sendRedirect(req.getContextPath() + "/faces/homeAdmin.xhtml");
        		else 
        			res.sendRedirect(req.getContextPath() + "/faces/homeAdminEmpresa.xhtml");
            chain.doFilter(request, response);
        } else {
        	res.sendRedirect(req.getContextPath() + "/faces/login.xhtml");
        	 
        }
    }

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

    // You need to override init() and destroy() as well, but they can be kept empty.
}