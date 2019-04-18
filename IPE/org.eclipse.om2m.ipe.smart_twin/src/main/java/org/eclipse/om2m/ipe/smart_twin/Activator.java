package org.eclipse.om2m.ipe.smart_twin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.datamapping.service.DataMapperService;
import org.eclipse.om2m.interworking.service.InterworkingService;
import org.eclipse.om2m.ipe.smart_twin.util.Controller;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class Activator implements BundleActivator {

	private static final Log LOGGER = LogFactory.getLog(Activator.class);
	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	private Monitor monitor;
	private Controller controller;

	private ServiceTracker<CseService, CseService> cseServiceTracker;
	private ServiceTrackerCustomizer<CseService, CseService> cseServiceTrackerCustomizer = new ServiceTrackerCustomizer<CseService, CseService>() {

		@Override
		public void removedService(ServiceReference<CseService> reference, CseService service) {
			monitor.setCse(null);
		}

		@Override
		public void modifiedService(ServiceReference<CseService> reference, CseService service) {
			monitor.setCse(service);
		}

		@Override
		public CseService addingService(ServiceReference<CseService> reference) {
			monitor.setCse(context.getService(reference));
			// Starts the monitor when the CSE service is available
			monitor.start();
			return context.getService(reference);
		}
	};

	private ServiceTracker<DataMapperService, DataMapperService> dataMapperServiceTracker;
	private ServiceTrackerCustomizer<DataMapperService, DataMapperService> dataMapperServiceCustomizer = new ServiceTrackerCustomizer<DataMapperService, DataMapperService>() {

		public DataMapperService addingService(ServiceReference<DataMapperService> reference) {
			DataMapperService service = context.getService(reference);
			controller.setDataMapperService(service);
			service.getServiceDataType();
			return service;
		}

		@Override
		public void modifiedService(ServiceReference<DataMapperService> reference, DataMapperService service) {
			controller.setDataMapperService(service);
		}

		@Override
		public void removedService(ServiceReference<DataMapperService> reference, DataMapperService service) {
			controller.setDataMapperService(null);
		};

	};

	public void start(BundleContext bundleContext) throws Exception {
		try {
			Activator.context = bundleContext;
			LOGGER.info("Starting SMART_TWIN IPE");
			monitor = new Monitor();
			controller = new Controller(monitor);
			bundleContext.registerService(InterworkingService.class, controller, null);
			Filter filter = FrameworkUtil.createFilter("(&(" + Constants.OBJECTCLASS + "="
					+ DataMapperService.class.getName() + ")" + "(type=" + MimeMediaType.JSON + "))");
			dataMapperServiceTracker = new ServiceTracker<>(bundleContext, filter, dataMapperServiceCustomizer);
			dataMapperServiceTracker.open();

			cseServiceTracker = new ServiceTracker<>(bundleContext, CseService.class, cseServiceTrackerCustomizer);
			cseServiceTracker.open();

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		cseServiceTracker.close();
		cseServiceTracker = null;
		dataMapperServiceTracker.close();
		dataMapperServiceTracker = null;
		monitor.stop();
	}

}
