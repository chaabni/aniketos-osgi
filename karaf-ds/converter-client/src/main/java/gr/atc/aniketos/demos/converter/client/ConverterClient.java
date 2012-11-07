package gr.atc.aniketos.demos.converter.client;

import org.osgi.service.component.ComponentContext;

import gr.atc.aniketos.demos.converter.Converter;

public class ConverterClient {

	private ConverterDialog converterDialog;

	private Converter service;

    protected void activate(ComponentContext context) {
    	System.out.println("*** Activating Client");
    } 

    protected void deactivate(ComponentContext context) {
    	System.out.println("*** Deactivating Client");
        
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
        System.out.println("Converter Service was set. !");
        this.service = _service;
        
    	converterDialog = new ConverterDialog(new ConverterDialog.ConverterDialogListener() {
			@Override
			public double onCelciusToFahrenheit(double celcius) {
                double fahrenheit = service.toFahrenheit(celcius);
                System.out.println("Celcius " + celcius + " => Fahrenheit " + fahrenheit);
                return fahrenheit;
			}
            
			@Override
			public double onFahrenheitToCelcius(double fahrenheit) {
                double celcius = service.toCelcius(fahrenheit);
                System.out.println("Fahrenheit " + fahrenheit + " => Celcius " + celcius);
                return celcius;
			}            
		});
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                converterDialog.setVisible(true);
            }
        });        
    }

    /**
     * Unsets the Marketplace service.
     * @param service The Marketplace service
     */
    public synchronized void unsetService(Converter service) {
        System.out.println("Converter Service was unset.");
        if (this.service == service) {
            this.service = null;
        }
    }    


}
