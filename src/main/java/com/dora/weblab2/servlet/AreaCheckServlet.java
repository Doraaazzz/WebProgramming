package com.dora.weblab2.servlet;

import com.dora.weblab2.service.Area;
import com.dora.weblab2.session.PointSessionManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AreaCheckServlet", value = "/checkArea")
public class AreaCheckServlet extends HttpServlet {

    private final Area area = new Area();

    private double getDouble(HttpServletRequest req, String name) throws HttpException {
        try {
            return Double.parseDouble(req.getParameter(name));
        } catch (NumberFormatException e) {
            throw new HttpException(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Bad number format for parameter " + name);
        }
    }

    private Double[] getDoubleArray(HttpServletRequest req, String name) throws HttpException {
        try {
            String[] paramValues = req.getParameterValues(name); //a=1&b=2&a=3&a=4
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

        double x = getDouble(req, "coordinate_x");
        Double[] ys = getDoubleArray(req, "coordinate_y");
        double r = getDouble(req, "coordinate_r");

        for (double y : ys) {
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
        }
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }
}
