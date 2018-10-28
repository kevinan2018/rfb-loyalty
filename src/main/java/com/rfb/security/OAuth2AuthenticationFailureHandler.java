package com.rfb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.context.request.RequestContextListener;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OAuth2AuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    private HttpServletRequest request;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
              HttpServletResponse httpServletResponse, AuthenticationException e)
        throws IOException, ServletException {

       String ipAddress = getClientIP();
       System.out.println("client ip address: " + ipAddress);
        if (loginAttemptService.isBlocked(ipAddress)) {
            //throw new LockedException("blocked");//serialization exception
        }

    }


    private String getClientIP() {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null){
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

}
