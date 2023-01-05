package com.dora.weblab4.controller;

import com.dora.weblab4.controller.auth.RequiresAuth;
import com.dora.weblab4.model.response.CheckResponse;
import com.dora.weblab4.model.request.PointRequest;
import com.dora.weblab4.model.WebUser;
import com.dora.weblab4.service.AreaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping(value = "/area/", produces = MediaType.APPLICATION_JSON_VALUE)
public class AreaController {

    private AreaService areaService;

    private WebUser getAuthenticatedUser(Authentication authentication) {
        return (WebUser) authentication.getPrincipal();
    }

    @PostMapping("/check")
    @RequiresAuth
    public CheckResponse check(
            @RequestBody @Valid PointRequest point,
            Authentication authentication) {
        WebUser user = getAuthenticatedUser(authentication);
        return new CheckResponse(areaService.check(point, user));
    }

    @GetMapping
    @RequiresAuth
    public List<CheckResponse> getChecks(Authentication authentication) {
        WebUser user = getAuthenticatedUser(authentication);

        return areaService.getChecksFor(user).stream()
                .map(CheckResponse::new)
                .collect(Collectors.toList());
    }

    @Autowired
    public void setAreaService(AreaService areaService) {
        this.areaService = areaService;
    }
}
