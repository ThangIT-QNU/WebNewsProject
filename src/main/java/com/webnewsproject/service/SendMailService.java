package com.webnewsproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class SendMailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String email, String link) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("testmailnet612@gmail.com", "Báo Ông Thắng");
        helper.setTo(email);
        String subject = "Đây là liên kết để đặt lại mật khẩu của bạn";
        String content = "<p> Xin chào! </p>" +
                "<p> Bạn có yêu cầu đặt lại mật khẩu của mình </p>" +
                "<p> Nhấp vào liên kết bên dưới để thay đổi mật khẩu của bạn </p>" +
                "<p> <b> <a href=\""+link+"\"> Nhấp vào đây </a> </b> </p>" +
                "<p> Bỏ qua email này nếu bạn nhớ mật khẩu của mình hoặc bạn chưa yêu cầu </p>" +
                "<p> Xin cảm ơn! </p>";
        helper.setSubject(subject);
        helper.setText(content,true);

        mailSender.send(message);
    }
}
