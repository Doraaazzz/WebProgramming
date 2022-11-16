package com.dora.weblab2.servlet;

import com.dora.weblab2.util.Area;
import com.dora.weblab2.session.PointSessionManager;
import com.dora.weblab2.util.PointJsonArrayManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
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

@WebServlet(name = "AreaCheckServlet", value = "/checkArea")
public class AreaCheckServlet extends HttpServlet {

    private final Area area = new Area();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocalDateTime before = LocalDateTime.now();

        double x = (double) req.getAttribute("x");
        Double[] ys = (Double[]) req.getAttribute("ys");
        double r = (double) req.getAttribute("r");

        PointJsonArrayManager json = new PointJsonArrayManager();
        for (double y : ys) {
            boolean hit = area.contains(x, y, r);
            LocalDateTime after = LocalDateTime.now();
            long executionTime = ChronoUnit.MILLIS.between(before, after);
            try (PointSessionManager manager = new PointSessionManager(req)) {
                manager.insertPoint(x, y, r, hit, after, executionTime);
            }
            json.insertPoint(x, y, r, hit, after, executionTime);
        }

        String accept = req.getHeader("Accept");

        if (accept != null && accept.equals("application/json")) {
            resp.setContentType("application/json");
            PrintWriter out = resp.getWriter();
            out.print(json.toString());
            out.flush();
        } else {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }
}
