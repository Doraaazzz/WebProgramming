package com.example.web_vt_2.controllers;

import javax.servlet.annotation.WebServlet;

@WebServlet (name = "AreaCheckServlet", value = "/checkArea")
public class AreaCheckServlet {

}


public class AreaCheckServlet extends HttpServlet{
    @EJB
    private DataManagerBean dataManagerBean;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        dataManagerBean.checkAndUpdateData(request, getServletContext());
        getServletContext().getRequestDispatcher("/table.jsp").forward(request, response);
    }

}