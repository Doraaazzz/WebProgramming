package com.example.web_vt_2.controllers;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "ErrorHandleServlet", value = "/error")
public class ErrorServlet extends HttpServlet {
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpError httpError = (HttpError) request.getAttribute("error");
        response.setStatus(httpError.getStatusCode());
        response.getWriter().println(httpError.getErrorMessage());
    }

    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

    }
}
