package com.dora.weblab2.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dora.weblab2.session.PointSessionManager;

import java.io.IOException;
import java.util.stream.Stream;

@WebServlet(name = "ControllerServlet", value = "/ControllerServlet")
public class ControllerServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("clear") != null){
            try(PointSessionManager manager = new PointSessionManager(req)) {
                manager.clear();
            }
        } else {
            RequestDispatcher d = getServletContext()
                    .getRequestDispatcher("/checkArea");
            d.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        throw new HttpException(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Bad request parameters");
    }

}


