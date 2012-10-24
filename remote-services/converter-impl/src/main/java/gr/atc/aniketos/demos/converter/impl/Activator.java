package gr.atc.aniketos.demos.converter.impl;

import java.util.Dictionary;
import java.util.Hashtable;

import gr.atc.aniketos.demos.converter.Converter;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {
    private ServiceRegistration registration;

    public void start(BundleContext bc) throws Exception {
        Dictionary props = new Hashtable();

        props.put("service.exported.interfaces", "*");
        props.put("service.exported.configs", "org.apache.cxf.ws");
        props.put("org.apache.cxf.ws.address", "http://localhost:9090/converter");
        
        registration = bc.registerService(Converter.class.getName(), 
                                          new ConverterImpl(), props);
    }

    public void stop(BundleContext bc) throws Exception {
        registration.unregister();
    }
}