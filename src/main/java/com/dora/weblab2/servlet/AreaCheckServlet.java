package com.dora.weblab2.servlet;

import com.dora.weblab2.service.Area;
import com.dora.weblab2.session.PointSessionManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@WebServlet (name = "AreaCheckServlet", value = "/checkArea")
public class AreaCheckServlet extends HttpServlet {

    private final Area area = new Area();

    private double getDouble(HttpServletRequest req, String name) throws HttpException {
        try {
            return Double.parseDouble(req.getParameter(name));
        } catch (NumberFormatException e) {
            throw new HttpException(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Bad number format for parameter " + name
            );
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocalDateTime before = LocalDateTime.now();

        double x = getDouble(req, "coordinate_x");
        double y = getDouble(req, "coordinate_y");
        double r = getDouble(req, "coordinate_r");

        boolean hit = area.contains(x, y, r);
        LocalDateTime after = LocalDateTime.now();
        long executionTime = ChronoUnit.MILLIS.between(before, after);
        try (PointSessionManager manager = new PointSessionManager(req)) {
            manager.insertPoint(x, y, r, hit, after, executionTime);
        }

        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }
}
