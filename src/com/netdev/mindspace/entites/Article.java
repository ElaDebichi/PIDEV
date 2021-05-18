/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netdev.mindspace.entites;

import java.util.Date;

/**
 *
 * @author spicy
 */
public class Article {
    
    
    private int id,id_emp;
    private String description,titre;
    private String date; 
    private String img; 
    
     public Article() {
        
    }
        public Article(int id,String titre,String description,String date , String img,int id_emp) {
        this.id = id;
        this.titre = titre;
        this.date=date;
        this.description=description;
        this.img = img;
        

    }
        public Article(int id,String titre,String description,String date , String img) {
        this.id = id;
        this.titre = titre;
        this.date=date;
        this.description=description;
        this.img = img;
        

    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

   

    public String getTitre() {
        return titre;
    }
    
    
    public void setTitre(String titre) {
        this.titre = titre;
    }


    
    
    public void setDate (String date) {
        this.date = date;
    }
    
    public String getDate() {
        return date;
    }
    
   
      public String getDescription() {
        return description;
    }
    
    
    public void setDescription(String description) {
        this.description = description;
    }
    
     public void setImg(String img) {
        this.img = img;
    }

     public String getImg() {
        return img;
    }

    @Override
    public String toString() {
        return "Article {" + "id=" + id + ", titre=" + titre + ", description=" + description +""
                + "Date=" + date + "img=" + img +'}';
    }
   
    
}
