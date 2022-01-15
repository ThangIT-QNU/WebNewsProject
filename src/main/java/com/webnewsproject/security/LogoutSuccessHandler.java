package com.webnewsproject.security;

import com.webnewsproject.UserDetailService.MyUserDetail;
import com.webnewsproject.domain.Roles;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        MyUserDetail userDetail = (MyUserDetail) authentication.getPrincipal();

        String redirectUrl = request.getContextPath();

        if (userDetail.hasRole("ROLE_USER")){
            response.sendRedirect(redirectUrl+"/");
        }else{
            response.sendRedirect(redirectUrl+"/login");
        }

    }


}
