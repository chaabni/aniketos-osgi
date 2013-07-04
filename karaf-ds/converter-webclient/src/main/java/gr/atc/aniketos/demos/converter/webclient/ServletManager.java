package gr.atc.aniketos.demos.converter.webclient;

import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

/**
 * Helper class that registers static content and the converter servlet in a HttpService.
 * It is defined as a component with immediate start.
 * @author Kostas Giannakakis
 *
 */
public class ServletManager {

	
	/** The instance of http service */
	private HttpService httpService;
	
	/** The Converter servlet */
	private Servlet servlet;
	
	/** The path (alias) of the servlet. It is read from the servlet's attributes */
	private String context;
	
	/**
	 * Sets the http service
	 * @param httpService The http service
	 */
	public void setHttpService(HttpService httpService) {
		System.out.println("Setting HTTP service");
		this.httpService = httpService;
		registerStaticContent();
		registerServlet();
	}
	
	/**
	 * Unsets the http service
	 * @param httpService The http service
	 */
	public void unsetHttpService(HttpService httpService) {
		if (this.httpService == httpService) {
			System.out.println("Unsetting service");
			unregisterStaticContent();
			this.httpService = null;
			unregisterServlet();
		}
	}
		
	@SuppressWarnings("rawtypes")
	/**
	 * Sets the Converter servlet
	 * @param servlet The Converter servlet
	 * @param attrs The attributes of the servlet. Contains the path parameter
	 */
	public void setServlet(Servlet servlet, Map attrs) {
		System.out.println("Setting Servlet");

		if (attrs != null && attrs.containsKey("Web-ContextPath")) {
			String ctx = (String) attrs.get("Web-ContextPath");
			System.out.println("Servlet path: " + ctx);
			
			this.servlet = servlet;
			this.context = ctx;
			registerServlet();
		}
	}
	
	/**
	 * Unsets the Converter servlet
	 * @param servlet The Converter servlet
	 */
	public void unsetServlet(Servlet servlet) {
		System.out.println("Unsetting servlet");
		unregisterServlet();
	}	
	
	/**
	 * Registers the static content of the web application
	 */
	private void registerStaticContent() {
		if (httpService != null) {
			try {
				httpService.registerResources( "/", "/html", null );
				httpService.registerResources( "/images", "/images", null );
			} catch (NamespaceException e) {
				System.out.println("Failed to register static content");
			}
		}
	}

	/**
	 * Unregisters the static content of the web application
	 */
	private void unregisterStaticContent() {
		if (httpService != null) {
			httpService.unregister("/");
			httpService.unregister("/images");
		}
	}
	
	/**
	 * Registers the servlet
	 */
	private void registerServlet() {
		if (httpService != null && servlet != null && context != null) {
			try {
				httpService.registerServlet(context, servlet, null, null);
			} catch (ServletException e) {
				System.out.println("Failed to registerServlet: " + e.getMessage());
			} catch (NamespaceException e) {
				System.out.println("Failed to registerServlet: " + e.getMessage());
			}		
		}
	}

	/**
	 * Unregisters the servlet
	 */
	private void unregisterServlet() {
		if (httpService != null && servlet != null && context != null) {
			httpService.unregister(context);
		}
	}	

}
