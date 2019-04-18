package org.eclipse.om2m.ttn.ipe;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Activator implements BundleActivator {
	
	private static BundleContext context;

    /** Logger */
    private static Log logger = LogFactory.getLog(Activator.class);

	static BundleContext getContext() {

		logger.info("Creating TTN Ipe !!!!!!!!!!!");
		
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;

		logger.info("Starting TTN Ipe !!!!!!!!!!!");
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
