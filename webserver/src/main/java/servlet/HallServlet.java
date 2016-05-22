package servlet;

import dao.DaoContext;
import model.HallModel;
import model.HallPrivateModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

final class HallServlet extends HttpServlet {

    private final DaoContext context;

    HallServlet(DaoContext context) {
        this.context = context;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        final Map<String, Object> data = new HashMap<>();
        fillModel(data, context.getPrivateHellModel());
        TemplateUtil.render("hall/get.html", data, resp.getWriter());
    }

    private void fillModel(Map<String, Object> data, HallModel model) {
        try {
            data.put("hallList", model.listHalls());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String name = req.getParameter("name");
        final Map<String, Object> data = new HashMap<>();
        if (name != null && !name.isEmpty()) {
            int nrows = Integer.parseInt(req.getParameter("nrows"));
            int seats = Integer.parseInt(req.getParameter("seats"));
            final HallPrivateModel model = context.getPrivateHellModel();

            data.put("name", name);
            try {
                model.addHall(name, nrows, seats);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            fillModel(data, model);
        }
        resp.setCharacterEncoding("UTF-8");
        TemplateUtil.render("hall/get.html", data, resp.getWriter());
    }
}
