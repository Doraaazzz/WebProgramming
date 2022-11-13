package com.dora.weblab2.servlet;

import com.dora.weblab2.service.Area;
import com.dora.weblab2.session.PointSessionManager;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AreaCheckResult {
    public double x;
    public double y;
    public double r;
    public boolean hit;
    public LocalDateTime date;
    public long executionTime;
}

@WebServlet(name = "AreaCheckServlet", value = "/checkArea")
public class AreaCheckServlet extends HttpServlet {

    private final Area area = new Area();

    private Double[] getDoubles(HttpServletRequest req, String name) throws HttpException {
        try {
            String[] paramValues = req.getParameterValues(name);
            List<Double> result = new ArrayList<Double>();
            for (String val : paramValues) {
                result.add(Double.parseDouble(val));
            }

            return result.toArray(new Double[0]);
        } catch (NumberFormatException e) {
            throw new HttpException(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Bad number format for parameter " + name);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocalDateTime before = LocalDateTime.now();

        double x = getDoubles(req, "coordinate_x")[0];
        Double[] ys = getDoubles(req, "coordinate_y");
        double r = getDoubles(req, "coordinate_r")[0];

        for (double y : ys) {
            try {
                boolean hit = area.contains(x, y, r);
                LocalDateTime after = LocalDateTime.now();
                long executionTime = ChronoUnit.MILLIS.between(before, after);
                try (PointSessionManager manager = new PointSessionManager(req)) {
                    manager.insertPoint(x, y, r, hit, after, executionTime);
                }

                if (req.getParameter("asJson") != null) {
                    JsonObject obj = new JsonObject();
                    obj.addProperty("x", x);
                    obj.addProperty("y", y);
                    obj.addProperty("r", r);
                    obj.addProperty("hit", hit);
                    obj.addProperty("date", after.toString());
                    obj.addProperty("executionTime", executionTime);
                    String json = new Gson().toJson(obj);

                    PrintWriter out = resp.getWriter();
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    out.print(json);
                    out.flush();
                    return;
                }
            } catch (NumberFormatException e) {
                throw new HttpException(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Bad number format for parameter coordinate_y");
            }

        }
        if (req.getParameter("asJson") == null) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }
}
