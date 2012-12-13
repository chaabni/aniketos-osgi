package gr.atc.aniketos.demos.converter.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.osgi.service.component.ComponentContext;

import gr.atc.aniketos.demos.converter.Converter;

public class ConverterClient {

    private static Logger logger = LoggerFactory.getLogger(ConverterClient.class);

	private ConverterDialog converterDialog;

	private Converter service;

    protected void activate(ComponentContext context) {
    	logger.debug("*** Activating Client");
    } 

    protected void deactivate(ComponentContext context) {
    	logger.debug("*** Deactivating Client");
        
		service = null;
		if (converterDialog != null) {
	        java.awt.EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                converterDialog.setVisible(false);
	                converterDialog.dispose();
	                converterDialog = null;
	            }
	        });
		}        
    }  
    
    public synchronized void setService(Converter _service) {
        logger.debug("Converter Service was set. !");
        this.service = _service;
        
    	converterDialog = new ConverterDialog(new ConverterDialog.ConverterDialogListener() {
			@Override
			public double onCelciusToFahrenheit(double celcius) {
                double fahrenheit = service.toFahrenheit(celcius);
                logger.debug("Celcius {} => Fahrenheit {}",  celcius, fahrenheit);
                return fahrenheit;
			}
            
			@Override
			public double onFahrenheitToCelcius(double fahrenheit) {
                double celcius = service.toCelcius(fahrenheit);
                logger.debug("Fahrenheit {} => Celcius {}", fahrenheit, celcius);
                return celcius;
			}            
		});
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                converterDialog.setVisible(true);
            }
        });        
    }

    public synchronized void unsetService(Converter service) {
        logger.debug("Converter Service was unset.");
        if (this.service == service) {
            this.service = null;
        }
    } 
}
