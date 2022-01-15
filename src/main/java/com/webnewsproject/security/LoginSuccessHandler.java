package com.webnewsproject.security;

import com.webnewsproject.UserDetailService.MyUserDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        MyUserDetail userDetail = (MyUserDetail) authentication.getPrincipal();

        String redirectUrl = request.getContextPath();
        if (userDetail.hasRole("ROLE_ADMIN")
        ){
            redirectUrl +="/admin";
        }
        if (userDetail.hasRole("ROLE_EDITOR")){
            redirectUrl +="/admin/manage-post";
        }
        if (userDetail.hasRole("ROLE_USER")){
            redirectUrl +="/";

        }
        response.sendRedirect(redirectUrl);

    }
}
