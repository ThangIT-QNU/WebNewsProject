package com.webnewsproject.controller;

import com.webnewsproject.CustomerException.UserNotFoundException;
import com.webnewsproject.domain.Users;
import com.webnewsproject.service.SendMailService;
import com.webnewsproject.service.UserService;
import com.webnewsproject.service.Utility;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class ForgorPasswordController {
    @Autowired
    private UserService userService;

    @Autowired
    private SendMailService mailService;


    @GetMapping("/forgot-password")
    public String forgotPassword(){
        return "forgotPassword";
    }

    @PostMapping("/forgot-password")
    public String proccessForgotPassword(HttpServletRequest request, Model model){
        String email = request.getParameter("email");
        String token = RandomString.make(45);

        try {
            userService.updateResetPasswordToken(token, email);
            //create a reset password link.
            String resetPasswordLink = Utility.getSiteUrl(request)+"/reset-password?token="+token;
            //send mail.
            mailService.sendMail(email,resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email, Please check!!!");
        }catch (UserNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (MessagingException | UnsupportedEncodingException me) {
            me.printStackTrace();
            model.addAttribute("error", "Error while sending mail...");
        }
        return "forgotPassword";
    }

    @GetMapping("/reset-password")
    public String resetPassword(@Param("token") String token, Model model){
        Users user = userService.findUserByResetPasswordToken(token);
        if (user == null){
//            model.addAttribute("message","Invalid Token");
//            model.addAttribute("title","Reset Your Password");
            return "error/404";
        }
        model.addAttribute("token",token);
        return "changePassword";
    }

    @PostMapping("/do-reset-password")
    public String changePassword(HttpServletRequest request, Model model, RedirectAttributes ra){
        String token = request.getParameter("token");
        String newpassword = request.getParameter("newpassword");
        Users user = userService.findUserByResetPasswordToken(token);

        if (user == null){
            model.addAttribute("message","Invalid Token");
            model.addAttribute("title","Reset Your Password");
            return "message";
        }else{
            userService.changePassword(user,newpassword);
            ra.addFlashAttribute("message","You have changed successfully your password");
        }
        return "redirect:/login";
    }
}
