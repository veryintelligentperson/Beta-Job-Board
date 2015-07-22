package com.utility;

import com.controller.LoginController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * Created by michal on 04.07.15.
 */

@Component
public class SendEmail {

    @Value("${email.smtp}")
    private String smtp;
    @Value("${email.address}")
    private String emailAddress;
    @Value("${email.domain}")
    private String domainAddress;
    @Value("${email.login}")
    private String emailLogin;
    @Value("${email.password}")
    private String emailPassword;



    private Properties properties;
    private Session session;
    private MimeMessage msg;

    static Logger logger = LoggerFactory.getLogger(LoginController.class);

    public SendEmail(){
        properties = new Properties();
        session = Session.getInstance(properties);
        msg = new MimeMessage(session);
    }


    public void send(String sendTo,String token) {

        logger.info("login {}",emailLogin);

        Transport transport = null;

        try {
            Address me = new InternetAddress(emailAddress, "info");
            Address someUser = new InternetAddress(sendTo);
            msg.setText("Click this link if you want to activate your account: " +
                    domainAddress+"/verified/"+token);
            msg.setFrom(me);
            msg.setRecipient(Message.RecipientType.TO, someUser);
            msg.setSubject("account verification");

            transport = session.getTransport("smtps");

            //Here we add our gmail(or different provider) username and password
            transport.connect(smtp, emailLogin, emailPassword);
            transport.sendMessage(msg,msg.getAllRecipients());


        } catch (UnsupportedEncodingException | MessagingException e) {
            e.printStackTrace();
        } finally {
            if(transport != null){
                try{
                    transport.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }


    }

}
