package com.dora.weblab2.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Closeable;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class PointSessionManager implements Closeable {

    private PointsTable pointsTable;
    private HttpSession session;

    public List<Point> getPoints() {
        return pointsTable.getPoints();
    }

    public void insertPoint(double x, double y, double r, boolean hit, LocalDateTime date, long executionTime) {
        pointsTable.getPoints().add(new Point(x, y, r, hit, date, executionTime));
    }

    public PointSessionManager(HttpServletRequest request) {
        session = request.getSession();
        Object obj = session.getAttribute("points");
        if (obj instanceof PointsTable) {
            pointsTable = (PointsTable) obj;
        } else {
            pointsTable = new PointsTable();
        }
    }

    @Override
    public void close() throws IOException {
        session.setAttribute("points", pointsTable);
    }
}
