package servlet;

import dao.DaoContext;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public final class ServletCollections {
    public static void addServlets(ServletContextHandler handler) {
        final DaoContext context = DaoContext.getDefault();
        final ServletHolder holder = new ServletHolder(new HallServlet(context));
        handler.addServlet(holder, "/hall");
    }
}
