package com.netdev.mindspace.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.netdev.mindspace.entites.Internship;
import com.netdev.mindspace.utils.Statics; 
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InternshipService { 
    
    public static InternshipService instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    
    private InternshipService(){
        req = new ConnectionRequest();
    }
    
    public static InternshipService getInstance() {
        if (instance == null) {
            instance = new InternshipService();
        }
        return instance;
    }
    
    public ArrayList<Internship> tasks;
    
    public ArrayList<Internship> parseTasks(String jsonText){
        try {
            tasks=new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            
            for(Map<String,Object> obj : list){
                //Création des tâches et récupération de leurs données
                
                Internship t = new Internship();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
                t.setLibelle(obj.get("libelle").toString());
                t.setPost(obj.get("poste").toString());
                t.setD(obj.get("dateExpiration").toString());
                t.setDescription(obj.get("description").toString());
                float duree = Float.parseFloat(obj.get("duree").toString());
                t.setDuration((int)duree);
                t.setLevel(obj.get("niveau").toString());
                //float cat = Float.parseFloat(obj.get("category").toString());
                t.setCat(obj.get("category").toString());
                t.setAddress(obj.get("user").toString());
                System.out.println(t);
                tasks.add(t);
                
            }
            
        } catch (IOException ex) { 
        }
        return tasks;
    }
    
    public ArrayList<Internship> getAllTasks(){
        String url = Statics.BASE_URL+"/InternshipMobile";
        
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                tasks = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tasks;
    }
 
    public boolean add(Internship i) {
        
        String url = Statics.BASE_URL + "/InternshipMobile/add?libelle=" + i.getLibelle() + 
                "&poste=" + i.getPost() + "&date_expiration=" + i.getDateExpiration() + 
                "&description=" + i.getDescription() + "&niveau=" + i.getLevel() + 
                "&duree=" + i.getDuration() + "&categorie=" + i.getCategory() ;
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); //Supprimer cet actionListener
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
}
