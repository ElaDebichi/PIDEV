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

         
          
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
           
            java.util.List<Map<String,Object>> list = (java.util.List<Map<String,Object>>)tasksListJson.get("root");
            
            //Parcourir la liste des tâches Json
            for(Map<String,Object> obj : list){
                //Création des tâches et récupération de leurs données
                User t = new User();
                
               float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
                t.setNom(obj.get("nom").toString());
               
                t.setPrenom(obj.get("prenom").toString());
                 t.setAddress(obj.get("address").toString());
                  t.setFb(obj.get("fb").toString());
                   t.setLinkdin(obj.get("linkdin").toString());
                    t.setTown(obj.get("town").toString());
                     t.setImg(obj.get("img").toString());
//                      t.setNiv_etude(obj.get("niv_etude").toString());
//                       t.setType_candidat(obj.get("type_candidat").toString());
                        t.setDescription(obj.get("description").toString());
                         System.out.println(t.getNom());
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
     public User getUserr(int id){
         User u = new User();
        String url = Statics.BASE_URL+"/candidat/candidat/getUser/"+"{"+id+"}";
        
        req.setUrl(url);
        String str= new String(req.getResponseData());
        req.addResponseListener(((evt) -> {
            
                JSONParser jsonp = new JSONParser();
                
                try{
                    
                Map<String,Object>obj = jsonp.parseJSON(new CharArrayReader(new String(str).toCharArray()));
                        
                u.setNom(obj.get("nom").toString());
                System.out.println(u.getNom());
                            
                }
                catch(IOException ex){
                    System.out.println("error related to sql : " + ex.getMessage());
                
                }
                
                System.out.println("data === " + str);
                
        }));
                NetworkManager.getInstance().addToQueueAndWait(req);
        return u;
    }
     public void update(int id, String nom, String prenom, String email) {
        String url = Statics.BASE_URL + "/candidat/mobile/UpdateUserMobile/" + id + "/" + nom + "/" + prenom + "/" + email ;
        System.out.print(url);
        ConnectionRequest req = new ConnectionRequest();
        req.setUrl(url);

        NetworkManager.getInstance().addToQueueAndWait(req);

    }
     
      public void updatePassword(int id, String password) {
        String url = Statics.BASE_URL + "/candidat/mobile/UpdatePasswordMobile/" + id + "/" + password ;
        System.out.print(url);
        ConnectionRequest req = new ConnectionRequest();
        req.setUrl(url);

        NetworkManager.getInstance().addToQueueAndWait(req);

    }
     public void editUser(TextField id,TextField nom ,TextField prenom ,TextField email ,TextField town ,TextField fb,TextField linkdin,TextField password ,
            TextField telephone ,TextField img,TextField level,TextField type_candidat,TextField description, Resources res)
    {
        String url = Statics.BASE_URL + "/candidat/candidat/membre/ediUser?" + "id=" + id.getText().toString()+"nom=" + nom.getText().toString() + "&prenom=" + prenom.getText().toString()
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
      public void signin(TextField email ,TextField password ,Resources res)
    {
        String url = Statics.BASE_URL + "/candidat/candidat/signin?" + "address=" + email.getText().toString() + "&password=" + password.getText().toString(); //création de l'URL
        req.setUrl(url);
        
        req.addResponseListener((e)-> {
            
            JSONParser j = new JSONParser();
            
            String json = new String(req.getResponseData()) +"";
            
            try{
            if(json.equals("failed"))
            {
                Dialog.show("Echec d'authontification", "Email ou password incorrect", "OK",null);
            }
            else {
                System.out.println("data ====>"+json);
//                Map<String,Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));
//                if(user.size() >0)
                    Dialog.show("succes", "login cb", "ok",null);
            }
            }catch(Exception ex) {
                System.out.println(ex.getMessage());
            }
            
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
    public Map<String, Object> parseConnexion(String jsonText) {
        User u = new User();
        try {
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            return tasksListJson;

        } catch (IOException ex) {

        }
        return null;
    }
    public Map<String, Object> resultatCnx;
    
    public String connect(TextField email ,TextField password) {
        String url = Statics.BASE_URL + "/candidat/candidat/signin?" + "address=" + email.getText().toString() + "&password=" + password.getText().toString(); //création de l'URL
       
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

               
                resultatCnx = parseConnexion(new String(req.getResponseData()));
                req.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultatCnx.get("resultat").toString();
    }
     public User getUser(String text) {
         ArrayList<User>  l = getAllUsers();
        boolean test = false;
        int i;
        for (i = 0; i < l.size(); i++) {
            System.err.println(l.get(i).getId());
            if (l.get(i).getAddress().compareTo(text) == 0) {
                return l.get(i);
            }
        }
        return null;
    }
    public User getUser1(int id) {
      ArrayList<User>  l = getAllUsers();
        int i;
        for (i = 0; i < l.size(); i++) {
            if (l.get(i).getId()== id) {
                return l.get(i);
            }
        }
        return null;
    }
    String json;
    public String getPasswordByEmail(String email,Resources res){
        
         String url = Statics.BASE_URL+"/candidat/membre/getPasswordByEmail?address="+email;
        req = new ConnectionRequest(url , false);
        req.setUrl(url);
         req.addResponseListener((e)->{
             
             JSONParser j = new JSONParser();
         json = new String(req.getResponseData())+"";
        
         try {
             System.out.println("data =="+json);
            Map<String,Object> password =j.parseJSON(new CharArrayReader(json.toCharArray()));
            
            
         }catch(Exception ex){
            ex.printStackTrace();
        }
             
         });
         
          NetworkManager.getInstance().addToQueueAndWait(req);
          return json;
         }
     
    
}
