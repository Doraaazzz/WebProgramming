package com.dora.weblab4.service;

import com.dora.weblab4.data.CheckRepository;
import com.dora.weblab4.data.WebUserRepository;
import com.dora.weblab4.model.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AreaService {

    private CheckRepository checkRepository;
    private WebUserRepository userRepository;

    private boolean checkTopRight(double x, double y, double r) {
        // rectangle
        return (x >= 0 && y >= 0) && (x <= r && y <= r * 0.5);
    }

    private boolean checkTopLeft(double x, double y, double r) {
        // sector
        return (x <= 0 && y >= 0) && (x * x + y * y <= r * r * 0.25);
    }

    private boolean checkBottomLeft(double x, double y, double r) {
        // empty
        return (x <= 0 && y <= 0) && (false);
    }

    private boolean checkBottomRight(double x, double y, double r) {
        // triangle
        return (x >= 0 && y <= 0) && (y >= 0.5 * x - (r / 2));
    }

    private ZonedDateTime getNow() {
        return LocalDateTime.now().atZone(ZoneId.systemDefault());
    }

    @Transactional
    public Check check(Point point, WebUser user) {
        double x = point.getX();
        double y = point.getY();
        double r = point.getR();

        boolean hit = false;
        ZonedDateTime date = getNow();

        hit = hit || checkTopRight(x, y, r);
        hit = hit || checkTopLeft(x, y, r);
        hit = hit || checkBottomLeft(x, y, r);
        hit = hit || checkBottomRight(x, y, r);

        WebUserModel userModel = userRepository.findByUserInterface(user);
        CheckModel checkResult = new CheckModel(x, y, r, hit, date, 0, userModel);

        return checkRepository.save(checkResult);
    }

    public List<Check> getChecksFor(WebUser user) {
        WebUserModel userModel = userRepository.findByUserInterface(user);

        return new ArrayList<>(checkRepository.findAllByUser(userModel));
    }

    @Autowired
    public void setCheckRepository(CheckRepository checkRepository) {
        this.checkRepository = checkRepository;
    }

    @Autowired
    public void setUserRepository(WebUserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
