package gr.atc.aniketos.demos.converter.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.osgi.service.component.ComponentContext;
import gr.atc.aniketos.demos.converter.Converter;

public class ConverterImpl implements Converter {
    
    private static Logger logger = LoggerFactory.getLogger(ConverterImpl.class);
    
    @Override
    public double toCelcius(double fahrenheit) {
        double celcius = ((fahrenheit - 32) * 5) / 9;
        logger.debug("Fahrenheit {} => Celcius {}", fahrenheit, celcius);
        return celcius;
    }
    
    @Override
    public double toFahrenheit(double celcius) {
        double fahrenheit = 32 + ((celcius*9)/5);
        logger.debug("Celcius {} => Fahrenheit {}", celcius, fahrenheit);
        return fahrenheit;
    }
    
    protected void activate(ComponentContext context) {
    	logger.debug("*** Activating Service");
    } 

    protected void deactivate(ComponentContext context) {
    	logger.debug("*** Deactivating Service");
    }     
}