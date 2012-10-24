package gr.atc.aniketos.demos.converter.impl;

import gr.atc.aniketos.demos.converter.Converter;

public class ConverterImpl implements Converter {
    
    @Override
    public double toCelcius(double fahrenheit) {
        return ((fahrenheit - 32) * 5) / 9;
    }
    
    @Override
    public double toFahrenheit(double celcius) {
        return 32 + ((celcius*9)/5);
    }
}