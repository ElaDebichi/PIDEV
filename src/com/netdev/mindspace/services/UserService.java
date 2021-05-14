/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netdev.mindspace.services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.netdev.mindspace.entites.User;
import com.netdev.mindspace.utils.Statics;

import java.util.ArrayList;

/**
 *
 * @author hp
 */
public class UserService {
     public ArrayList<User> membre;
    
    public static UserService instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private UserService() {
         req = new ConnectionRequest();
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }
    
    public void signup(TextField nom ,TextField prenom ,TextField email ,TextField town ,TextField fb,TextField linkdin,TextField password ,
            TextField telephone ,TextField img,TextField level,TextField type_candidat,TextField description, Resources res)
    {
        String url = Statics.BASE_URL + "/candidat/candidat/membre/signup?" + "nom=" + nom.getText().toString() + "&prenom=" + prenom.getText().toString()
                 + "&password=" + password.getText().toString() +  "&phone=" + telephone.getText().toString()
                + "&address=" + email.getText().toString() + "&fb=" + fb.getText().toString() + "&linkdin=" + linkdin.getText().toString() 
                + "&town=" + town.getText().toString()+ "&birthday=" +  "&img=" + img.getText().toString()+ "&level=" + level.getText().toString()
                + "&type_candidat=" + type_candidat.getText().toString()+ "&description=" + description.getText().toString(); //création de l'URL
        
        req.setUrl(url);
        if(nom.getText().equals("") && prenom.getText().equals("") && password.getText().equals("") && town.getText().equals("")
                && telephone.getText().equals("") && email.getText().equals("") && type_candidat.getText().equals("") && fb.getText().equals("")
               && linkdin.getText().equals("")&& description.getText().equals("") )
        {
            Dialog.show("Erreur", "Veillez remplire tous les champs", "ok",null);
        }
        
        req.addResponseListener((e)-> {
            byte[]data = (byte[])e.getMetaData();
            String responceData =new String(data);
            System.out.println("data =====>"+responceData);
                      
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        
        
    }
    
}
