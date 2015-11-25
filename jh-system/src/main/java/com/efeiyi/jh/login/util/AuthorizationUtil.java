package com.efeiyi.jh.login.util;

import com.efeiyi.ec.organization.model.MyUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthorizationUtil {

    public static MyUser getUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        try {
            return (MyUser) authentication.getPrincipal();
        } catch (Exception e) {
            MyUser user = new MyUser();
            return user;
        }
    }

}
