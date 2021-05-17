/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.cleanmodern;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.netdev.mindspace.services.UserService;
import com.netdev.mindspace.utils.Session;


/**
 *
 * @author hp
 */
public class SignInForm extends BaseForm{
    public SignInForm(Resources res) {
         super(new BorderLayout());
        
        if(!Display.getInstance().isTablet()) {
            BorderLayout bl = (BorderLayout)getLayout();
            bl.defineLandscapeSwap(BorderLayout.NORTH, BorderLayout.EAST);
            bl.defineLandscapeSwap(BorderLayout.SOUTH, BorderLayout.CENTER);
        }
        getTitleArea().setUIID("Container");
        setUIID("SignIn");
        
        add(BorderLayout.NORTH, new Label(res.getImage("Logo1.png"), "LogoLabel"));
        
        TextField email = new TextField("", "Email", 20, TextField.ANY);
        TextField password = new TextField("", "Password", 20, TextField.PASSWORD);
        email.setSingleLineTextArea(false);
        password.setSingleLineTextArea(false);
        Button signIn = new Button("Sign In");
        Button signUp = new Button("Sign Up");
        Button mp = new Button("mot de passe oublier","centerLabel1");
        signUp.addActionListener(e -> new SignUpForm(res).show());
        signUp.setUIID("Link");
        Label doneHaveAnAccount = new Label("Don't have an account?");
        
        Container content = BoxLayout.encloseY(
                new FloatingHint(email),
                createLineSeparator(),
                new FloatingHint(password),
                createLineSeparator(),
                signIn,
                FlowLayout.encloseCenter(doneHaveAnAccount, signUp),
                FlowLayout.encloseCenter(mp)
                
        );
        content.setScrollableY(true);
        add(BorderLayout.SOUTH, content);
        signIn.requestFocus();
        signIn.addActionListener((e)->{
           //MembreService.getInstance().signin(email, password, res);
           //pour tester mehdi
           String cnxResultat = UserService.getInstance().connect(email, password);
           System.out.println(cnxResultat);
           if (cnxResultat.compareTo("password") == 0)
           {
               Dialog.show("Erreur", "your password or email incorrect", "ok",null);
           }
           else if (cnxResultat.compareTo("false") == 0)
           {
               Dialog.show("Erreur", "your password or email incorrect", "ok",null);
           }else if(cnxResultat.compareTo("true") == 0)
           {
               Dialog.show("succes", "you are connected", "ok",null);
               Session.StartSession();
               Session.getSession().SetSessionUser(UserService.getInstance().getUser(email.getText()));
               System.out.print(Session.getSession().getSessionUser());
               
               Form profil = new ProfileForm(res);
               profil.show();
          
            
            
           }
           
           
           
           
            
        });
        mp.addActionListener((e) -> {
            new ActivateForm(res).show();
        });
    }
    
    }
    

