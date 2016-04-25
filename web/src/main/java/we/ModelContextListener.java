package we;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

/**
 * Created by aleksejpluhin on 25.04.16.
 */
@WebListener
public class ModelContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            base.CinemaMonitor cinemaMonitor = new base.Cinema();
            servletContextEvent.getServletContext().setAttribute("model", cinemaMonitor);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
