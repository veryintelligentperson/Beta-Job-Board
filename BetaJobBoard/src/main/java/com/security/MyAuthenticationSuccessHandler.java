package com.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by michal on 05.07.15.
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        String targetURL = targetUrl(authentication);
        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, targetURL);
        clearAuthenticationAttributes(httpServletRequest);
    }

    private String targetUrl( Authentication authentication){
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for(GrantedAuthority grantedAuthority : authorities ){
            if(grantedAuthority.getAuthority().equals("ROLE_NEW")){
                return "/needverify";
            }else if (grantedAuthority.getAuthority().equals("ROLE_USER")){
                return "/";
            }else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")){
                return "/admin";
            }else return "/access-denied";
        }
        return "#";
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
