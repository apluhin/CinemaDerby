package we;

import base.CinemaMonitor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by aleksejpluhin on 18.04.16.
 */
@WebServlet(urlPatterns = "/admin/*")
public final class AdminService extends HttpServlet {

    private CinemaMonitor monitor;

    @Override
    public void init() throws ServletException {
        monitor = (CinemaMonitor) this.getServletContext().getAttribute("model");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println("hello world1");
     //   resp.getWriter().println(this.getServletContext().getAttribute("model").toString());
        try {
            resp.getWriter().println(monitor.listHall());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
