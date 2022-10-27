package com.dora.weblab2.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Stream;

@WebServlet(name = "ControllerServlet", value = "/ControllerServlet")
public class ControllerServlet extends HttpServlet{

    private boolean hasParameters(HttpServletRequest req, String ...params){
        return Stream.of(params)
                .allMatch(p->req.getParameter(p) != null );
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ( hasParameters(req, "coordinate_x", "coordinate_y", "coordinate_r")){
            getServletContext()
                    .getRequestDispatcher("/checkArea")
                    .forward(req, resp);
        } else if (hasParameters(req, "clear")){
            // TODO очистка данных, отправка
        } else {
            throw new HttpException(HttpServletResponse.SC_BAD_REQUEST, "Bad request parameters");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        throw new HttpException(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Bad request parameters");
    }

}


