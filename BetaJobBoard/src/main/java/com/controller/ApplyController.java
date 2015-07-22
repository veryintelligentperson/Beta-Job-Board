package com.controller;


import com.model.beans.Application;
import com.model.beans.User;
import com.model.interfaces.AdDao;
import com.model.interfaces.ApplicationDao;
import com.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by michal on 19.07.15.
 */
@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class ApplyController {

    @Value("${files.path}")
    private String filesCvPath;

    static Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    UserService userService;
    @Autowired
    ApplicationDao applicationDao;
    @Autowired
    AdDao adDao;


    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public String apply(@ModelAttribute("user") User user,@ModelAttribute("adId") int id,
                        @RequestParam("file") MultipartFile file){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User loggedUser = userService.getUserByUsername(username);

        if(!file.isEmpty()){
            try {
                byte[] bytes = file.getBytes();
                String rootPath = filesCvPath;
                File dir = new File(rootPath+File.separator+"applications");
                if (!dir.exists())
                    dir.mkdirs();

                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + "cv"+loggedUser.getId()+".pdf");
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                logger.info("Server File Location="
                        + serverFile.getAbsolutePath());

                logger.info("Successfully uploaded file");
            }catch (IOException e){
                e.printStackTrace();

            }
        }else {
           logger.info("Failed to upload because the file was empty.");
        }


        loggedUser.setName(user.getName());
        loggedUser.setLastname(user.getLastname());
        loggedUser.setPhone(user.getPhone());

        Application application = new Application(loggedUser, adDao.getAdById(id));

        applicationDao.createOrUpdateApplication(application);

        userService.updateUser(loggedUser);

        return "applied";
    }

    @RequestMapping("/applied")
    public String applied(){
        return "applied";
    }
}
