package com.Hamza.findPharmacy.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Home Controller Class - Shows app version on home endpoint uri
 * @author Hamza Ahmed
 */
@RestController
public class HomeController {

    // reading in app version from properties file
    @Value("${app.version}")
    private String appVersion;

    /**
     * status details of the app version currently running
     * @return - Map containing the app status (currently only includes app version)
     */
    @GetMapping
    @RequestMapping("/")
    public Map getStatus() {
        Map map = new HashMap<String,String>();
        map.put("app-version",appVersion);
        return map;
    }
}
