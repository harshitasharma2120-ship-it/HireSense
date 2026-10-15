package hiresenseapp.dbutils;

import hiresenseapp.utils.AppConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class AppInitializer implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext sc = sce.getServletContext();

		// Database credentials
		String url = sc.getInitParameter("dbUrl");
		String username = sc.getInitParameter("username");
		String password = sc.getInitParameter("password");

		// Connect with the database
		DBConnection.openConnection(url, username, password);

		// Secrets (mail login, Affinda key)
		AppConfig.init(sc.getInitParameter("mailUser"), sc.getInitParameter("mailPassword"),
				sc.getInitParameter("affindaApiKey"));

		// Set application name
		String appName = sc.getInitParameter("appName");
		sc.setAttribute("appName", appName);

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		DBConnection.closeConnection();
	}

}
