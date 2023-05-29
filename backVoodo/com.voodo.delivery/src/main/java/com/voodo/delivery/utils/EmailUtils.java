package com.voodo.delivery.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;


@Service
public class EmailUtils {
    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String subject, String text, List<String> list){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("jorgealiasgorka@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        if (list!=null && list.size()>0){
            message.setCc(getCCArray(list));
        }
        emailSender.send(message);

    }

    private String[] getCCArray(List<String> ccList){
        String[] cc=new String[ccList.size()];
        for (int i=0; i<ccList.size(); i++){
            cc[i]=ccList.get(i);

        }
        return cc;

    }
    public void forgotMail(String to,String subject,String password) throws MessagingException{
        MimeMessage message=emailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message,true);
        helper.setFrom("jorgealiasgorka@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        String htmlMsg="<p><b>Tus credenciales de inicio de sesion</b><br><b>Email: </b> " + to + " <br><b>Password: </b> " + password + "<br><a href=\"http://localhost:4200/\">Pincha aqui para logearte</a></p>";
        message.setContent(htmlMsg,"text/html");
        emailSender.send(message);
    }
}
