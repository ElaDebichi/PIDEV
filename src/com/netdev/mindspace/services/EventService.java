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
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import com.netdev.mindspace.entites.Event;
import com.netdev.mindspace.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;



/**
 *
 * @author spicy
 */
public class EventService {
    
     public ArrayList<Event> events;
    
    public static EventService instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private EventService() {
         req = new ConnectionRequest();
    }

    public static EventService getInstance() {
        if (instance == null) {
            instance = new EventService();
        }
        return instance;
    }
    
     public boolean addEvent(Event t) {
        String url = Statics.BASE_URL + "/evenements_front/event_jsn"; //création de l'URL
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); //Supprimer cet actionListener
                /* une fois que nous avons terminé de l'utiliser.
                La ConnectionRequest req est unique pour tous les appels de 
                n'importe quelle méthode du Service task, donc si on ne supprime
                pas l'ActionListener il sera enregistré et donc éxécuté même si 
                la réponse reçue correspond à une autre URL(get par exemple)*/
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
     
     
        public boolean AttendEvent(int id_event,int id_user) {
        String url = Statics.BASE_URL + "/participer_jsn/"+id_event; //création de l'URL
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); //Supprimer cet actionListener
                /* une fois que nous avons terminé de l'utiliser.
                La ConnectionRequest req est unique pour tous les appels de 
                n'importe quelle méthode du Service task, donc si on ne supprime
                pas l'ActionListener il sera enregistré et donc éxécuté même si 
                la réponse reçue correspond à une autre URL(get par exemple)*/
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    public boolean UnattendEvent(int id_event,int id_user) {
        String url = Statics.BASE_URL + "/removeParticiper/"+id_event; //création de l'URL
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); //Supprimer cet actionListener
                /* une fois que nous avons terminé de l'utiliser.
                La ConnectionRequest req est unique pour tous les appels de 
                n'importe quelle méthode du Service task, donc si on ne supprime
                pas l'ActionListener il sera enregistré et donc éxécuté même si 
                la réponse reçue correspond à une autre URL(get par exemple)*/
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
        
    
    public ArrayList<Event> parseTasks(String jsonText) throws ParseException{
         try {
             events=new ArrayList<>();
             JSONParser j = new JSONParser();
             
             
             Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
             
             
              List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
             
             SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
              for(Map<String,Object> obj : list){
             
             Event t = new Event();
             float id = Float.parseFloat(obj.get("id").toString());
             float nbr_participants = Float.parseFloat(obj.get("nbrParticipants").toString());
             float nbr_max = Float.parseFloat(obj.get("nbrMax").toString());
             
             String date = obj.get("Date").toString();
            
            
             t.setDate(date);
          
             t.setId((int)id);
             t.setNom(obj.get("nom").toString());
             
             t.setTitre(obj.get("titre").toString());
             t.setAdresse(obj.get("adresse").toString());
             
             t.setNbr_participants((int)nbr_participants);
             t.setNbr_max((int)nbr_max);
             t.setImg(obj.get("img").toString());
             
             
             
             events.add(t);
             }
             
             
         } catch (IOException ex) {
            
         }
         return events;
    }
    
    public ArrayList<Event> getAllTasks(){
        String url = Statics.BASE_URL + "/evenements_front/event_jsn";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
                try {
                    events = parseTasks(new String(req.getResponseData()));
                    req.removeResponseListener(this);
                } catch (ParseException ex) {
                   
                }
              
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return events;
    }
    
   
    
}
