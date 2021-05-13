/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
//import com.mycompany.myapp.entities.Utilisateur;
import com.mycompany.myapp.entities.Post;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author sofie
 */
public class ServicePost {
  public ArrayList<Post> posts;
    
    public static ServicePost instance=null;
    public boolean resultOK=true;
    private ConnectionRequest req;

    private ServicePost() {
         req = new ConnectionRequest();
    }

    public static ServicePost getInstance() {
        if (instance == null) {
            instance = new ServicePost();
        }
        return instance;
    }

    public boolean addPost(Post t) {
        //http://127.0.0.1:8000/post/all/posts/new?content=aaaaa
        
        String url = Statics.BASE_URL + "/post/all/posts/new?content="+ t.getContent(); //création de l'URL
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

    public ArrayList<Post> parseTasks(String jsonText){
        try {
            posts=new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

           
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            
            //Parcourir la liste des tâches Json
            for(Map<String,Object> obj : list){
                //Création des tâches et récupération de leurs données
                Post t = new Post();
                
                float id = Float.parseFloat(obj.get("id").toString());
                
                t.setId((int) id);
               
                t.setContent(obj.get("content").toString());
               
                
                t.setDate(obj.get("date").toString());
                
                float nblikes = Float.parseFloat(obj.get("nblikes").toString());
                 t.setNblikes((int) nblikes);
                
                
                
                //Ajouter la tâche extraite de la réponse Json à la liste
                posts.add(t);
                System.out.println(posts);
            }
            
            
        } catch (IOException ex) {
            
        }
         
        return posts;
    }
    
    public ArrayList<Post> getAllPosts(){
        String url = Statics.BASE_URL+"/post/all/posts";
        
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                posts = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return posts;
    }
    
     public Post getPost(int id, Post post){
        String url = Statics.BASE_URL+"/post/all/posts";
        
        req.setUrl(url);
        String str= new String(req.getResponseData());
        req.addResponseListener(((evt) -> {
            
                JSONParser jsonp = new JSONParser();
                
                try{
                    
                Map<String,Object>obj = jsonp.parseJSON(new CharArrayReader(new String(str).toCharArray()));
                float idd = Float.parseFloat(obj.get("id").toString());
                post.setId((int) idd);             
                post.setContent(obj.get("content").toString());
                            
                }
                catch(IOException ex){
                    System.out.println("error related to sql : " + ex.getMessage());
                
                }
                
                System.out.println("data === " + str);
                
        }));
                NetworkManager.getInstance().addToQueueAndWait(req);
        return post;
    }
    
    
      public boolean deletePost(int id){
          
           String url = Statics.BASE_URL+"/post/all/posts/delete/"+id;
        
        req.setUrl(url);
        //String str= new String(req.getResponseData());
        req.addResponseListener(new ActionListener<NetworkEvent>() {
               @Override
               public void actionPerformed(NetworkEvent evt) {
                   
                   req.removeResponseCodeListener(this);
               }
           });
            
               NetworkManager.getInstance().addToQueueAndWait(req);
          
          return resultOK;
      
      }
    
}
