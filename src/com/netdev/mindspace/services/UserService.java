/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netdev.mindspace.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.List;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.netdev.mindspace.entites.User;
import com.netdev.mindspace.utils.Statics;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Map;

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
    
    public ArrayList<User> users;
    
      public ArrayList<User> parseTasks(String jsonText){
        try {
            users=new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            /*
                On doit convertir notre réponse texte en CharArray à fin de
            permettre au JSONParser de la lire et la manipuler d'ou vient 
            l'utilité de new CharArrayReader(json.toCharArray())
            
            La méthode parse json retourne une MAP<String,Object> ou String est 
            la clé principale de notre résultat.
            Dans notre cas la clé principale n'est pas définie cela ne veux pas
            dire qu'elle est manquante mais plutôt gardée à la valeur par defaut
            qui est root.
            En fait c'est la clé de l'objet qui englobe la totalité des objets 
                    c'est la clé définissant le tableau de tâches.
            */
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
              /* Ici on récupère l'objet contenant notre liste dans une liste 
            d'objets json List<MAP<String,Object>> ou chaque Map est une tâche.               
            
            Le format Json impose que l'objet soit définit sous forme
            de clé valeur avec la valeur elle même peut être un objet Json.
            Pour cela on utilise la structure Map comme elle est la structure la
            plus adéquate en Java pour stocker des couples Key/Value.
            
            Pour le cas d'un tableau (Json Array) contenant plusieurs objets
            sa valeur est une liste d'objets Json, donc une liste de Map
            */
            java.util.List<Map<String,Object>> list = (java.util.List<Map<String,Object>>)tasksListJson.get("root");
            
            //Parcourir la liste des tâches Json
            for(Map<String,Object> obj : list){
                //Création des tâches et récupération de leurs données
                User t = new User();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
                t.setPhone(((int)Float.parseFloat(obj.get("phone").toString())));
                t.setNom(obj.get("nom").toString());
                t.setPrenom(obj.get("prenom").toString());
                 t.setAddress(obj.get("email").toString());
                  t.setFb(obj.get("fb").toString());
                   t.setLinkdin(obj.get("linkdin").toString());
                    t.setTown(obj.get("town").toString());
                     t.setImg(obj.get("img").toString());
                      t.setNiv_etude(obj.get("level").toString());
                       t.setType_candidat(obj.get("type_candidat").toString());
                        t.setDescription(obj.get("description").toString());
                //Ajouter la tâche extraite de la réponse Json à la liste
                users.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
         /*
            A ce niveau on a pu récupérer une liste des tâches à partir
        de la base de données à travers un service web
        
        */
        return users;
    }
    
    
    public ArrayList<User> getAllUsers(){
        String url = Statics.BASE_URL+"/candidat/candidat/getUsers";
        
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                users = parseTasks(new String(req.getResponseData())); 
                req.removeResponseListener(this);
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return users;
    }
    
}
