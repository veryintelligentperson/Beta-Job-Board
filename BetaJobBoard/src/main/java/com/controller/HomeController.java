package com.controller;

import com.model.beans.User;
import com.model.interfaces.AdDao;
import com.model.beans.Ad;
import com.utility.TokenGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * Created by michal on 27.06.15.
 */
@Controller
public class HomeController {

    @Autowired
    private AdDao adDao;

    static Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(value = "/")
    public String sayHello(ModelMap model){
        System.out.println(TokenGenerator.generateToken());
        List<Ad> ads = adDao.getAllAds();
        model.addAttribute("allads", ads);

        return "welcome";
    }

    @RequestMapping(value = "/ad")
    public String adViewer
            (@RequestParam(value = "id", required = true) int id, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        logger.info("username: {} ",username);

        Ad adById = adDao.getAdById(id);
        model.addAttribute("ad", adById);

        model.addAttribute("user", new User());

        return "ad";
    }


}
