package we;

import base.CinemaMonitor;
import base.Halls;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import convertor.BaseHallToWebHallConverter;
import convertor.WebHallToBaseHallConverter;
import convertor.WebReservationToBaseReservationConverter;
import convertor.WebSeanceToBaseSeanceConverter;
import model.Seance;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.HTMLDocument;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by aleksejpluhin on 18.04.16.
 */
@WebServlet(urlPatterns = "/admin/*")
public final class AdminService extends HttpServlet {
    private WebSeanceToBaseSeanceConverter webSeanceToBaseSeanceConverter = new WebSeanceToBaseSeanceConverter();
    private CinemaMonitor monitor;
    GsonBuilder builder = new GsonBuilder();
    ;
    Gson gson = builder.create();
    private static final Pattern idNumber = Pattern.compile("^.*(\\d+)$");

    @Override
    public void init() throws ServletException {
        monitor = (CinemaMonitor) this.getServletContext().getAttribute("model");

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_OK);

//        Seance seance = new Seance();
//        seance.setTime(LocalDateTime.parse(req.getParameter("time")));
//        seance.setNameFilm(req.getParameter("film"));
//        System.out.printf(req.getParameter("price"));
//        seance.setAge(Integer.parseInt(req.getParameter("hall")));
//        Double aDouble = new Double(req.getParameter("price"));
//        seance.setPrice(aDouble);
//        seance.setHall(Integer.parseInt(req.getParameter("hall")));
//        seance.setAge(Integer.parseInt(req.getParameter("age")));
//        base.Seance base = webSeanceToBaseSeanceConverter.convert(seance);
//        try {
//            monitor.addSeances(base.getNameFilm(), base.getTime(), base.getHall(), base.getAge(), base.getPrice());
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setStatus(HttpServletResponse.SC_OK);


    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String s = req.getMethod();
        String path = req.getPathInfo();
        if (path.endsWith("list") && s.equalsIgnoreCase("GET")) {
            try {
                List<base.Seance> seances = monitor.search(null, null, null, null, null);
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.setContentType("application/json;charset=UTF-8");
                base.Seance[] listM = seances.toArray(new base.Seance[seances.size()]);
                gson.toJson(listM, base.Seance[].class, resp.getWriter());

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (path.endsWith("new") && s.equalsIgnoreCase("PUT")) {
            base.Seance seance = gson.fromJson(req.getReader(), base.Seance.class);
            try {
                monitor.addSeances(seance.getNameFilm(), seance.getTime(), seance.getHall(), seance.getAge(), seance.getPrice());
            } catch (SQLException e) {
                e.printStackTrace();
            }


        } else if (s.equalsIgnoreCase("DELETE")) {
            Matcher matcher = idNumber.matcher(path);
            if (matcher.matches()) {
                String number = matcher.group(1);
                int id = Integer.parseInt(number);
                try {
                    monitor.deleteSeance(id);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
