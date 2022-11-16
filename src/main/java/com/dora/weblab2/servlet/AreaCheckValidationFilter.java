package com.dora.weblab2.servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebFilter(value = "/checkArea", dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST})
public class AreaCheckValidationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String xParam = req.getParameter("coordinate_x");
        String[] yParam = req.getParameterValues("coordinate_y");
        String rParam = req.getParameter("coordinate_r");

        if (xParam == null || yParam == null || rParam == null || yParam.length == 0)
            throw new HttpException(HttpServletResponse.SC_BAD_REQUEST, "Missing required parameters");

        try {
            Double[] ys = Stream.of(yParam)
                    .map(y -> Double.parseDouble(y))
                    .collect(Collectors.toList())
                    .toArray(new Double[0]);

            req.setAttribute("x", Double.parseDouble(xParam));
            req.setAttribute("ys", ys);
            req.setAttribute("r", Double.parseDouble(rParam));

            filterChain.doFilter(req, resp);
        } catch (NumberFormatException e) {
            throw new HttpException(HttpServletResponse.SC_BAD_REQUEST, "All parameters must be double");
        }
    }
}
