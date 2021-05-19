
package com.netdev.mindspace.services;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;

import com.netdev.mindspace.entites.Formation;
import com.netdev.mindspace.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author spicy
 */
public class Formationservice {
    
    
    public ArrayList<Formation> formations;
    public static Formationservice instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private Formationservice() {
         req = new ConnectionRequest();
    }

    public static Formationservice getInstance() {
        if (instance == null) {
            instance = new Formationservice();
        }
        return instance;
    }
    
    
       public ArrayList<Formation> parseTasks(String jsonText) throws ParseException, IOException{
        
             formations=new ArrayList<>();
             JSONParser j = new JSONParser();
             
             
             Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));  
             System.out.print(tasksListJson);
             
              List<Map<String,Object>> list = (java.util.List<Map<String,Object>>)tasksListJson.get("root");
             System.out.print(list);
             System.out.print("AAAAAAAAAAAA");
             SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
              for(Map<String,Object> obj : list){
           
             Formation t = new Formation();
             float id = Float.parseFloat(obj.get("id").toString());
             
             String nomformation = obj.get("nomformation").toString();
             t.setNomformation(nomformation);
            
             
          
             t.setIdformation((int)id);
             
             t.setSujetdeformation(obj.get("sujetdeformation").toString());
             
             t.setNbrParticipants((double) obj.get("nbrParticipants"));
           
             
            
             
             
             formations.add(t);
             }
             
             
        
         return formations;
    }
       
     
       public ArrayList<Formation> parseTaskshow(String jsonText) throws ParseException, IOException{
         
             formations=new ArrayList<>();
             JSONParser j = new JSONParser();
             
             
             Map<String,Object> obj = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
             
             
              //Map<String,Object> obj = (Map<String,Object>) tasksListJson.get("root");
            
             System.out.print("AAAAAAAAAAAA");
             System.out.print(obj);
             SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
              
           
             Formation t = new Formation();
             float id = Float.parseFloat(obj.get("id").toString());
             
             String nomformation = obj.get("nomformation").toString();
            
            
             t.setNomformation(nomformation);
          t.setIdformation((int)id);
             
             t.setSujetdeformation(obj.get("sujetdeformation").toString());
             
             t.setNbrParticipants( (double) obj.get("nbrParticipants"));
           
             
            
             
             
             
             
             formations.add(t);
             
 
         return formations;
    }

    
     
       
       
    public ArrayList<Formation> getAllFormations(){
        String url = Statics.BASE_URL + "/mobile/displayFormations";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
                try {
                    formations = parseTasks(new String(req.getResponseData()));
                    req.removeResponseListener(this);
                } catch (ParseException ex) {
                   
                } catch (IOException ex) {
                  System.out.println("Error!!!" + ex.getMessage());
                }
              
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return formations;
    }
    
     public ArrayList<Formation> ShowFormation(int id){
        String url = Statics.BASE_URL + "/mobile/formation/art_d_jsn/"+id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
                try {
                    formations = parseTaskshow(new String(req.getResponseData()));
                    req.removeResponseListener(this);
                } catch (ParseException ex) {
                   
                } catch (IOException ex) {
                     System.out.println("Error!!!" + ex.getMessage());
                }
              
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return formations;
    }
     
    
    
    
}
