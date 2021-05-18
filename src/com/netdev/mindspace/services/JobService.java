package com.netdev.mindspace.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.netdev.mindspace.entites.Job; 
import com.netdev.mindspace.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JobService {
    
    public static JobService instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    
    private JobService(){
        req = new ConnectionRequest();
    }
    
    public static JobService getInstance() {
        if (instance == null) {
            instance = new JobService();
        }
        return instance;
    }
    
    public ArrayList<Job> tasks;
    
    public ArrayList<Job> parseTasks(String jsonText){
        try {
            tasks=new ArrayList<>();
            JSONParser j = new JSONParser();
            
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            
            for(Map<String,Object> obj : list){
                //Création des tâches et récupération de leurs données
                Job t = new Job();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
                t.setLibelle(obj.get("libelle").toString());
                t.setPost(obj.get("poste").toString());
                t.setD(obj.get("dateExpiration").toString());
                t.setDescription(obj.get("description").toString());
                t.setContrat(obj.get("typeContrat").toString());
                float salaire = Float.parseFloat(obj.get("salaire").toString());
                t.setSalary(salaire);
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
    
    public ArrayList<Job> getAllTasks(){
        String url = Statics.BASE_URL+"/JobMobile";
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
    
}
