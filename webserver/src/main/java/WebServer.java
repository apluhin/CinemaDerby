import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import servlet.ServletCollections;

public final class WebServer {

    public static void main(String[] args) throws Exception {
        final Server server = new Server(8080);
        final ServletContextHandler handler = new ServletContextHandler();
        server.setHandler(handler);
        ServletCollections.addServlets(handler);
        server.start();
    }
}
