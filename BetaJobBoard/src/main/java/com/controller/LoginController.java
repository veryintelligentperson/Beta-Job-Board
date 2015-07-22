package com.controller;

import com.model.interfaces.VerificationTokenDao;
import com.model.beans.User;
import com.model.beans.VerificationToken;
import com.utility.EmailExistsException;
import com.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by michal on 30.06.15.
 */
@Controller
public class LoginController {

    static Logger logger = LoggerFactory.getLogger(LoginController.class);

    private UserService userService;
    @Autowired
    private VerificationTokenDao tokenDao;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login")
    public String login(@RequestParam(value = "error", required = false) boolean err,
                        Model model){
        model.addAttribute("error",err);
        return "login";
    }


    @RequestMapping(value = "/signup")
    public String signUp(Model model)
    {
        model.addAttribute("user", new User());
        return "signup";
    }

    @RequestMapping(value = "/signup",method = RequestMethod.POST)
    public String createUser(@ModelAttribute("user") User user, BindingResult result){

        if(!result.hasErrors()){
            createUserAccount(user);
        }
        return "createuser";
    }

    @RequestMapping(value = "/createuser")
    public String userCreated(Model model){
        List<User> usersList = userService.getUsersList();

        model.addAttribute("users",usersList);

        return "createuser";
    }

    @RequestMapping(value = "/verified/{token}")
    public String verification(@PathVariable String token, Model model){

        logger.info("token received from path:{} ",token);
        logger.info("token exist: {}",tokenExist(token));
        if(tokenExist(token)){

            VerificationToken verTok = tokenDao.getVerificationTokenByToken(token);

            if(verTok.getExpirationTime().after(new Date())){
                User user = verTok.getUser();
                user.setAuthority("ROLE_USER");
                userService.updateUser(user);
                return "succeed";

            }else{
                model.addAttribute("errToken", "Token has expired!");
            }
        }else {
            model.addAttribute("errToken", "Wrong token!");
        }

        return "verified";
    }

    @RequestMapping(value = "/access-denied")
    public String accessDenied(){
        return "access-denied";
    }

    private User createUserAccount(User user){
        User newUser = null;
        try {
            newUser = userService.registerAccount(user);
        }catch (EmailExistsException e){
            return null;
        }
        return newUser;
    }

    @PreAuthorize("hasRole('ROLE_NEW')")
    @RequestMapping(value = "/needverify")
    public String needVerify(){
        return "needverify";
    }

    private boolean tokenExist(String token){
        VerificationToken verToken = tokenDao.getVerificationTokenByToken(token);
        return verToken != null;
    }

}
