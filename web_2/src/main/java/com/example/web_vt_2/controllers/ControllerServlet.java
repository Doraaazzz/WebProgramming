package com.example.web_vt_2.controllers;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "ControllerServlet", value = "/controller")
public class ControllerServlet extends HttpServlet{

    private final String METHOD_GET = "GET";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("x_coordinate") != null && request.getParameter("y_coordinate") != null
                && request.getParameterValues("r_coordinates") != null) {
            getServletContext().getRequestDispatcher("/checkArea").forward(request, response);
        }
        else if (request.getParameter("clear") != null){
            //Получение Beans для очистки
        }
        else {
            request.setAttribute("error", new HttpError(450, "Incorrect data:"));
            getServletContext().getRequestDispatcher("/error").forward(request, response);
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getMethod();
        if (method.equals(METHOD_GET)) {
            doGet(request, response);
        } else {
            request.setAttribute("error", new HttpError(404, "Page was not found:"));
            getServletContext().getRequestDispatcher("/error").forward(request, response);
        }
    }


}