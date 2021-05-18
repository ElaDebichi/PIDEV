/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.cleanmodern;

import com.codename1.components.FloatingHint;
import com.codename1.components.SpanLabel;
import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.netdev.mindspace.entites.User;
import com.netdev.mindspace.services.UserService;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author hp
 */
public class MotDePasseOubliee extends BaseForm {
       int randomNum=ThreadLocalRandom.current().nextInt(100000,999999);
    public MotDePasseOubliee(Resources res) {
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("Container");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("Activate");
        
        add(BorderLayout.NORTH, 
                BoxLayout.encloseY(
                        new Label(res.getImage("smily.png"), "LogoLabel"),
                        new Label("Awsome Thanks!", "LogoLabel")
                )
        );
      
        TextField email = new TextField("", "Enter Email");
        email.setSingleLineTextArea(false);
     
        
           TextField code = new TextField("", "Enter code");
        code.setSingleLineTextArea(false);
     
       
        
       
           Button valider = new Button("valider");
              Button change = new Button("change");
           
               TextField pass = new TextField("", "Enter new password");
             pass.setSingleLineTextArea(false);
             pass.setVisible(true);
        Button resend = new Button("Resend");
        resend.setUIID("CenterLink");
        
        Label alreadHaveAnAccount = new Label("Already have an account?");
        Button signIn = new Button("Sign In");
        signIn.addActionListener(e -> previous.showBack());
        signIn.setUIID("CenterLink");
         ;
         Button postuler = new Button("Apply");
        postuler.addActionListener(l -> {
            sendMail("aaa");
            //  System.out.println(in.getLibelle());
            System.out.println("sent mail !");
          
            if (postuler.isEnabled()) {
                System.out.println("button activé ");
            } else {
                System.out.println("button désactivé ");
                new ListInternship(res).showBack();
            }
        });
          change.setEnabled(false);
         valider.addActionListener(l -> {
         
         if ((int)Float.parseFloat(code.getText()) == randomNum)
         {
                
        
             pass.setVisible(true);
            change.setEnabled(true);
             System.out.print("yessssssssss");
                 Dialog.show("succes", "success code", "ok",null);
         }
         else{
              pass.setVisible(false);
              change.setVisible(false);
               change.setEnabled(false);
                  Dialog.show("succes", "not the right code", "ok",null);
                          System.out.print("nooo");

             
         }
            
        });
       
        Container content = BoxLayout.encloseY(
                new FloatingHint(email),
                createLineSeparator(),
                new SpanLabel("We've sent the password  to your email. Please check your inbox", "CenterLabel"),
                resend,
                 createLineSeparator(),
             
                postuler,
                 new FloatingHint(code),
                createLineSeparator(),
                valider,
                pass,
                change,
               
                  
                  
                FlowLayout.encloseCenter(alreadHaveAnAccount, signIn)
        );
        content.setScrollableY(true);
        add(BorderLayout.SOUTH, content);
      
        
    }
     //<editor-fold defaultstate="collapsed" desc=" SendMail ">
    public void sendMail(String address) {

       //authentification info
        String username = "asma.besbes@esprit.tn";
        String password = "203JFT1621";
        String fromEmail = "asma.besbes@esprit.tn";
    
        String toEmail ="mariem.azouz@esprit.tn";
        System.out.println(toEmail);

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        //properties.put("mail.smtp.port", "587");
        properties.put("mail.smtps.port", "465");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                System.out.println("test password");
                return new PasswordAuthentication(username, password);
            }
        });

        //start our mail message
        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(fromEmail));
            msg.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(toEmail));
            msg.setSubject("Forget password");

         

            //msg.setText("Email Body Text");
            Multipart emailContent = new MimeMultipart();

            MimeBodyPart textBodyPart = new MimeBodyPart();
          
             System.out.println(randomNum);
            textBodyPart.setText("your code is : "+randomNum);

           
            emailContent.addBodyPart(textBodyPart);
         
            msg.setContent(emailContent);

            Transport.send(msg);
            System.out.println("Sent message");
        } catch (MessagingException e) {
            e.printStackTrace();
            //Logger.getLogger(Pidev.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    //</editor-fold>
    
    
}
