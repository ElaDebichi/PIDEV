/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.cleanmodern;

import com.codename1.capture.Capture;
import com.codename1.components.FloatingHint;
import com.codename1.components.ImageViewer;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import com.netdev.mindspace.services.UserService;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author hp
 */
public class SignUpForm extends BaseForm{
    String avatar;
    ImageViewer img_viewer;
     public SignUpForm(Resources res) {
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("Container");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("SignIn");
                
       
        TextField nom = new TextField("", "First Name", 20, TextField.ANY);
        TextField prenom = new TextField("", "Name", 20, TextField.ANY);
         TextField email = new TextField("", "Email", 20, TextField.EMAILADDR);
        TextField town = new TextField("", "Town", 20, TextField.ANY);
        TextField fb = new TextField("", "Facebook", 20, TextField.ANY);
        TextField linkdin = new TextField("", "Linkdin", 20, TextField.ANY);
      
        TextField password = new TextField("", "Password", 20, TextField.PASSWORD);
        TextField confirmPassword = new TextField("", "Confirm Password", 20, TextField.PASSWORD);
        TextField telephone = new TextField("", "phone", 20, TextField.ANY);
         
        
            TextField img = new TextField("", "img", 20, TextField.ANY);
              TextField level = new TextField("", "level", 20, TextField.ANY);
                TextField type_candidat = new TextField("", "type_candidat", 20, TextField.ANY);
                  TextField description = new TextField("", "description", 20, TextField.ANY);

      
        nom.setSingleLineTextArea(false);
        prenom.setSingleLineTextArea(false);
        email.setSingleLineTextArea(false);
        town.setSingleLineTextArea(false);
        fb.setSingleLineTextArea(false);
        linkdin.setSingleLineTextArea(false);
        password.setSingleLineTextArea(false);
        confirmPassword.setSingleLineTextArea(false);
        telephone.setSingleLineTextArea(false);
         img.setSingleLineTextArea(false);
          level.setSingleLineTextArea(false);
           type_candidat.setSingleLineTextArea(false);
            description.setSingleLineTextArea(false);
            Button btn_take_photo= new Button("Prendre une photo");
            
         
       
        Button next = new Button("Next");
        Button signIn = new Button("Sign In");
      //  Button img1 = new Button ("choose an image");
        signIn.addActionListener(e -> previous.showBack());
        signIn.setUIID("Link");
        Label alreadHaveAnAccount = new Label("Already have an account?");
        
        Container content = BoxLayout.encloseY(
                new Label("Sign Up", "LogoLabel"),
                
               
                new FloatingHint(nom),
                createLineSeparator(),
                new FloatingHint(prenom),
                createLineSeparator(),
                new FloatingHint(email),
                createLineSeparator(),
                new FloatingHint(town),
                createLineSeparator(),
                new FloatingHint(fb),
                createLineSeparator(),
                new FloatingHint(linkdin),
                createLineSeparator(),
                new FloatingHint(password),
                createLineSeparator(),
                new FloatingHint(confirmPassword),
                createLineSeparator(),
                new FloatingHint(telephone),
                createLineSeparator(),
                new FloatingHint(img),
                createLineSeparator(),
                new FloatingHint(level),
                createLineSeparator(),
                new FloatingHint(type_candidat),
                createLineSeparator(),
                new FloatingHint(description),
                createLineSeparator()
                
                
        );
        content.setScrollableY(true);
        add(BorderLayout.CENTER, content);
        add(BorderLayout.SOUTH, BoxLayout.encloseY(
                next,
                btn_take_photo,
                FlowLayout.encloseCenter(alreadHaveAnAccount, signIn)
        ));
        next.requestFocus();
        next.addActionListener((e)-> {
            UserService.getInstance().signup(nom, prenom, email, town, fb, linkdin, password, telephone,img,level,type_candidat,description, res);
          
            new ActivateForm(res).show();
        });
        
        btn_take_photo.addActionListener(e->{
            
            this.avatar = Capture.capturePhoto();

            if(this.avatar!=null)
            {
                Image img1= null;
                try {
                    img1 = Image.createImage(FileSystemStorage.getInstance().openInputStream(this.avatar)).scaled(400,440);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                img_viewer.setImage(img1);
                img_viewer.getComponentForm().revalidate();

            }
        });
        
    }
     
     protected String saveFileToDevice(String hi, String ext) throws IOException, URISyntaxException {
        URI uri;
        try {
            uri = new URI(hi);
            String path = uri.getPath();
            int index = hi.lastIndexOf("/");
            hi = hi.substring(index + 1);
            return hi;
        } catch (URISyntaxException ex) {
        }
        return "hh";
    }
//        img1.addActionListener((ActionEvent e) -> {
//            if (FileChooser.isAvailable()) {
//                FileChooser.setOpenFilesInPlace(true);
//                FileChooser.showOpenDialog(multiSelect.isSelected(), ".jpg, .jpeg, .png/plain", (ActionEvent e2) -> {
//                    if (e2 == null || e2.getSource() == null) {
//                        add("No file was selected");
//                        revalidate();
//                        return;
//                    }
//                    if (multiSelect.isSelected()) {
//                        String[] paths = (String[]) e2.getSource();
//                        for (String path : paths) {
//                            System.out.println(path);
//                            CN.execute(path);
//                        }
//                        return;
//                    }
//
//                    String file = (String) e2.getSource();
//                    if (file == null) {
//                        add("No file was selected");
//                        revalidate();
//                    } else {
//                        Image logo;
//
//                        try {
//                            logo = Image.createImage(file).scaledHeight(500);;
//                            add(logo);
//                            String imageFile = FileSystemStorage.getInstance().getAppHomePath() + "photo.png";
//
//                            try (OutputStream os = FileSystemStorage.getInstance().openOutputStream(imageFile)) {
//                                System.out.println(imageFile);
//                                ImageIO.getImageIO().save(logo, os, ImageIO.FORMAT_PNG, 1);
//                            } catch (IOException err) {
//                            }
//                        } catch (IOException ex) {
//                        }
//
//                        String extension = null;
//                        if (file.lastIndexOf(".") > 0) {
//                            extension = file.substring(file.lastIndexOf(".") + 1);
//                            StringBuilder hi = new StringBuilder(file);
//                            if (file.startsWith("file://")) {
//                                hi.delete(0, 7);
//                            }
//                            int lastIndexPeriod = hi.toString().lastIndexOf(".");
//                            Log.p(hi.toString());
//                            String ext = hi.toString().substring(lastIndexPeriod);
//                            String hmore = hi.toString().substring(0, lastIndexPeriod - 1);
//                            try {
//                                String namePic = saveFileToDevice(file, ext);
//                                System.out.println(namePic);
//                            } catch (IOException ex) {
//                            }
//
//                            revalidate();
//
//                        
//                    }
//                    }
//                        });
//            }
//                });
    
    
}
