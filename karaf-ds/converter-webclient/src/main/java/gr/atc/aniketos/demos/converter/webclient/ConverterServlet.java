package gr.atc.aniketos.demos.converter.webclient;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gr.atc.aniketos.demos.converter.Converter;

public class ConverterServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private Converter service;
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
	
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		
		PrintWriter writer = resp.getWriter();
			
        boolean success = false;
        String error = "";
        double result = 0;
            
		if (service == null) {
			error = "Internal error!";
		}
		else {			
			String val = req.getParameter("val");
			String conversion = req.getParameter("conversion");
	
            if (val == null || conversion == null || 
                    (!conversion.equals("celcius") && !conversion.equals("fahrenheit"))) {
                error = "Not enough data";
            }
            else {
    
                try {
                    result = Double.parseDouble(val);
                    success = true;
                } catch (NumberFormatException ex) {
                    error = ex.getMessage();
                }
                
                if (success) {
                    if (conversion.equals("celcius")) {
                        result = service.toFahrenheit(result);
                    }
                    else {
                        result = service.toCelcius(result);
                    }
                }
            }
		}        
        String json = String.format("{\"success\":%s,\"result\":\"%.02f\",\"error\":\"%s\"}",
                     success ? "true" : "false",
                     result, error.replace('"', '\''));
        
        writer.println(json);					
		writer.close();
	}
	  
    public synchronized void setConverterService(Converter _service) {
        System.out.println("Converter Service was set. !");
        this.service = _service;        
    }

    public synchronized void unsetConverterService(Converter service) {
        System.out.println("Converter Service was unset.");
        if (this.service == service) {
            this.service = null;
        }
    } 	  
}

