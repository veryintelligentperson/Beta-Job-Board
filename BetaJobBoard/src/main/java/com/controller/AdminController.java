package com.controller;

import com.model.interfaces.AdDao;
import com.model.interfaces.ApplicationDao;
import com.model.interfaces.UserDao;
import com.model.beans.Ad;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by michal on 29.06.15.
 */
@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private ApplicationDao applicationDao;
    @Autowired
    private AdDao adDao;


    static Logger logger = LoggerFactory.getLogger(HomeController.class);


    @RequestMapping(value = "admin", method = RequestMethod.GET)
    public String admin(){
        return "admin";
    }


    @RequestMapping(value = "/adform")
    public String adForm(Model model){

        model.addAttribute("ad", new Ad());
        return "adform";
    }

    @RequestMapping(value = "/createad", method = RequestMethod.POST)
    public String adFormSubmit(@ModelAttribute Ad ad, Model model){
        model.addAttribute("ad", ad);
        adDao.createOrUpdateAd(ad);
        return "result";
    }

    @RequestMapping(value = "/adsettings")
    public String adSettings(Model model){

        List<Ad> ads = adDao.getAllAds();
        model.addAttribute("allads", ads);
        Ad ad = new Ad();
        model.addAttribute("adEdit", ad);

        return "adsettings";
    }

    @RequestMapping(value = "/deleteAd", method = RequestMethod.POST)
    public String deleteAd(@ModelAttribute("idDelete") int id){
        logger.info("deleting user id:{} ",id);
        adDao.deleteAd(id);
        return "redirect:adsettings";
    }

    @RequestMapping(value = "/editAd", method = RequestMethod.POST)
    public String editAd(@ModelAttribute("editAd")Ad ad, @ModelAttribute("idEdit")int id){

        logger.info("EDIT ID: {}",id);
        Ad ad1 = adDao.getAdById(id);
        ad1.setName(ad.getName());
        ad1.setText(ad.getText());
        adDao.createOrUpdateAd(ad1);

        return "redirect:adsettings";
    }

    @RequestMapping(value = "/applications")
    public String test(){
        return "applications";
    }

    @RequestMapping(value = "/updatead", method = RequestMethod.POST)
    public String updateAd(@ModelAttribute Ad ad){
        adDao.createOrUpdateAd(ad);
        return "adsettings";
    }

}
