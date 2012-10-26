package gr.atc.aniketos.demos.converter.client;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import gr.atc.aniketos.demos.converter.Converter;

public class Activator implements BundleActivator {

	private static BundleContext context;

	private ServiceTracker tracker;

	private ConverterDialog converterDialog;

	private Converter service;

	static BundleContext getContext() {
		return context;
	}

	public void start(final BundleContext bundleContext) throws Exception {
        tracker = new ServiceTracker(bundleContext, Converter.class.getName(), null) {
            @Override
            public Object addingService(ServiceReference reference) {
                Object result = super.addingService(reference);

                useService(bundleContext, reference);

                return result;
            }
        };
        tracker.open();
	}

    protected void useService(final BundleContext bc, ServiceReference reference) {
        Object svc = bc.getService(reference);
        if (!(svc instanceof Converter)) {
            return;
        }
        service = (Converter) svc;

        if (converterDialog != null) {
        	return;
        }

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

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		tracker.close();
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

}
