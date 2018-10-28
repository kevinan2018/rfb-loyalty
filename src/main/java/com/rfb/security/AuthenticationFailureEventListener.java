package com.rfb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@Component
public class AuthenticationFailureEventListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    @Autowired
    private LoginAttemptService loginAttempService;

    private static Logger logger = LoggerFactory.getLogger(AuthenticationFailureEventListener.class);

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails) event.getAuthentication().getDetails();
        loginAttempService.loginFailed(webAuthenticationDetails.getRemoteAddress());
        logger.error("Login attempt failed!");
    }
}
