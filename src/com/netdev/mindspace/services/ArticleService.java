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
import com.netdev.mindspace.entites.Article;
import com.netdev.mindspace.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 *
 * @author spicy
 */
public class ArticleService {
    
    
    public ArrayList<Article> articles;
    public static ArticleService instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ArticleService() {
         req = new ConnectionRequest();
    }

    public static ArticleService getInstance() {
        if (instance == null) {
            instance = new ArticleService();
        }
        return instance;
    }
    
    
       public ArrayList<Article> parseTasks(String jsonText) throws ParseException{
         try {
             articles=new ArrayList<>();
             JSONParser j = new JSONParser();
             
             
             Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
             
             
              List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
             System.out.print(list);
             System.out.print("AAAAAAAAAAAA");
             SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
              for(Map<String,Object> obj : list){
           
             Article t = new Article();
             float id = Float.parseFloat(obj.get("id").toString());
             
             String date = obj.get("Date").toString();
            
            
             t.setDate(date);
          
             t.setId((int)id);
             t.setDescription(obj.get("description").toString());
             
             t.setTitre(obj.get("titre").toString());
           
             
           
             t.setImg(obj.get("img").toString());
             
             
             
             articles.add(t);
             }
             
             
         } catch (IOException ex) {
            
         }
         return articles;
    }
    
     
       public ArrayList<Article> parseTaskshow(String jsonText) throws ParseException{
         try {
             articles=new ArrayList<>();
             JSONParser j = new JSONParser();
             
             
             Map<String,Object> obj = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
             
             
              //Map<String,Object> obj = (Map<String,Object>) tasksListJson.get("root");
            
             System.out.print("AAAAAAAAAAAA");
             System.out.print(obj);
             SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
              
           
             Article t = new Article();
             float id = Float.parseFloat(obj.get("id").toString());
             
             String date = obj.get("Date").toString();
            
            
             t.setDate(date);
          
             t.setId((int)id);
             t.setDescription(obj.get("description").toString());
             
             t.setTitre(obj.get("titre").toString());
           
             
           
             t.setImg(obj.get("img").toString());
             
             
             
             articles.add(t);
             }
             
             
          catch (IOException ex) {
            
         }
         return articles;
    }

    
     
       
       
    public ArrayList<Article> getAllTasks(){
        String url = Statics.BASE_URL + "/art/art_jsn";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
                try {
                    articles = parseTasks(new String(req.getResponseData()));
                    req.removeResponseListener(this);
                } catch (ParseException ex) {
                   
                }
              
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return articles;
    }
    
     public ArrayList<Article> ShowArticle(int id){
        String url = Statics.BASE_URL + "/article/art_d_jsn/"+id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
                try {
                    articles = parseTaskshow(new String(req.getResponseData()));
                    req.removeResponseListener(this);
                } catch (ParseException ex) {
                   
                }
              
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return articles;
    }
     
    
    
    
}
