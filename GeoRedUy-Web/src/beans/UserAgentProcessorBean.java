/**
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 **/
package beans;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import otros.UAgentInfo;

/**
 * @author jbalunas@redhat.com
 * @author <a href="http://community.jboss.org/people/bleathem">Brian
 *         Leathem</a>
 * 
 */
@ManagedBean(name = "userAgent")
@ViewScoped
public class UserAgentProcessorBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private UAgentInfo uAgentInfo;

	@PostConstruct
	public void init() {
		ExternalContext externalContext = FacesContext.getCurrentInstance()
				.getExternalContext();
		String userAgentStr = externalContext.getRequestHeaderMap().get(
				"user-agent");
		String httpAccept = externalContext.getRequestHeaderMap().get("Accept");
		uAgentInfo = new UAgentInfo(userAgentStr, httpAccept);
		System.out.println("---------> UAgentInfo Init");
	}

	public boolean isPhone() {
		// Detects a whole tier of phones that support similar functionality as
		// the iphone
		return uAgentInfo.detectTierIphone();
	}

	public boolean isTablet() {
		// Will detect iPads, Xooms, Blackberry tablets, but not Galaxy - they
		// use a strange user-agent
		return uAgentInfo.detectTierTablet();
	}

	public boolean isMobile() {
		return isPhone() || isTablet();
	}

	public void redirect(SelectEvent event) {

		try {
			if (isMobile()) {
				// redirecciono a base de movil
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("/faces/movilWebapp/login.xhtml");
			} else {
				// redirecciono a base web
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("/faces/login.xhtml");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void redir() {
		try {
			String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
			System.out.println("Context path: "+contextPath);
			if (isMobile()) {
				// redirecciono a base de movil
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect(contextPath+"/faces/movilWebapp/login.xhtml");
			} else {
				// redirecciono a base web
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect(contextPath+"/faces/login.xhtml");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}