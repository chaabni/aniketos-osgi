package gr.atc.aniketos.demos.converter.impl;

import org.osgi.service.component.ComponentContext;
import gr.atc.aniketos.demos.converter.Converter;

public class ConverterImpl implements Converter {
    
    @Override
    public double toCelcius(double fahrenheit) {
        double celcius = ((fahrenheit - 32) * 5) / 9;
        System.out.println("Fahrenheit " + fahrenheit + " => Celcius " + celcius);
        return celcius;
    }
    
    @Override
    public double toFahrenheit(double celcius) {
        double fahrenheit = 32 + ((celcius*9)/5);
        System.out.println("Celcius " + celcius + " => Fahrenheit " + fahrenheit);
        return fahrenheit;
    }
    
    protected void activate(ComponentContext context) {
    	System.out.println("*** Activating Service");
    } 

    protected void deactivate(ComponentContext context) {
    	System.out.println("*** Deactivating Service");
    }     
}