package com.controller;

import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by michal on 03.07.15.
 */
@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class UserController {

    @RequestMapping(value = "/userSettings")
    public String userSettings(){

        return "userSettings";
    }

}
